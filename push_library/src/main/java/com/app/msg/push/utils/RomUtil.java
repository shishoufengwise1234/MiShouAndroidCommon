package com.app.msg.push.utils;

import android.util.Log;

import com.app.msg.push.Const;
import com.app.msg.push.Target;

/**
 * Created by ssf
 */

public class RomUtil {

    private static final String TAG = "RomUtil";

    /**
     * rom 标识
     */
    //华为
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    //小米
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_HANDY_MODE_SF = "ro.miui.has_handy_mode_sf";
    private static final String KEY_MIUI_REAL_BLUR = "ro.miui.has_real_blur";
    //魅族
    private static final String KEY_FLYME_ICON = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_PUBLISHED = "ro.flyme.published";
    private static final String KEY_FLYME_FLYME = "ro.meizu.setupwizard.flyme";


    /**
     * 华为rom
     *
     * @return
     */
    private static boolean isEMUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.containsKey(KEY_EMUI_VERSION_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 小米rom
     *
     * @return
     */

    private static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();

            return prop.containsKey(KEY_MIUI_VERSION_CODE)
                    || prop.containsKey(KEY_MIUI_VERSION_NAME)
                    || prop.containsKey(KEY_MIUI_REAL_BLUR)
                    || prop.containsKey(KEY_MIUI_HANDY_MODE_SF);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 魅族rom
     *
     * @return
     */
    private static boolean isFlyme() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.containsKey(KEY_FLYME_ICON)
                    || prop.containsKey(KEY_FLYME_PUBLISHED)
                    || prop.containsKey(KEY_FLYME_FLYME);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断手机rom
     * @return
     */
    public static @Target int rom() {

        @Target
        int mTarget;

        if (isEMUI()) {
            mTarget = Const.EMUI;
        }else if (isMIUI()) {
            mTarget = Const.MIUI;
        }else if (isFlyme()) {
            mTarget = Const.FLYME;
        }else{
            mTarget = Const.UMENG;
        }

        Log.d(TAG, "rom: mTarget ="+mTarget);

        return mTarget;
    }
}
