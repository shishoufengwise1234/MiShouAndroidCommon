package com.mishou.demo.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.app.msg.push.Message;
import com.app.msg.push.PushInterface;
import com.app.msg.push.Target;
import com.mishou.common.utils.AppUtils;
import com.mishou.common.utils.StringUtils;
import com.mishou.common.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import com.app.msg.push.Const;

/**
 * Created by ${shishoufeng} on 2017/2/7 0007.
 * email:shishoufeng1227@126.com
 * <p>
 * 推送回调
 */
public class PushCallBack implements PushInterface {

    private static final String TAG = "PushCallBack";


    /**
     * 注册服务成功
     * 小米 regid
     * 友盟 device_token
     * huawei token
     *
     * @param context
     * @param registerID 注册成功 返回 regId
     */
    @Override
    public void onRegister(Context context, String registerID) {

        Logger.t(TAG).d("注册服务成功 registerID =" + registerID);

    }

    @Override
    public void onUnRegister(Context context) {

        Logger.d("onUnRegister");
    }

    @Override
    public void onPaused(Context context) {

        Logger.d("onPaused");
    }

    @Override
    public void onResume(Context context) {

        Logger.d("onResume");
    }

    /**
     * 推送通知
     *
     * @param context
     * @param message 具体消息
     */
    @Override
    public void onMessage(Context context, Message message) {

        Logger.d("onMessage" + message.toString());
    }

    /**
     * 点击消息时执行自定义操作
     *
     * @param context
     * @param message
     */
    @Override
    public void onMessageClicked(Context context, Message message) {

        Logger.d("onMessageClicked" + message.toString());


    }

    /**
     * 华为 通知栏点击
     * @param context
     * @param message
     */
    private void EMUIMsgClicker(Context context, Message message) {

        ToastUtils.showMessage(context,"通知栏 被点击了 "+message.getTarget());
    }

    /**
     * 小米 通知栏被点击
     *
     * @param context
     * @param message
     */
    private void MIUIMsgClicker(Context context, Message message) {

        if (message != null){

            String type = message.getType();
            if (!StringUtils.isEmpty(type)){
                jumpTypeManager(context,message,Integer.parseInt(type));
            }

        }
    }

    /**
     * 通知栏被点击
     *
     * @param context
     * @param message
     */
    private void UMengMsgClicker(Context context, Message message) {

        if (message != null){

            String type = message.getType();
            if (!StringUtils.isEmpty(type)){
                jumpTypeManager(context,message,Integer.parseInt(type));
            }

        }

    }

    /**
     * 小米 透传消息回调
     * 华为 透传消息回调
     *
     * @param context
     * @param message
     */
    @Override
    public void onCustomMessage(Context context, Message message) {

        if (message != null) {

            Logger.d("onCustomMessage" + message.toString());

            @Target
            int target = message.getTarget();

            switch (target) {

                case Const.MIUI:
                    Logger.d("已接受到 小米透传消息" + message.toString());

//                    ImPushMessageManager.getManager().onNewMessage(message.getTransMsg());

//                    ToastUtils.showMessage(context,"已接受到 小米透传消息"+message.toString());
                    break;

                case Const.FLYME:
//                    break;
                case Const.EMUI:
                case Const.UMENG:
                    Logger.d("收到友盟自定义消息: "+message.toString());


//                    ImPushMessageManager.getManager().onNewMessage(message.getTransMsg());

//                    Logger.d("已接受到 华为透传消息" + message.toString());
//                    ToastUtils.showMessage(context,"已接受到 华为透传消息"+message.toString());


                    break;

                default:
                    break;
            }

        }
    }

    /***
     * 设置别名回调
     *
     * @param context
     * @param alias
     */
    @Override
    public void onAlias(Context context, String alias) {

        Logger.d("设置别名 alias =" + alias);
    }

    /***
     *
     * 自定义点击通知栏行为
     * @param context
     * @param msg 推送数据
     * @param type
     */
    private void jumpTypeManager(Context context,Message msg,int type){

        if (context == null)
            return;

        //判断APP 是否已经启动了
        if (AppUtils.isAppRunning(context,"")){

            Logger.d("qifuyun app is runing");

            Intent main_intent = new Intent();

            main_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//            Intent detais_intent = getTypeBundle(context,type);

//            Intent [] intents = {main_intent,detais_intent};

//            context.startActivities(intents);
        }else{  //启动应用 之后进入 推送指定页面

            Logger.d("qifuyun app not runing");

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("");

            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

            Bundle bundle = new Bundle();

            context.startActivity(launchIntent);
        }
    }



}
