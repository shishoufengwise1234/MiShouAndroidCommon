package com.mishou.common.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 * <p>
 * 不进行添加缓存 拦截器
 */

public class NoCacheInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .header("Cache-Control", "no-cache")
                .build();
        Response originalResponse = chain.proceed(request);
        originalResponse = originalResponse.newBuilder()
                .header("Cache-Control", "no-cache")
                .build();

        return originalResponse;
    }
}
