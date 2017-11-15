package com.mishou.common.net.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 *
 *
 * 处理相关判断和逻辑等
 */

public class OnlyUtils {


    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }


    /**
     * 检测网络是否可用
     * @param context context
     * @return true 可用 false no
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        if (null == manager)
            return false;

        NetworkInfo info = manager.getActiveNetworkInfo();

        return !(null == info || !info.isAvailable());
    }
}
