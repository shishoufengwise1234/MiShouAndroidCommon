package com.mishou.common.image.strategy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by ${shishoufeng} on 17/11/8.
 * email:shishoufeng1227@126.com
 */

public class PicassoImageLoaderStrategy implements BaseImageLoaderStrategy {



    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, @NonNull ImageView imageView) {

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
