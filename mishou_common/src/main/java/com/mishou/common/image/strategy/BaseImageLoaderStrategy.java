package com.mishou.common.image.strategy;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by ${shishoufeng} on 17/11/8.
 * email:shishoufeng1227@126.com
 * <p>
 * 加载器
 */

public interface BaseImageLoaderStrategy {


    /**
     * 简单加载图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 简单加载图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     * @param width     宽
     * @param height    高
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, int width, int height);

    /**
     * 简单加载图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param imageView   img
     */
    void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                   @DrawableRes int errorRes, @NonNull ImageView imageView);

    /**
     * 简单加载图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                   @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView);

    /**
     * 简单加载 GIF 图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);


    /**
     * 简单加载 GIF 图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param imageView   img
     */
    void loadGifImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                      @DrawableRes int errorRes, @NonNull ImageView imageView);


    /**
     * 加载缩略图
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    void loadThumbnailImage(@NonNull Context context, @NonNull String url,@DrawableRes int placeholder,
                            @DrawableRes int errorRes, @DrawableRes int fallback,
                            @NonNull ImageView imageView,float sizeMultiplier);




    /**
     * 简单加载图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    void loadFitCenterImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                   @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView);
    /**
     * 简单加载图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    void loadCenterCropImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                   @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView);
    /**
     * 简单加载图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    void loadCenterInsideImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                   @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView);
    /**
     * 清除硬盘缓存
     *
     * @param context context
     */
    void clearImageDiskCache(@NonNull Context context);

    /**
     * 清除内存缓存
     *
     * @param context context
     */
    void clearImageMemoryCache(@NonNull Context context);

    //根据不同的内存状态，来响应不同的内存释放策略
    void trimMemory(Context context, int level);

    /**
     * 获取缓存大小
     *
     * @param context context
     * @return 缓存值
     */
    String getCacheSize(@NonNull Context context);
}
