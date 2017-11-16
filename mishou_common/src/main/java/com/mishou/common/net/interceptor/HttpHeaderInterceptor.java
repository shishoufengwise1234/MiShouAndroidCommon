package com.mishou.common.net.interceptor;

import android.support.annotation.NonNull;

import com.mishou.common.net.util.OnlyLog;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 * 添加请求头拦截器
 */

public class HttpHeaderInterceptor implements Interceptor{


    private Map<String,String> headers;

    public HttpHeaderInterceptor(@NonNull Map<String,String> headers){
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        OnlyLog.d("HttpHeaderInterceptor -> intercept()");

        Request.Builder builder = chain.request().newBuilder();
        if (headers.isEmpty())
            return chain.proceed(builder.build());


        try {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue()).build();
            }
        } catch (Exception e) {
            OnlyLog.e(e);
        }
        return chain.proceed(builder.build());
    }
}
