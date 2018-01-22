package com.app.msg.push.utils;

import android.util.Log;

/**
 * Created by ssf
 */

public class L {
    private static final String TAG = "AndroidPush";

    public static boolean debug = false;

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg){
        d(TAG,msg);
    }

    public static void d(String TAG,String msg){
        if (debug){
            Log.d(TAG,msg);
        }
    }
}
