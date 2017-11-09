package com.mishou.common.image;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.mishou.common.image.strategy.BaseImageLoaderStrategy;
import com.mishou.common.image.strategy.GlideImageLoaderStrategy;

/**
 * Created by ${shishoufeng} on 17/11/7.
 * email:shishoufeng1227@126.com
 */

public class ImageLoader {

    private BaseImageLoaderStrategy loaderStrategy = null;

    private ImageLoader(){
        loaderStrategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoader getInstance(){
        return ImageLoaderBuilder.INSTANCE;
    }

    private static class ImageLoaderBuilder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }


    public void setLoaderStrategy(BaseImageLoaderStrategy loaderStrategy) {
        this.loaderStrategy = loaderStrategy;
    }


    /**
     * 简单加载图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView){

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadImage(context,url,imageView);
    }

    /**
     * 简单加载图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes 失败图
     * @param imageView   img
     */
    public void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                   @DrawableRes int errorRes, @NonNull ImageView imageView){

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        if (placeholder == 0 || errorRes == 0)
            return;

        loaderStrategy.loadImage(context,url,placeholder,errorRes,imageView);
    }

    /**
     * 简单加载 GIF 图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    public void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView){

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadGifImage(context,url,imageView);
    }


    /**
     * 简单加载 GIF 图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes 失败图
     * @param imageView   img
     */
    public void loadGifImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                      @DrawableRes int errorRes, @NonNull ImageView imageView){

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        if (placeholder == 0 || errorRes == 0)
            return;

        loaderStrategy.loadGifImage(context,url,placeholder,errorRes,imageView);
    }

    /**
     *
     *清除硬盘缓存
     * @param context  context
     */
    public void clearImageDiskCache(@NonNull Context context){

        if (context == null || loaderStrategy == null)
            return;

        loaderStrategy.clearImageDiskCache(context);
    }

    /**
     * 清除内存缓存
     * @param context context
     */
    public void clearImageMemoryCache(@NonNull Context context){

        if (context == null || loaderStrategy == null)
            return;

        loaderStrategy.clearImageMemoryCache(context);

    }
}
