package com.mishou.common.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class ToastUtils {

    private static final String TAG = "ToastUtils";


    /**
     * 短时间显示toast
     *
     * @param context
     * @param message
     */
    public static void showMessage(@NonNull Context context, @Nullable String message) {
        Context mContext = context.getApplicationContext();
        show(mContext, message, 0, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示toast
     *
     * @param context
     */
    public static void showMessage(@NonNull Context context, @StringRes int id) {
        Context mContext = context.getApplicationContext();
        show(mContext, null, id, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示toast
     *
     * @param context
     * @param message
     */
    public static void showMessageLong(@NonNull Context context, @Nullable String message) {
        Context mContext = context.getApplicationContext();
        show(mContext, message, 0, Toast.LENGTH_LONG);
    }

    private static Toast mToast;

    /***
     * toast 显示 可在子线程中使用 避免多次点击 toast 不消失问题
     *
     * @param context
     * @param message
     * @param time
     */
    private static void show(Context context, final String message, final int resId, final int time) {

        if (context == null)
            return;

        if (mToast == null) {

            try {

                final Activity activity = (Activity) context;

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (message == null || "".equals(message)) {
                            if (resId != 0){
                                mToast = Toast.makeText(activity, activity.getResources().getString(resId) + "", time);
                            }else{
                                Log.d(TAG,"toast error");
                            }
                        } else {
                            mToast = Toast.makeText(activity, message, time);
                        }
                    }
                });

            } catch (Exception e) {

                if (message == null || "".equals(message)) {
                    if (resId == 0){
                        Log.d(TAG,"toast error");
                    }else{
                        mToast = Toast.makeText(context, context.getResources().getString(resId) + "", time);
                    }
                } else {
                    mToast = Toast.makeText(context, message + "", time);
                }
            }

        } else {
            if (!StringUtils.isEmpty(message)){
                mToast.setText(message+"");
                mToast.setDuration(time);
            }
        }
        if (mToast != null)
            mToast.show();

    }


}
