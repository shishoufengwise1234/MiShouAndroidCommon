package com.mishou.common.utils.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mishou.common.utils.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 * <p>
 * 封装 通过jumpUtils 快速跳转页面 传递的bundle 数据
 */

public class JumpDataUtils {


    /**
     * 取出bundle 数据
     * @param intent getIntent()
     * @return bundle
     *
     * 之所以 new Bundle() 这样写 是为了防止出现 空对象崩溃
     * （以log输出作为提醒）
     */
    public static Bundle getBundle(@NonNull Intent intent) {
        if (intent == null){
            LogUtils.e("JumpDataUtils -> intent = null");
            return new Bundle();
        }else {
            Bundle bundle = intent.getBundleExtra(JumpUtils.DEFAULT_DATA_KEY);
            if (bundle == null){
                LogUtils.e("JumpDataUtils -> intent.getBundleExtra(JumpUtils.DEFAULT_DATA_KEY) = null");
                return new Bundle();
            }else{
                return bundle;
            }
        }
    }

    /**
     * 根据指定key获取bundle
     */
    public static Bundle getBundle(@NonNull Intent intent,@NonNull String key) {
        if (intent == null){
            LogUtils.e("JumpDataUtils -> intent = null");
            return new Bundle();
        }else {
            Bundle bundle = intent.getBundleExtra(key);
            if (bundle == null){
                LogUtils.e("JumpDataUtils -> intent.getBundleExtra(JumpUtils.DEFAULT_DATA_KEY) = null");
                return new Bundle();
            }else{
                return bundle;
            }
        }
    }

    public static String getString(@NonNull Intent intent, @NonNull String key) {
        return getBundle(intent).getString(key, "");
    }

    public static String getString(@NonNull Intent intent, @NonNull String key, @Nullable String defaultValue) {
        return defaultValue == null ? getString(intent, key) : getBundle(intent).getString(key, defaultValue);
    }

    public static int getInt(@NonNull Intent intent, @NonNull String key) {
        return getBundle(intent).getInt(key, 0);
    }

    public static int getInt(@NonNull Intent intent, @NonNull String key, int defaultValue) {
        return getBundle(intent).getInt(key, defaultValue);
    }

    public static long getLong(@NonNull Intent intent, @NonNull String key) {
        return getBundle(intent).getLong(key, 0L);
    }

    public static long getLong(@NonNull Intent intent, @NonNull String key, long defaultValue) {
        return getBundle(intent).getLong(key, defaultValue);
    }

    public static Parcelable getParcelable(@NonNull Intent intent, @NonNull String key) {
        return getBundle(intent).getParcelable(key);
    }

    public static Serializable getSerializable(@NonNull Intent intent, @NonNull String key) {
        return getBundle(intent).getSerializable(key);
    }

    public static ArrayList<String> getStringArrayList(@NonNull Intent intent, @NonNull String key) {
        return getBundle(intent).getStringArrayList(key);
    }

    public static CharSequence getCharSequence(@NonNull Intent intent, @NonNull String key) {
        return getBundle(intent).getCharSequence(key, "");
    }

    public static CharSequence getCharSequence(@NonNull Intent intent, @NonNull String key, @Nullable CharSequence defaultValue) {
        return defaultValue == null ? getCharSequence(intent, key) : getBundle(intent).getCharSequence(key, defaultValue);
    }


}
