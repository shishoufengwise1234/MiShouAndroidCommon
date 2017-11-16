package com.mishou.common.net.interceptor;

import com.mishou.common.net.callback.body.FileResponseBody;
import com.mishou.common.net.util.OnlyLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 * 下载文件拦截器
 */

public class DownloadInterceptor implements Interceptor{


    @Override
    public Response intercept(Chain chain) throws IOException {

        OnlyLog.d("DownloadInterceptor > intercept()");

        Response originalResponse = chain.proceed(chain.request());

        return originalResponse
                .newBuilder()
                .body(new FileResponseBody(originalResponse))
                .build();

    }
}
