package com.mishou.common.image.strategy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by ${shishoufeng} on 17/11/8.
 * email:shishoufeng1227@126.com
 */

public class PicassoImageLoaderStrategy implements BaseImageLoaderStrategy{


    @Override
    public void setDebug(Context context, boolean isDebug) {

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, int width, int height) {

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, @NonNull ImageView imageView) {

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {

    }

    @Override
    public void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    @Override
    public void loadGifImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, @NonNull ImageView imageView) {

    }

    @Override
    public void loadThumbnailImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView, float sizeMultiplier) {

    }

    @Override
    public void loadFitCenterImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {

    }

    @Override
    public void loadCenterCropImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {

    }

    @Override
    public void loadCenterInsideImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {

    }

    @Override
    public void loadCircleImage(@NonNull Context context, @NonNull String url, int placeholder, int errorRes, int fallback, @NonNull ImageView imageView) {

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
