package com.mishou.common.net.util;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 *
 * 网络请求框架log 打印
 */

public class OnlyLog {

    /**
     * The default tag used for logging (if not yet configured).
     */
    public static final String DEFAULT_TAG = "OnlyHttp";


    public static final int VERBOSE = android.util.Log.VERBOSE;

    public static final int DEBUG = android.util.Log.DEBUG;

    public static final int INFO = android.util.Log.INFO;

    public static final int WARN = android.util.Log.WARN;

    public static final int ERROR = android.util.Log.ERROR;

    public static final int ASSERT = android.util.Log.ASSERT;

    public static final int NONE = Integer.MAX_VALUE;

    private static String sTag = DEFAULT_TAG;

    private static int sLevel = VERBOSE;

    /**
     * Configure the defaults for logging.
     *
     * @param tag the default logging tag
     * @param level the minimum level at which statements are logged
     */
    public static void configure(String tag, int level) {
        sTag = tag;
        sLevel = level;
    }

    /**
     * Configure the defaults for logging.  This method is a simpler
     * version of the other configure() method, with less specificity
     * needed for the level.
     *
     * @param tag the default logging tag
     * @param enabled whether logging should be enabled or not
     */
    public static void configure(String tag, boolean enabled) {
        sTag = tag;
        sLevel = enabled ? VERBOSE : NONE;
    }

    /**
     * @return the configured default logging tag
     */
    public static String getTag() {
        return sTag;
    }

    /**
     * @return the configured default logging level
     */
    public static int getLevel() {
        return sLevel;
    }

    //////////////////////////////////////////////////////////////////////////
    // Logging

    public static int v(String msg) {
        return log(VERBOSE, sTag, msg);
    }

    public static int v(String msg, Throwable tr) {
        return log(VERBOSE, sTag, msg, tr);
    }

    public static int v(String tag, String msg) {
        return log(VERBOSE, tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return log(VERBOSE, tag, msg, tr);
    }

    public static int d(String msg) {
        return log(DEBUG, sTag, msg);
    }

    public static int d(String msg, Throwable tr) {
        return log(DEBUG, sTag, msg, tr);
    }

    public static int d(String tag, String msg) {
        return log(DEBUG, tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return log(DEBUG, tag, msg, tr);
    }

    public static int i(String msg) {
        return log(INFO, sTag, msg);
    }

    public static int i(String msg, Throwable tr) {
        return log(INFO, sTag, msg, tr);
    }

    public static int i(String tag, String msg) {
        return log(INFO, tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return log(INFO, tag, msg, tr);
    }

    public static int w(String msg) {
        return log(WARN, sTag, msg);
    }

    public static int w(String msg, Throwable tr) {
        return log(WARN, sTag, msg, tr);
    }

    public static int w(String tag, String msg) {
        return log(WARN, tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return log(WARN, tag, msg, tr);
    }

    public static int e(String msg) {
        return log(ERROR, sTag, msg);
    }
    public static int e(Throwable throwable){
        return log(ERROR,sTag,throwable);
    }

    public static int e(String msg, Throwable tr) {
        return log(ERROR, sTag, msg, tr);
    }

    public static int e(String tag, String msg) {
        return log(ERROR, tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return log(ERROR, tag, msg, tr);
    }

    public static int wtf(String msg) {
        return log(ASSERT, sTag, msg);
    }

    public static int wtf(String msg, Throwable tr) {
        return log(ASSERT, sTag, msg, tr);
    }

    public static int wtf(String tag, String msg) {
        return log(ASSERT, tag, msg);
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        return log(ASSERT, tag, msg, tr);
    }

    public static int log(int level, String msg) {
        return log(level, sTag, msg);
    }

    public static int log(int level, String tag, String msg) {
        if (sLevel > level) {
            return -1;
        }

        switch (level) {
            case VERBOSE:
                return android.util.Log.v(tag, msg);
            case DEBUG:
                return android.util.Log.d(tag, msg);
            case INFO:
                return android.util.Log.i(tag, msg);
            case WARN:
                return android.util.Log.w(tag, msg);
            case ERROR:
                return android.util.Log.e(tag, msg);
        }

        // Provided invalid level
        return -1;
    }

    public static int log(int level, String msg, Throwable tr) {
        return log(level, sTag, msg, tr);
    }

    public static int log(int level, String tag, String msg, Throwable tr) {
        if (sLevel > level) {
            return -1;
        }

        switch (level) {
            case VERBOSE:
                return android.util.Log.v(tag, msg, tr);
            case DEBUG:
                return android.util.Log.d(tag, msg, tr);
            case INFO:
                return android.util.Log.i(tag, msg, tr);
            case WARN:
                return android.util.Log.w(tag, msg, tr);
            case ERROR:
                return android.util.Log.e(tag, msg, tr);
        }

        // Provided invalid level
        return -1;
    }

}
