package com.mishou.common.image.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;
import com.mishou.common.utils.LogUtils;
import com.orhanobut.logger.Logger;

import java.io.InputStream;

/**
 * Created by shishoufeng on 2018/1/8.
 * email:shishoufeng1227@126.com
 *
 * 自定义 glide 组件
 */

public class CommonModule extends LibraryGlideModule{

    private static final String TAG = "CommonModule";


    //硬盘缓存大小
    private static final int DEFAULT_DISK_CACHE_SIZE = 300 * 1024 * 1024;
    //缓存文件夹名称
    private static final String DEFAULT_DISK_CACHE_DIR = "image_manager_disk_cache";

//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//
//        LogUtils.d("applyOptions -- ");
//
//        Logger.d("applyOptions -- ");
//
//        Log.d(TAG,"applyOptions");
//
//        //设置 硬盘缓存
//        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context,DEFAULT_DISK_CACHE_DIR,DEFAULT_DISK_CACHE_SIZE));
//    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

        LogUtils.d("registerComponents -- ");

        Logger.d("registerComponents -- ");

        Log.d(TAG, "registerComponents: ");


        //替换 glide http 通信组件
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
