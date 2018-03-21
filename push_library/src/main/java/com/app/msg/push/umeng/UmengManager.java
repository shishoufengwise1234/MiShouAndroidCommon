package com.app.msg.push.umeng;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.app.msg.push.Const;
import com.app.msg.push.Message;
import com.app.msg.push.PushInterface;
import com.app.msg.push.utils.JHandler;
import com.app.msg.push.utils.L;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.tag.TagManager;

import java.util.List;


/**
 *
 *
 * update ssf 2017/2/8
 */
public class UmengManager {


    private String TAG = "UmengManager";

    public static final String ALIAS_DEFAULT = "MiShouNusing";

    //最大展示推送数量
    public static final int DISPLAY_NOTIFICATION_NUMBER = 10;

    private Context context;
    private PushAgent mPushAgent;
    private String deviceToken;

    private static PushInterface mPushInterface;

    private static UmengManager instance = null;

    /**
     * 单一实例
     */
    public static UmengManager getInstance(Context context) {
        if (instance == null) {
            instance = new UmengManager(context);
        }
        return instance;
    }

    private UmengManager(Context context) {
        this.context = context;
    }

    /**
     * 获取推送服务
     * @return
     */
    private PushAgent getPushAgent(){
        if (mPushAgent == null){
            this.mPushAgent = PushAgent.getInstance(context);
        }
        return mPushAgent;
    }


    /**
     * @param pushInterface
     */
    public static void registerInterface(PushInterface pushInterface) {
        mPushInterface = pushInterface;
    }


    /**
     * 注册友盟推送
     */
    public void register() {

        getPushAgent().register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String device_token) {
                if (mPushInterface != null) {
                    mPushInterface.onRegister(context, device_token);
                }
                deviceToken = device_token;

                L.d(TAG, device_token);

                //设置通知栏点击效果
                NotificationClickerHandler notificationClickerHandler = new NotificationClickerHandler();
                //设置回调
                notificationClickerHandler.setPushInterface(mPushInterface);

                getPushAgent().setNotificationClickHandler(notificationClickerHandler);

                //自定义消息回调
                getPushAgent().setMessageHandler(messageHandler);

                //通知栏可以设置最多显示通知的条数，当有新通知到达时，会把旧的通知隐藏。
                getPushAgent().setDisplayNotificationNumber(DISPLAY_NOTIFICATION_NUMBER);

            }

            @Override
            public void onFailure(String s, String s1) {

                L.e(TAG, s + "&&" + s1);
            }
        });
    }

    /***
     *
     * 自定义消息回调
     */
    UmengMessageHandler messageHandler = new UmengMessageHandler(){
        @Override
        public void dealWithCustomMessage(final Context context, final UMessage msg) {

            L.d(TAG,"ummeng 自定义消息:");
            if (mPushInterface != null) {

                final Message message = new Message();
                message.setTarget(Const.UMENG);

                //自定义消息
                String mes = msg.custom;
                message.setTransMsg(mes);

                JHandler.handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mPushInterface.onCustomMessage(context, message);
                    }
                });
            }
        }
//
//        /**
//         * 自定义通知栏样式
//         * @param context
//         * @param msg
//         * @return
//         */
//        @Override
//        public Notification getNotification(Context context, UMessage msg) {
//            switch (msg.builder_id) {
//                case 1:
//
//                    //使用兼容Notification
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                    //配置通知内容
//                    builder.setAutoCancel(true)
//                            .setShowWhen(true)
////                            .setSmallIcon(R.drawable.umeng_push_notification_default_small_icon)
//                            .setSmallIcon(R.drawable.icon_push_yellow_car_small)
//                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.umeng_push_notification_default_large_icon))
//                            .setColor(Color.parseColor("#005dad"))
//                            .setContentTitle(msg.title)
//                            .setContentText(msg.text);
//
//                    return builder.build();
//                default:
//                    //默认为0，若填写的builder_id并不存在，也使用默认。
//                    return super.getNotification(context, msg);
//            }
//        }
    };

    /**
     * 关闭推送
     */
    public void closePush() {
            getPushAgent().disable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    if (mPushInterface != null) {
                        mPushInterface.onPaused(context);
                    }
                }

                @Override
                public void onFailure(String s, String s1) {
                }
            });

    }

    /**
     * 获取DeviceToken
     *
     * @return
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * 设置用户tag
     * @param tags
     */
    public void addTag(String... tags){

        getPushAgent().getTagManager().add(new TagManager.TCallBack() {

            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                //isSuccess表示操作是否成功
            }
        },tags);
    }

    /***
     * 删除tag
     * @param tags
     */
    public void deleteTag(String... tags){

        getPushAgent().getTagManager().delete(new TagManager.TCallBack() {
            @Override
            public void onMessage(boolean b, ITagManager.Result result) {

            }
        },tags);
    }

    /**
     * 更新tag
     * @param tags
     */
    public void updateTag(String... tags){

        getPushAgent().getTagManager().update(new TagManager.TCallBack() {
            @Override
            public void onMessage(boolean b, ITagManager.Result result) {

            }
        },tags);
    }

    /**
     * 清除所有tag
     */
    public void resetTag(){

        getPushAgent().getTagManager().reset(new TagManager.TCallBack() {
            @Override
            public void onMessage(boolean b, ITagManager.Result result) {

            }
        });
    }

    /**
     * 获取所有tag
     */
    public void getAllTag(){

        getPushAgent().getTagManager().list(new TagManager.TagListCallBack() {
            @Override
            public void onMessage(boolean b, List<String> list) {

            }
        });
    }

    /**
     * 添加别名
     * 设置用户id和device_token的一对多的映射关系
     * @param str
     * @param string
     */
    public void addAlias(String str,String string){

        getPushAgent().addAlias(str, string, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {

                L.d(TAG, "umneg add alias b ="+b+"s ="+s);
            }
        });
    }

    /**
     * 设置用户别名
     *设置用户id和device_token的一一映射关系，确保同一个alias只对应一台设备
     * @param alias
     */
    public void setAlias(String alias,String string) {
            getPushAgent().addAlias(alias, string, new UTrack.ICallBack() {
                @Override
                public void onMessage(boolean isSuccess, String message) {

                    Log.d(TAG, "isSuccess ="+isSuccess+"message ="+message);
                    mPushInterface.onAlias(context, message);
                }
            });

    }

    /**
     * 移除一个别名
     * @param alias
     * @param string
     */
    public void removeAlias(String alias,String string){
        getPushAgent().removeAlias(alias, string, new UTrack.ICallBack(){
            @Override
            public void onMessage(boolean isSuccess, String message) {

                L.d(TAG,"umeng 移除别名"+isSuccess+" message ="+message);
            }
        });
    }

    /**
     * 为免过度打扰用户，SDK默认在“23:00”到“7:00”之间收到通知消息时不响铃，不振动，不闪灯。
     * 如果需要改变默认的静音时间，可以使用以下方法
     *
     * @param startHour
     * @param startMinute
     * @param endHour
     * @param endMinute
     */
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute) {
        getPushAgent().setNoDisturbMode(startHour, startMinute, endHour, endMinute);
    }

    /**
     * 打开debug 功能
     */
    public void setDebugMode(boolean isDebug) {
        getPushAgent().setDebugMode(isDebug);
    }

    /**
     * 若调用关闭推送后，想要再次开启推送，则需要调用以下代码（请在Activity内调用)
     */
    public void openPush() {
        getPushAgent().enable(new IUmengCallback() {
            @Override
            public void onSuccess() {
                L.d(TAG,"open push onSuccess");
                if (mPushInterface != null) {
                    mPushInterface.onResume(context);
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                L.d(TAG,"open push failure"+s+"  "+s1);
            }
        });
    }


    public void onAppStart(){
        getPushAgent().onAppStart();
    }

    /**
     * 设置渠道
     */
    public void setChannel(String channel){
        if (TextUtils.isEmpty(channel)){
            return;
        }
        getPushAgent().setMessageChannel(channel);
    }

    /**
     * 获取注册完成之后 device token
     * @return device token
     */
    public String getRegisterId(){
        return getPushAgent().getRegistrationId();
    }

}
