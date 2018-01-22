package com.app.msg.push.utils;

import android.os.Looper;

/**
 * Created by ssf
 */

public class JHandler {
    private static android.os.Handler mHandler = null;

    public static android.os.Handler handler() {
        if (mHandler == null) {
            synchronized (JHandler.class) {
                mHandler = new android.os.Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }

}
