package com.library.widgets.utils;

import android.util.Log;

/**
 * Created by shishoufeng on 2017/12/13.
 * email:shishoufeng1227@126.com
 *
 * log 输出控制
 */

public class Wlog {

    private static final String TAG = "Wlog";

    private static boolean isLog = false;

    public static void setIsLog(boolean isPrint){
        isLog = isPrint;
    }

    public static void i (String tag,String msg){
        if (isLog)
            Log.i(tag,msg);
    }

    public static void i (String msg){
        i(TAG,msg);
    }
    public static void d (String tag,String msg){
        if (isLog)
            Log.d(tag,msg);
    }

    public static void d (String msg){
        d(TAG,msg);
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
        if (isLog)
            Log.e(tag,msg,throwable);
    }

    public static void w (String tag,String msg){
        if (isLog)
            Log.w(tag,msg);
    }

    public static void w (String msg){
        w(TAG,msg);
    }
}
