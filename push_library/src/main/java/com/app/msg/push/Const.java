package com.app.msg.push;

import android.text.TextUtils;

/**
 * Created by ssf
 */

public class Const {

    /**小米*/
    public static final int MIUI = 1;
    /**华为*/
    public static final int EMUI = 2;
    /**魅族*/
    public static final int FLYME = 3;
    /**友盟*/
    public static final int UMENG = 4;


    private static String miui_app_id = null;
    private static String miui_app_key = null;
    private static String flyme_app_id = null;
    private static String flyme_app_key = null;

    public static final String ALIILAS_TYPE = "Umeng";

    public static String getMiui_app_id() {
        if (TextUtils.isEmpty(miui_app_id)) {
            throw new NullPointerException("please config miui_app_id before use it");
        }
        return miui_app_id;
    }

    public static String getMiui_app_key() {
        if (TextUtils.isEmpty(miui_app_key)) {
            throw new NullPointerException("please config miui_app_key before use it");
        }
        return miui_app_key;
    }

    public static String getFlyme_app_id() {
        if (TextUtils.isEmpty(flyme_app_id)) {
            throw new NullPointerException("please config flyme_app_id before use it");
        }
        return flyme_app_id;
    }

    public static String getFlyme_app_key() {
        if (TextUtils.isEmpty(flyme_app_key)) {
            throw new NullPointerException("please config flyme_app_key before use it");
        }
        return flyme_app_key;
    }


    public static void setMiUI_APP(String miui_app_id, String miui_app_key) {
        setMiui_app_id(miui_app_id);
        setMiui_app_key(miui_app_key);
    }

    public static void setFlyme_APP(String flyme_app_id, String flyme_app_key) {
        setFlyme_app_id(flyme_app_id);
        setFlyme_app_key(flyme_app_key);
    }


    private static void setMiui_app_id(String miui_app_id) {
        Const.miui_app_id = miui_app_id;
    }

    private static void setMiui_app_key(String miui_app_key) {
        Const.miui_app_key = miui_app_key;
    }

    private static void setFlyme_app_id(String flyme_app_id) {
        Const.flyme_app_id = flyme_app_id;
    }

    private static void setFlyme_app_key(String flyme_app_key) {
        Const.flyme_app_key = flyme_app_key;
    }
}
