package com.mishou.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;


/**
 * Created by Administrator on 2016/8/10 0010.
 * <p/>
 * SharedPreferences 封装 可写入 string int float set<String> boolean long 类型数值
 */
public class SharedPreferencesUtils {


    public static final String DEFAULT_FILE_NAME = "ms_nursing";
    /**
     * 保存用户其他信息，保存城市信息，是否第一次安装
     */
    public static final String USER_OTHER_MESSAGE = "user_other_message";
    /**
     * 保存用户信息
     */
    public static final String USER_MESSAGE = "user_message";

    /**
     * 保存IM 服务器配置信息
     */
    public static final String USER_IM_MESSAGE = "user_im_message";

    private static SharedPreferencesUtils sharedPreferencesUtils = null;

    public static SharedPreferencesUtils getSharedPreferencesUtils() {
        if (sharedPreferencesUtils == null)
            sharedPreferencesUtils = new SharedPreferencesUtils();
        return sharedPreferencesUtils;

    }

    /**
     * 获取 SharedPreferences 对象
     *
     * @param context
     * @param fileName 目标文件
     * @return
     */
    public SharedPreferences getSharedPreferences(@NonNull Context context, @NonNull String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }


    /**
     * 获取editor 对象
     *
     * @param context
     * @param fileName
     * @return
     */
    private SharedPreferences.Editor getEditor(@NonNull Context context, @NonNull String fileName) {
        return getSharedPreferences(context, fileName).edit();
    }

    private SharedPreferencesUtils() {
    }

    /**
     * 清除指定名字的SharedPreferences
     *
     * @param context
     * @param fileName
     */
    public static void ClearSharedPreferences(@NonNull Context context, @NonNull String fileName) {
        getSharedPreferencesUtils().getSharedPreferences(context, fileName).edit().clear().commit();
    }

    /**
     * 写入string
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @return
     */
    public static boolean putString(@NonNull Context context, @Nullable String fileName,
                                    @NonNull String key, @Nullable String value) {

        if (fileName == null) {
            return getSharedPreferencesUtils().getEditor(context, DEFAULT_FILE_NAME)
                    .putString(key, value)
                    .commit();
        } else {
            return getSharedPreferencesUtils().getEditor(context, fileName)
                    .putString(key, value)
                    .commit();
        }


    }

    /***
     * 写入默认文件
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putString(@NonNull Context context, @NonNull String key, @Nullable String value) {

        return putString(context, null, key, value);
    }


    /**
     * 获取string
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(@NonNull Context context, @Nullable String fileName,
                                   @NonNull String key, @Nullable String defaultValue) {

        if (fileName == null) {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, DEFAULT_FILE_NAME)
                    .getString(key, defaultValue);
        } else {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, fileName)
                    .getString(key, defaultValue);
        }

    }

    /**
     * 获取string 没有自定义默认值
     *
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public static String getString(@NonNull Context context, @Nullable String fileName,
                                   @NonNull String key) {
        return getString(context, fileName, key, "");

    }

    /**
     * 获取默认文件 值
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(@NonNull Context context, @NonNull String key) {
        return getString(context, null, key, "");

    }


    /**
     * 写入int
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @return
     */
    public static boolean putInt(@NonNull Context context, @Nullable String fileName,
                                 @NonNull String key, @Nullable int value) {

        if (fileName == null) {
            return getSharedPreferencesUtils().getEditor(context, DEFAULT_FILE_NAME)
                    .putInt(key, value)
                    .commit();
        } else {
            return getSharedPreferencesUtils().getEditor(context, fileName)
                    .putInt(key, value)
                    .commit();
        }


    }

    /***
     * 写入默认文件
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putInt(@NonNull Context context, @NonNull String key, @Nullable int value) {

        return putInt(context, null, key, value);
    }


    /**
     * 获取int
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(@NonNull Context context, @Nullable String fileName,
                             @NonNull String key, @Nullable int defaultValue) {

        if (fileName == null) {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, DEFAULT_FILE_NAME)
                    .getInt(key, defaultValue);
        } else {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, fileName)
                    .getInt(key, defaultValue);
        }

    }

    /**
     * 获取int 没有自定义默认值
     *
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public static int getInt(@NonNull Context context, @Nullable String fileName,
                             @NonNull String key) {
        return getInt(context, fileName, key, 0);

    }

    /**
     * 获取默认文件 值
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(@NonNull Context context, @NonNull String key) {
        return getInt(context, null, key, 0);

    }


    /**
     * 写入int
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @return
     */
    public static boolean putFloat(@NonNull Context context, @Nullable String fileName,
                                   @NonNull String key, @Nullable float value) {

        if (fileName == null) {
            return getSharedPreferencesUtils().getEditor(context, DEFAULT_FILE_NAME)
                    .putFloat(key, value)
                    .commit();
        } else {
            return getSharedPreferencesUtils().getEditor(context, fileName)
                    .putFloat(key, value)
                    .commit();
        }


    }

    /***
     * 写入默认文件
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putFloat(@NonNull Context context, @NonNull String key, @Nullable float value) {

        return putFloat(context, null, key, value);
    }


    /**
     * 获取int
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getFloat(@NonNull Context context, @Nullable String fileName,
                                 @NonNull String key, @Nullable float defaultValue) {

        if (fileName == null) {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, DEFAULT_FILE_NAME)
                    .getFloat(key, defaultValue);
        } else {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, fileName)
                    .getFloat(key, defaultValue);
        }

    }

    /**
     * 获取int 没有自定义默认值
     *
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public static float getFloat(@NonNull Context context, @Nullable String fileName,
                                 @NonNull String key) {
        return getFloat(context, fileName, key, 0.0f);

    }

    /**
     * 获取默认文件 值
     *
     * @param context
     * @param key
     * @return
     */
    public static float getFloat(@NonNull Context context, @NonNull String key) {
        return getFloat(context, null, key, 0.0f);

    }

    /**
     * 写入Set<String>
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @return
     */
    public static boolean putSet(@NonNull Context context, @Nullable String fileName,
                                 @NonNull String key, @Nullable Set<String> value) {

        if (fileName == null) {
            return getSharedPreferencesUtils().getEditor(context, DEFAULT_FILE_NAME)
                    .putStringSet(key, value)
                    .commit();
        } else {
            return getSharedPreferencesUtils().getEditor(context, fileName)
                    .putStringSet(key, value)
                    .commit();
        }


    }

    /***
     * 写入默认文件
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putSet(@NonNull Context context, @NonNull String key, @Nullable Set<String> value) {

        return putSet(context, null, key, value);
    }


    /**
     * 获取Set<String>
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static Set<String> getSet(@NonNull Context context, @Nullable String fileName,
                                     @NonNull String key, @Nullable Set<String> defaultValue) {

        if (fileName == null) {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, DEFAULT_FILE_NAME)
                    .getStringSet(key, defaultValue);
        } else {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, fileName)
                    .getStringSet(key, defaultValue);
        }

    }

    /**
     * 获取Set<String> 没有自定义默认值
     *
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public static Set<String> getSet(@NonNull Context context, @Nullable String fileName,
                                     @NonNull String key) {
        return getSet(context, fileName, key);

    }

    /**
     * 获取默认文件 值
     *
     * @param context
     * @param key
     * @return
     */
    public static Set<String> getSet(@NonNull Context context, @NonNull String key) {
        return getSet(context, null, key, null);

    }

    /**
     * 写入Set<String>
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @return
     */
    public static boolean putBoolean(@NonNull Context context, @Nullable String fileName,
                                     @NonNull String key, @Nullable boolean value) {

        if (fileName == null) {
            return getSharedPreferencesUtils().getEditor(context, DEFAULT_FILE_NAME)
                    .putBoolean(key, value)
                    .commit();
        } else {
            return getSharedPreferencesUtils().getEditor(context, fileName)
                    .putBoolean(key, value)
                    .commit();
        }


    }

    /***
     * 写入默认文件
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putBoolean(@NonNull Context context, @NonNull String key, @Nullable boolean value) {

        return putBoolean(context, null, key, value);
    }


    /**
     * 获取Set<String>
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(@NonNull Context context, @Nullable String fileName,
                                     @NonNull String key, @Nullable boolean defaultValue) {

        if (fileName == null) {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, DEFAULT_FILE_NAME)
                    .getBoolean(key, defaultValue);
        } else {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, fileName)
                    .getBoolean(key, defaultValue);
        }

    }

    /**
     * 获取 Boolean 没有自定义默认值
     *
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public static boolean getBoolean(@NonNull Context context, @Nullable String fileName,
                                     @NonNull String key) {
        return getBoolean(context, fileName, key, false);

    }

    /**
     * 获取默认文件 值
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(@NonNull Context context, @NonNull String key) {
        return getBoolean(context, null, key, false);
    }

    /**
     * 写入long
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @return
     */
    public static boolean putLong(@NonNull Context context, @Nullable String fileName,
                                  @NonNull String key, @Nullable long value) {

        if (fileName == null) {
            return getSharedPreferencesUtils().getEditor(context, DEFAULT_FILE_NAME)
                    .putLong(key, value)
                    .commit();
        } else {
            return getSharedPreferencesUtils().getEditor(context, fileName)
                    .putLong(key, value)
                    .commit();
        }


    }

    /***
     * 写入默认文件
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putLong(@NonNull Context context, @NonNull String key, @Nullable long value) {

        return putLong(context, null, key, value);
    }


    /**
     * 获取long
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLong(@NonNull Context context, @Nullable String fileName,
                               @NonNull String key, @Nullable long defaultValue) {

        if (fileName == null) {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, DEFAULT_FILE_NAME)
                    .getLong(key, defaultValue);
        } else {
            return getSharedPreferencesUtils()
                    .getSharedPreferences(context, fileName)
                    .getLong(key, defaultValue);
        }

    }

    /**
     * 获取 Boolean 没有自定义默认值
     *
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public static long getLong(@NonNull Context context, @Nullable String fileName,
                               @NonNull String key) {
        return getLong(context, fileName, key, 0L);

    }

    /**
     * 获取默认文件 值
     *
     * @param context
     * @param key
     * @return
     */
    public static long getLong(@NonNull Context context, @NonNull String key) {
        return getLong(context, null, key, 0L);

    }


}
