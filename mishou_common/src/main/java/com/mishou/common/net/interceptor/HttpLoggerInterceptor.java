package com.mishou.common.net.interceptor;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.mishou.common.utils.LogUtils;
import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 * <p>
 * 自定义网络拦截器
 */

public class HttpLoggerInterceptor implements Interceptor {

    private static final String TAG = "OkHttp";

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private String tag;
    private boolean isPrint = false;

    private int level = NONE;

    /**
     * 不打印日志
     */
    public static final int NONE = 1;
    /**
     * 打印body日志
     */
    public static final int BODY = 3;

    //log 输出等级
    @IntDef({NONE, BODY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {
    }

    /**
     * 设置打印级别
     *
     * @param level 级别
     */
    public void setLevel(@Level int level) {
        this.level = level;
    }

    public HttpLoggerInterceptor(@NonNull String tag, boolean isPrint) {
        if (tag == null) {
            this.tag = TAG;
        } else {
            this.tag = tag;
        }
        this.isPrint = isPrint;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (isPrint) {

            if (level == NONE) { //不打印日志
                LogUtils.d("HttpLoggerInterceptor level = NONE" + level);

                return chain.proceed(request);
            } else {

                return printHttpLog(request, chain);
            }
        } else {
            return chain.proceed(request);
        }
    }

    /**
     * 打印log
     *
     * @param request
     * @param chain
     * @return
     * @throws IOException
     */
    private Response printHttpLog(Request request, Chain chain) throws IOException {

        //log 信息
        StringBuilder builder = new StringBuilder();

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        //上传文件不用打印
        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                if (!requestBody.contentType().toString().contains("application/json")) {
                    return chain.proceed(request);
                }
            }
        }

        String requestStartMessage = "请求方式----> " + request.method() + "\n请求地址: " + request.url() + "\nHttp 版本:" + protocol;

        builder.append(requestStartMessage);

        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                builder.append("\n请求头:----> Content-Type: " + requestBody.contentType());
            }
        }
        builder.append("\n请求参数:---start  ");
        builder.append("\nheader:   ");
        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                builder.append(name + ": " + headers.value(i));
            }
        }
        //body
        if (!bodyEncoded(request.headers())) {
            Buffer buffer = new Buffer();
            if (requestBody != null) {
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {
                    builder.append("\nbody:   ");

                    builder.append("\n" + buffer.readString(charset));
                }
            }
        }

        builder.append("\n请求参数:---end");

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        builder.append("\n<-- 请求code: " + response.code() + "  请求状态: " + response.message() + "  请求地址: "
                + response.request().url() + " 用时:(" + tookMs + "ms)");

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                builder.append("\nCouldn't decode the response body; charset is likely malformed.");
                builder.append("\n<-- END HTTP");

                return response;
            }
        }

        if (!isPlaintext(buffer)) {
            builder.append("\n<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
            return response;
        }

        if (contentLength != 0) {
            builder.append("\n返回数据:");
            builder.append("\n" + buffer.clone().readString(charset));
        }

        builder.append("\n<-- 请求结束 END HTTP (" + buffer.size() + "-byte body)");

        Logger.t(tag).d("请求信息如下:\n" + builder);

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    /**
     * 判断body 是否含有参数
     *
     * @param headers 请求头
     * @return true body 不含参数 false 则包含
     */
    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
