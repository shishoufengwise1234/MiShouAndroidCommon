package com.mishou.common.image;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mishou.common.image.strategy.BaseImageLoaderStrategy;
import com.mishou.common.image.strategy.GlideImageLoaderStrategy;
import com.mishou.common.utils.FileUtils;
import com.mishou.common.utils.StringUtils;

import java.io.File;

/**
 * Created by ${shishoufeng} on 17/11/7.
 * email:shishoufeng1227@126.com
 * <p>
 * <p>
 * 图片加载器
 */
@SuppressWarnings("all")
public class ImageLoader {

    public static final String TAG = "ImageLoader";

    private BaseImageLoaderStrategy loaderStrategy = null;

    private ImageLoader() {
        loaderStrategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoader getInstance() {
        return ImageLoaderBuilder.INSTANCE;
    }

    private static class ImageLoaderBuilder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }


    public void setLoaderStrategy(BaseImageLoaderStrategy loaderStrategy) {
        this.loaderStrategy = loaderStrategy;
    }


    /**
     * 是否开启debug 模式
     *
     * @param isDebug true is debug
     */
    public void setDebug(Context context, boolean isDebug) {

        if (context == null || loaderStrategy == null) return;

        loaderStrategy.setDebug(context, isDebug);
    }


    /**
     * 简单加载图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadImage(context, url, imageView);
    }

    /**
     * 简单加载图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     * @param width     宽
     * @param height    高
     */
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, int width, int height) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadImage(context, url, imageView, width, height);
    }

    /**
     * 简单加载图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param imageView   img
     */
    public void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                          @DrawableRes int errorRes, @NonNull ImageView imageView) {


        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadImage(context, url, placeholder, errorRes, imageView);
    }

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
    public void loadImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                          @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadImage(context, url, placeholder, errorRes, fallback, imageView);
    }

    /**
     * 简单加载 GIF 图片
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    public void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadGifImage(context, url, imageView);
    }


    /**
     * 简单加载 GIF 图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param imageView   img
     */
    public void loadGifImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                             @DrawableRes int errorRes, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadGifImage(context, url, placeholder, errorRes, imageView);
    }


    /**
     * 加载缩略图
     *
     * @param context   上下文对象
     * @param url       图片地址
     * @param imageView img
     */
    public void loadThumbnailImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                                   @DrawableRes int errorRes, @DrawableRes int fallback,
                                   @NonNull ImageView imageView, float sizeMultiplier) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadThumbnailImage(context, url, placeholder, errorRes, fallback, imageView, sizeMultiplier);
    }


    /**
     * 指定 fitCenter
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    public void loadFitCenterImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                                   @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadFitCenterImage(context, url, placeholder, errorRes, fallback, imageView);
    }

    /**
     * 简单加载图片  CenterCrop
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    public void loadCenterCropImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                                    @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadCenterCropImage(context, url, placeholder, errorRes, fallback, imageView);
    }

    /**
     * 简单加载图片  CenterInside
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    public void loadCenterInsideImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                                      @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadCenterInsideImage(context, url, placeholder, errorRes, fallback, imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context     上下文对象
     * @param url         图片地址
     * @param placeholder 加载显示图
     * @param errorRes    失败图
     * @param fallback    备选图
     * @param imageView   img
     */
    public void loadCircleImage(@NonNull Context context, @NonNull String url, @DrawableRes int placeholder,
                                @DrawableRes int errorRes, @DrawableRes int fallback, @NonNull ImageView imageView) {

        if (context == null || url == null || imageView == null || loaderStrategy == null)
            return;

        loaderStrategy.loadCircleImage(context, url, placeholder, errorRes, fallback, imageView);
    }

    /**
     * 根据不同模式释放 内存
     *
     * @param context context
     * @param level   级别
     */
    public void trimMemory(Context context, int level) {

        if (context == null || loaderStrategy == null)
            return;

        loaderStrategy.trimMemory(context, level);

    }

    /**
     * 获取缓存大小
     *
     * @param context context
     * @return 缓存值
     */
    public String getCacheSize(@NonNull Context context) {

        if (context == null || loaderStrategy == null)
            return "";

        return loaderStrategy.getCacheSize(context);
    }

    /**
     * 清除硬盘缓存
     *
     * @param context context
     */
    public void clearImageDiskCache(@NonNull Context context) {

        if (context == null || loaderStrategy == null)
            return;

        loaderStrategy.clearImageDiskCache(context);
    }

    /**
     * 清除内存缓存
     *
     * @param context context
     */
    public void clearImageMemoryCache(@NonNull Context context) {

        if (context == null || loaderStrategy == null)
            return;

        loaderStrategy.clearImageMemoryCache(context);

    }

    /**
     * 清除所有缓存数据
     *
     * @param context            上下文对象
     * @param clearCacheListener 清除回调
     */
    public void clearAllCache(Context context, ClearCacheListener clearCacheListener) {
        try {

            clearImageDiskCache(context);
            clearImageMemoryCache(context);

            deleteCache(context, clearCacheListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 清除所有缓存数据
     *
     * @param context            上下文对象
     */
    public void clearAllCache(Context context) {
        try {

            clearImageDiskCache(context);
            clearImageMemoryCache(context);

            deleteCache(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除 cache 路径下所有数据
     *
     * @param context 上下文对象
     */
    public void deleteCache(Context context) {
        this.deleteCache(context, null);
    }

    /**
     * 删除 cache 路径下所有数据
     *
     * @param context            上下文对象
     * @param clearCacheListener 清除回调
     */
    public void deleteCache(Context context, ClearCacheListener clearCacheListener) {
        if (context == null)
            return;

        File cacheFile = Glide.getPhotoCacheDir(context);
        if (cacheFile != null) {
            String cachePath = cacheFile.getPath();

            if (StringUtils.isEmpty(cachePath)) {
                if (clearCacheListener != null)
                    clearCacheListener.clearCacheFailure();
            }
            boolean isSuccess = FileUtils.deleteAllInDir(cachePath);
            if (isSuccess) {
                if (clearCacheListener != null)
                    clearCacheListener.clearCacheSuccess();
            } else {
                if (clearCacheListener != null)
                    clearCacheListener.clearCacheFailure();
            }
        } else {
            if (clearCacheListener != null)
                clearCacheListener.clearCacheFailure();
        }

    }

    public interface ClearCacheListener {

        void clearCacheFailure();

        void clearCacheSuccess();
    }

}
