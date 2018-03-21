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

    public static void e (String tag,String msg){
        e(tag,msg,null);
    }

    public static void e (String msg){
        e(TAG,msg);
    }

    public static void e (Throwable t){
        e(TAG,TAG,t);
    }

    public static void e (String tag,String msg,Throwable throwable){
        if (debug)
            Log.e(tag,msg,throwable);
    }
}
