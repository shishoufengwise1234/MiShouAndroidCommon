package com.mishou.common.image.strategy;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mishou.common.BuildConfig;
import com.mishou.common.image.ImageLoader;
import com.mishou.common.thread.ExecutorServiceUtils;
import com.mishou.common.utils.FileUtils;


/**
 * Created by ${shishoufeng} on 17/11/8.
 * email:shishoufeng1227@126.com
 * <p>
 * <p>
 * glide 实现
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    //默认添加view 渐入动画
//    private DrawableTransitionOptions withCrossFade = DrawableTransitionOptions.withCrossFade();

    private RequestOptions fitCenter = RequestOptions.fitCenterTransform(),
            centerCrop = RequestOptions.centerCropTransform(),
            centerInside = RequestOptions.centerInsideTransform(),
            circleImage = RequestOptions.circleCropTransform();


    @Override
    public void setDebug(Context context, boolean isDebug) {

        if (isDebug) {
            new GlideBuilder().setLogLevel(Log.DEBUG).build(context);
        } else {
            if (BuildConfig.DEBUG) Log.d(ImageLoader.TAG, "setDebug: ");
        }
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, int width, int height) {

        RequestOptions options = new RequestOptions()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存修改过的图片

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder, @DrawableRes int errorRes, @NonNull ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(errorRes)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(errorRes)
                .fallback(fallback)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

        Glide.with(context)
                .asGif()
                .load(url)
                .into(imageView);

    }

    @Override
    public void loadGifImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, @NonNull ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(errorRes)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .asGif()
                .load(url)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadThumbnailImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                                   @DrawableRes int errorRes, @DrawableRes int fallback,
                                   @NonNull ImageView imageView, float sizeMultiplier) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(errorRes)
                .fallback(fallback)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(url)
                .thumbnail(sizeMultiplier)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadFitCenterImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {


        Glide.with(context)
                .load(url)
                .apply(fitCenter.placeholder(placeholder)
                        .error(errorRes)
                        .fallback(fallback)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

    @Override
    public void loadCenterCropImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {


        Glide.with(context)
                .load(url)
                .apply(centerCrop.placeholder(placeholder)
                        .error(errorRes)
                        .fallback(fallback)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }

    @Override
    public void loadCenterInsideImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {

        Glide.with(context)
                .load(url)
                .apply(centerInside.placeholder(placeholder)
                        .error(errorRes)
                        .fallback(fallback)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }

    @Override
    public void loadCircleImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {


        Glide.with(context)
                .load(url)
                .apply(circleImage.placeholder(placeholder)
                        .error(errorRes)
                        .fallback(fallback)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }


    @Override
    public void clearImageDiskCache(final @NonNull Context context) {

        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                ExecutorServiceUtils.submit(new Runnable() {  //提交到线程池中处理
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();  //清除硬盘缓存为耗时操作需开启子线程
                    }
                });
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();  //清除硬盘缓存为耗时操作需开启子线程
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clearImageMemoryCache(@NonNull Context context) {

        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context).clearMemory();
            } else {
                Log.e(ImageLoader.TAG, "clearImageMemoryCache:  looper is thread");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trimMemory(Context context, int level) {

        Glide.get(context.getApplicationContext()).trimMemory(level);
    }

    @Override
    public String getCacheSize(@NonNull Context context) {

        try {

            return FileUtils.getFileSize(Glide.getPhotoCacheDir(
                    context.getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
