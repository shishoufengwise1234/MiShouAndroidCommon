package com.mishou.common.image.strategy;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by ${shishoufeng} on 17/11/8.
 * email:shishoufeng1227@126.com
 *
 *
 * glide 实现
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {



    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,@DrawableRes int errorRes, @NonNull ImageView imageView) {

        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.error(errorRes);


        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    @Override
    public void loadGifImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, @NonNull ImageView imageView) {

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
