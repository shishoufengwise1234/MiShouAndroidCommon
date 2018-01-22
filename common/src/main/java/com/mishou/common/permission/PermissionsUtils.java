package com.mishou.common.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by ${shishoufeng} on 2017/5/2 0002.
 * email:shishoufeng1227@126.com
 *
 * 权限辅助类 跳转到应用设置页面 开启权限
 */

public class PermissionsUtils {


    /**
     * 跳转至 应用 详情 设置界面
     * @param context 上下文对象
     */
    public static void jumpAppDetailsSettings(Context context){

        if (context == null) return;

        //进入App设置页面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);

        context.startActivity(intent);
    }
}
