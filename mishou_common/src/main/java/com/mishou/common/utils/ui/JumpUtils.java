package com.mishou.common.utils.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 * <p>
 * 页面跳转 工具类
 */

public class JumpUtils {


    public static final String DEFAULT_DATA_KEY = "Data";


    /**
     * 自定义intent 跳转
     *
     * @param context
     * @param intent
     */
    public static void startActivity(@NonNull Context context, @NonNull Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转页面
     *
     * @param context
     * @param clazz
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz) {
        jumpPager(context, clazz, 0, false, null, null, 0, 0);
    }


    /***
     * 跳转页面
     *
     * @param context
     * @param clazz
     * @param flags
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @Nullable int flags) {
        jumpPager(context, clazz, flags, false, null, null, 0, 0);
    }

    /**
     * 跳转页面是否销毁本页
     *
     * @param context
     * @param clazz
     * @param flags
     * @param isFinish
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @Nullable int flags, boolean isFinish) {
        jumpPager(context, clazz, flags, isFinish, null, null, 0, 0);
    }

    /**
     * 跳转页面 传递数据
     *
     * @param context
     * @param clazz
     * @param flags
     * @param bundle
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @Nullable int flags, @Nullable Bundle bundle) {
        jumpPager(context, clazz, flags, false, null, bundle, 0, 0);
    }

    /**
     * 跳转页面 传递数据
     *
     * @param context
     * @param clazz
     * @param flags
     * @param isFinish
     * @param bundle
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @Nullable int flags, boolean isFinish, @Nullable Bundle bundle) {
        jumpPager(context, clazz, flags, isFinish, null, bundle, 0, 0);
    }

    /**
     * 跳转页面 自定义bundle key
     *
     * @param context
     * @param clazz
     * @param flags
     * @param isFinish
     * @param key
     * @param bundle
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @Nullable int flags, boolean isFinish, @Nullable String key, @Nullable Bundle bundle) {
        jumpPager(context, clazz, flags, isFinish, key, bundle, 0, 0);
    }

    /***
     * 增加转场动画
     *
     * @param context
     * @param clazz
     * @param flags
     * @param isFinish
     * @param key
     * @param bundle
     * @param enterAnim
     * @param exitAnim
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @Nullable int flags, boolean isFinish, @Nullable String key, @Nullable Bundle bundle, @IdRes int enterAnim, @IdRes int exitAnim) {
        jumpPager(context, clazz, flags, isFinish, key, bundle, enterAnim, exitAnim);
    }

    /**
     * 是否销毁本页
     *
     * @param context
     * @param clazz
     * @param isFinish
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, boolean isFinish) {
        jumpPager(context, clazz, 0, isFinish, null, null, 0, 0);
    }

    /**
     * 简单传递数据
     *
     * @param context
     * @param clazz
     * @param bundle
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @Nullable Bundle bundle) {
        jumpPager(context, clazz, 0, false, null, bundle, 0, 0);
    }

    /**
     * 是否销毁本页 传递数据
     *
     * @param context
     * @param clazz
     * @param isFinish
     * @param bundle
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, boolean isFinish, @Nullable Bundle bundle) {
        jumpPager(context, clazz, 0, isFinish, null, bundle, 0, 0);
    }

    /**
     * 自定义bundle key
     *
     * @param context
     * @param clazz
     * @param isFinish
     * @param key
     * @param bundle
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, boolean isFinish, String key, @Nullable Bundle bundle) {
        jumpPager(context, clazz, 0, isFinish, key, bundle, 0, 0);
    }

    /**
     * 自定义传递数据
     *
     * @param context
     * @param clazz
     * @param key
     * @param bundle
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, String key, @Nullable Bundle bundle) {
        jumpPager(context, clazz, 0, false, key, bundle, 0, 0);
    }

    /**
     * 增加转场动画
     *
     * @param context
     * @param clazz
     * @param enterAnim
     * @param exitAnim
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, @IdRes int enterAnim, @IdRes int exitAnim) {
        jumpPager(context, clazz, 0, false, null, null, enterAnim, exitAnim);
    }

    /**
     * 是否销毁本页 增加转场动画
     *
     * @param context
     * @param clazz
     * @param isFinish
     * @param enterAnim
     * @param exitAnim
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> clazz, boolean isFinish, @IdRes int enterAnim, @IdRes int exitAnim) {
        jumpPager(context, clazz, 0, isFinish, null, null, enterAnim, exitAnim);
    }


    /***
     * 跳转页面
     *
     * @param context   上下文对象
     * @param clazz     跳转页面
     * @param flags     意图
     * @param isFinish  是否销毁本页
     * @param key       bundle ---key
     * @param bundle    携带的bundle 数据
     * @param enterAnim 过度动画
     * @param exitAnim
     */
    private static void jumpPager(@NonNull Context context, @NonNull Class<?> clazz,
                                  @Nullable int flags, boolean isFinish, @Nullable String key, @Nullable Bundle bundle, @IdRes int enterAnim, @IdRes int exitAnim) {
        try {

            Intent intent = new Intent(context, clazz);

            if (flags != 0)
                intent.setFlags(flags);

            if (bundle != null) {
                if (key != null && !TextUtils.equals(key, "")) {
                    intent.putExtra(key, bundle);
                } else {
                    intent.putExtra(DEFAULT_DATA_KEY, bundle);
                }
            }

            context.startActivity(intent);

//            Activity activity = (Activity) context;
//
//            if (enterAnim != 0 && exitAnim != 0) {
//                activity.overridePendingTransition(enterAnim, exitAnim);
//            }

//            if (isFinish)
//                activity.finish();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
