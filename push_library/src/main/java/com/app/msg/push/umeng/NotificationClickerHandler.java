package com.app.msg.push.umeng;

import android.content.Context;

import com.app.msg.push.Const;
import com.app.msg.push.Message;
import com.app.msg.push.PushInterface;
import com.app.msg.push.utils.L;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ${shishoufeng} on 2017/2/8 0008.
 * email:shishoufeng1227@126.com
 * <p>
 * 自定义通知栏处理
 */
public class NotificationClickerHandler extends UmengNotificationClickHandler {

    private static final String TAG = "NotificationClickerHandler";


    private PushInterface mPushInterface;

    public PushInterface getPushInterface() {
        return mPushInterface;
    }

    public void setPushInterface(PushInterface mPushInterface) {
        this.mPushInterface = mPushInterface;
    }

    /**
     * 忽略此消息时 调用
     *
     * @param context
     * @param msg
     */
    @Override
    public void dismissNotification(Context context, UMessage msg) {
        UmLog.d(TAG, "dismissNotification");
        super.dismissNotification(context, msg);
    }

    /**
     * 点击通知栏时
     *
     * @param context
     * @param msg
     */
    @Override
    public void launchApp(Context context, UMessage msg) {
        UmLog.d(TAG, "launchApp");
        super.launchApp(context, msg);

        if (msg != null) {

            Message message = new Message();
            message.setMessage(msg.text);
            message.setMessageID(msg.message_id);
            message.setTitle(msg.title);

            //自定义参数
            String custom = msg.custom;

            L.d(TAG,"自定义参数 = "+custom);

            message.setTarget(Const.UMENG);

            if (mPushInterface != null) {
                mPushInterface.onMessageClicked(context, message);
            }
        }

    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        UmLog.d(TAG, "openActivity");
        super.openActivity(context, msg);
    }

    /**
     * 打开url
     *
     * @param context
     * @param msg
     */
    @Override
    public void openUrl(Context context, UMessage msg) {
        UmLog.d(TAG, "openUrl");
        super.openUrl(context, msg);
    }

    /**
     * 自定义通知栏点击时回调
     * @param context
     * @param msg
     */
    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        UmLog.d(TAG, "dealWithCustomAction"+msg.toString());
        super.dealWithCustomAction(context, msg);

        if (msg != null) {

            Message message = new Message();
            message.setMessage(msg.text);
            message.setMessageID(msg.message_id);
            message.setTitle(msg.title);

            //自定义参数
            String custom = msg.custom;
            try {
                JSONObject jsonObject = new JSONObject(custom);
                String type = jsonObject.getString("type");

                message.setType(type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            //自定义参数
//            Map<String,String> maps = msg.extra;
//
//            String type = maps.get("type");
//            message.setType(type);

            message.setTarget(Const.UMENG);

            if (mPushInterface != null) {
                mPushInterface.onMessageClicked(context, message);
            }
        }

    }

    @Override
    public void autoUpdate(Context context, UMessage msg) {
        UmLog.d(TAG, "autoUpdate");
        super.autoUpdate(context, msg);
    }
}
