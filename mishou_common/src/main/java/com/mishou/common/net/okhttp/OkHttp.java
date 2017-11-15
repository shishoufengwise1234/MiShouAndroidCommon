package com.mishou.common.net.okhttp;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mishou.common.net.file.download.FileResponseBody;
import com.mishou.common.net.interceptor.HttpLoggerInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${shishoufeng} on 17/11/7.
 * email:shishoufeng1227@126.com
 *
 * OkHttpClient 实例封装
 */
public class OkHttp {

    private static final String TAG = "OkHttp";
    //连接超时时间
    private static final long CONNECT_TIMEOUT = 30;
    //读取超时时间
    private static final long READ_TIMEOUT = 40;
    //写入超时时间
    private static final long WRITE_TIMEOUT = 40;

    /**
     * 设置缓存大小
     */
    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    //自定义网络拦截器
    private List<Interceptor> interceptors = new ArrayList<>();

    private static OkHttpClient okHttpClient = null;

    /***
     * 获取OkHttpClient
     *
     * @param context
     * @return
     */
    public static OkHttpClient getDefaultOkHttpClient(@NonNull final Context context) {

        if (okHttpClient != null)
            return okHttpClient;

        /**
         * 设置缓存路径
         */
        final File baseDir = context.getCacheDir();
        File cacheDir = null;
        if (baseDir != null) {
            cacheDir = new File(baseDir, "HttpResponseCache");
        } else {
            if (hasSystemSDCord()) {
                cacheDir = new File(Environment.getExternalStorageDirectory(), "HttpResponseCache");
            } else {
                Log.d(TAG, "getDefaultOkHttpClient() cache path == null");
            }
        }

        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new HeaderInterceptor())   //自定义header
//                .addInterc、eptor(new TokenInterceptor())  //自定义token拦截器
                .addInterceptor(new HttpLoggerInterceptor(null,true)) //自定义 logger 拦截器
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE))
                .retryOnConnectionFailure(true)//错误重连
                .build();


        return okHttpClient;
    }


    /**
     * 判断是否含有sdcard
     *
     * @return
     */
    private static boolean hasSystemSDCord() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    //连接超时时间
    private static final long CONNECT_TIMEOUT_FILE = 15;
    //读取超时时间
    private static final long READ_TIMEOUT_FILE = 15;
    //写入超时时间
    private static final long WRITE_TIMEOUT_FILE = 20;

    /**
     * 获取文件下载client
     *
     * @return
     */
    public static OkHttpClient getFileOkHttpClient() {

        OkHttpClient fileClient = null;
        if (fileClient != null)
            return fileClient;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.addInterceptor(new Interceptor() {
                    /**
                     *
                     * 添加网络拦截器统一添加header
                     * @param chain
                     * @return
                     * @throws IOException
                     */
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "Application/json")
                                .build();
                        return chain.proceed(request);

                    }
                });
                builder.networkInterceptors().add(new Interceptor() {
            /**
             * 添加文件拦截器
             * @param chain
             * @return
             * @throws IOException
             */
            @Override
            public Response intercept(Chain chain) throws IOException {

                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        builder.connectTimeout(CONNECT_TIMEOUT_FILE, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_FILE, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_FILE, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        fileClient = builder.build();

        return fileClient;
    }

    /**
     * 获取文件上传client
     *
     * @return
     */
    public static OkHttpClient getUploadFileOkHttpClient() {

        OkHttpClient fileClient = null;
        if (fileClient != null)
            return fileClient;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            /**
             *
             * 添加网络拦截器统一添加header
             * @param chain
             * @return
             * @throws IOException
             */
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "Application/json")
                        .build();

                return chain.proceed(request);
            }
        });
        builder.connectTimeout(CONNECT_TIMEOUT_FILE, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_FILE, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_FILE, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        fileClient = builder.build();

        return fileClient;
    }

}
