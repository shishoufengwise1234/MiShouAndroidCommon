package com.mishou.common.image.strategy;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by ${shishoufeng} on 17/11/8.
 * email:shishoufeng1227@126.com
 *
 *
 * glide 实现
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    //默认添加view 渐入动画
    private DrawableTransitionOptions withCrossFade = DrawableTransitionOptions.withCrossFade();

    private RequestOptions fitCenter = RequestOptions.fitCenterTransform(),
            centerCrop = RequestOptions.centerCropTransform(),
            centerInside = RequestOptions.centerInsideTransform();



    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade)
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, int width, int height) {

        RequestOptions options = new RequestOptions()
                .override(width,height)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存修改过的图片

        Glide.with(context)
                .load(url)
                .transition(withCrossFade)
                .apply(options)
                .into(imageView);

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,@DrawableRes int errorRes, @NonNull ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(errorRes)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(url)
                .transition(withCrossFade)
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
                .transition(withCrossFade)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

        Glide.with(context)
                .asGif()
                .load(url)
                .transition(withCrossFade)
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
                .transition(withCrossFade)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadThumbnailImage(@NonNull Context context, @NonNull String url,@DrawableRes int placeholder,
                                   @DrawableRes int errorRes, @DrawableRes int fallback,
                                   @NonNull ImageView imageView,float sizeMultiplier) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(errorRes)
                .fallback(fallback)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(url)
                .thumbnail(sizeMultiplier)
                .transition(withCrossFade)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadFitCenterImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {


        Glide.with(context)
                .load(url)
                .transition(withCrossFade)
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
                .transition(withCrossFade)
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
                .transition(withCrossFade)
                .apply(centerInside.placeholder(placeholder)
                        .error(errorRes)
                        .fallback(fallback)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }


    @Override
    public void clearImageDiskCache(@NonNull Context context) {

    }

    @Override
    public void clearImageMemoryCache(@NonNull Context context) {

    }

    @Override
    public void trimMemory(Context context, int level) {

    }

    @Override
    public String getCacheSize(@NonNull Context context) {
        return null;
    }
}
