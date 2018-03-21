package com.mishou.demo.push;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.app.msg.push.PushInterface;
import com.app.msg.push.model.TokenModel;
import com.app.msg.push.umeng.UmengManager;
import com.app.msg.push.utils.L;
import com.app.msg.push.utils.RomUtil;
import com.orhanobut.logger.Logger;

import java.util.List;


/****
 * update ssf 2017/2/7
 * <p/>
 * 1.注册推送服务
 * 2.开始推送服务
 * 3.停止推送服务
 * 4.设置推送回调
 */
public class PushManager {

    /**
     * 设置回调对象
     */
    private static PushInterface pushInterface = new PushCallBack();

    /**
     * 初始化配置
     *
     * @param context
     * @param debug
     */
    public static void register(Context context, boolean debug) {
        register(context, debug, pushInterface);
    }

    /**
     * 注册
     *
     * @param context;
     * @param debug
     * @param pushInterface
     */
    public static void register(final Context context, boolean debug, PushInterface pushInterface) {
        if (context == null)
            return;

        //log 日志开关
        L.debug = debug;
        //目前统一使用友盟推送
        UmengManager.getInstance(context).register();
        UmengManager.registerInterface(pushInterface);
        UmengManager.getInstance(context).setDebugMode(debug);

//        int target = RomUtil.rom();
//
//        switch (target) {
//
//            case Const.EMUI: //华为
//            case Const.FLYME: //魅族
//            case Const.UMENG: //默认友盟
//
//                break;
//            case Const.MIUI:  //小米
//
//                break;
//
//            default:
//                break;
//
//        }
        //注册之后打开 推送开关
//        resumePush(context);

    }

    /**
     * 用于小米推送的注册
     *
     * @param context
     * @return
     */
    private static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取消或者关闭推送
     *
     * @param context
     */
    public static void unregister(Context context) {
        if (context == null)
            return;

        UmengManager.getInstance(context).closePush();

//        int target = RomUtil.rom();
//
//        switch (target) {
//            case Const.EMUI:
////                EMHuaweiPushReceiver.clearPushInterface();
////                HmsPushManager.getPushManager(context).deleteToken(getToken(context).getToken());
////                break;
//            case Const.FLYME:
////                FlymeReceiver.clearPushInterface();
////                com.meizu.cloud.pushsdk.PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
////                break;
//            case Const.UMENG:
//                UmengManager.getInstance(context).closePush();
//                break;
//            case Const.MIUI:
//
//                break;
//            default:
//                break;
//        }
    }


    /**
     * 设置消息透传 接口回调
     *
     * @param pushInterface
     */
    public static void setPushInterface(PushInterface pushInterface) {
        if (pushInterface == null)
            return;

        UmengManager.registerInterface(pushInterface);

//        int target = RomUtil.rom();
//
//        switch (target) {
//            case Const.EMUI:
////                EMHuaweiPushReceiver.registerInterface(pushInterface);
////                break;
//            case Const.FLYME:
////                FlymeReceiver.registerInterface(pushInterface);
////                break;
//            case Const.UMENG:
//
//                break;
//            case Const.MIUI:
//
//                break;
//            default:
//                break;
//        }
    }


    /**
     * 设置别名，
     * 华为不支持alias的写法，所以只能用tag，tag只能放map，所以alias作为value,key为name
     *
     * @param context
     * @param alias
     */
    public static void setAlias(final Context context, String alias) {
        if (TextUtils.isEmpty(alias))
            return;

        Logger.d("set alias = " + alias);

        UmengManager.getInstance(context).addAlias(alias, UmengManager.ALIAS_DEFAULT);


//        int target = RomUtil.rom();
//
//        switch (target) {
//            case Const.EMUI:
////                Map<String, String> tag = new HashMap<>();
////                tag.put("name", alias);
////                HmsPushManager.getPushManager(context).setTags(tag);
////                break;
//            case Const.FLYME:
////                com.meizu.cloud.pushsdk.PushManager.subScribeAlias(context, Const.getFlyme_app_id(), Const.getFlyme_app_key(), getToken(context).getToken(), alias);
////                break;
//            case Const.UMENG:
////                UmengManager.getInstance(context).setAlias(alias, UmengManager.ALIAS_QIFUYUN);
//                break;
//            case Const.MIUI:
//
//                break;
//            default:
//                break;
//        }

    }

    /**
     * 获取唯一的token
     *
     * @param context
     * @return
     */
    public static TokenModel getToken(Context context) {
        if (context == null)
            return null;

        TokenModel result = new TokenModel();

        int target = RomUtil.rom();

        result.setTarget(target);

        result.setToken(UmengManager.getInstance(context).getDeviceToken());

//        switch (target) {
//            case Const.EMUI:
////                result.setToken(EMHuaweiPushReceiver.getmToken());
////                break;
//            case Const.FLYME:
////                result.setToken(com.meizu.cloud.pushsdk.PushManager.getPushId(context));
////                break;
//            case Const.UMENG:
//                result.setToken(UmengManager.getInstance(context).getDeviceToken());
//                break;
//            case Const.MIUI:
//                break;
//            default:
//                break;
//        }
        return result;
    }

    /**
     * 删除别名
     *
     * @param context
     */
    public static void deleteAlias(Context context, String alias) {
        if (context == null) {
            return;
        }
//        String alias = getAlias();

        UmengManager.getInstance(context).removeAlias(alias, UmengManager.ALIAS_DEFAULT);

//        @Target int target = RomUtil.rom();
//
//        switch (target) {
//            case Const.EMUI:
////                HmsPushManager.getPushManager(context).deleteTags(getAlias());
////                break;
//            case Const.FLYME://待续
////                break;
//            case Const.UMENG:
//                UmengManager.getInstance(context).removeAlias(alias,UmengManager.ALIAS_QIFUYUN);
//                break;
//            case Const.MIUI:
//                break;
//            default:
//                break;
//        }
    }


    /**
     * 关闭推送服务
     *
     * @param context
     */
    public static void closePush(Context context) {
        if (context == null)
            return;

        UmengManager.getInstance(context).closePush();


//        @Target int target = RomUtil.rom();
//
//        switch (target) {
//            case Const.EMUI:
////                HmsPushManager.getPushManager(context).deleteToken(getToken(context).getToken());
////                if (EMHuaweiPushReceiver.getPushInterface() != null) {
////                    EMHuaweiPushReceiver.getPushInterface().onPaused(context);
////                }
////                break;
//            case Const.FLYME:
////                com.meizu.cloud.pushsdk.PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
////                if (FlymeReceiver.getPushInterface() != null) {
////                    FlymeReceiver.getPushInterface().onPaused(context);
////                }
////                break;
//            case Const.UMENG:
//                UmengManager.getInstance(context).closePush();
//                break;
//            case Const.MIUI:
//
//                break;
//            default:
//                break;
//        }
    }

    /**
     * 开始推送
     *
     * @param context
     */
    public static void resumePush(Context context) {
        if (context == null)
            return;

        UmengManager.getInstance(context).openPush();

//        @Target int target = RomUtil.rom();
//
//        switch (target) {
//            case Const.EMUI:
//                //huawei 注册成功即可开始推送
////                if (EMHuaweiPushReceiver.getPushInterface() != null) {
////                    EMHuaweiPushReceiver.getPushInterface().onResume(context);
////                }
////                break;
//            case Const.FLYME:
////                com.meizu.cloud.pushsdk.PushManager.register(context, Const.getMiui_app_id(), Const.getFlyme_app_key());
////                if (FlymeReceiver.getPushInterface() != null) {
////                    FlymeReceiver.getPushInterface().onResume(context);
////                }
////                break;
//            case Const.UMENG:
//                UmengManager.getInstance(context).openPush();
//                break;
//            case Const.MIUI:
//
//                break;
//            default:
//                break;
//        }
    }


}
