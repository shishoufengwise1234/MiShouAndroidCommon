package com.mishou.common.net.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import com.google.gson.JsonObject;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 *
 *
 * 处理相关判断和逻辑等
 */

public class OnlyUtils {


    /**
     * null 值检查
     * @param t 检查数据
     * @param message 异常信息
     */
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

    /**
     * 关闭资源
     * @param close 资源
     */
    public static void close(Closeable close) {
        if (close != null) {
            try {
                closeThrowException(close);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 关闭资源
     * @param close 资源
     * @throws IOException
     */
    public static void closeThrowException(Closeable close) throws IOException {
        if (close != null) {
            close.close();
        }
    }

    /**
     *
     * 将Map<String,String> 转换成 jsonObject
     *
     * @param map Map<String,String>
     * @return jsonObject
     */
    public static JsonObject mapToJson(Map<String,String> map){
        JsonObject jsonObject = new JsonObject();
        if (map == null || map.isEmpty()){
            return jsonObject;
        }else{
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry != null) {
                    jsonObject.addProperty(entry.getKey(), entry.getValue());
                }
            }
        }
        return jsonObject;
    }
}
