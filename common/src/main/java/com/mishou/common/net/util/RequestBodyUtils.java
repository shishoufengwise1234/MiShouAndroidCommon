package com.mishou.common.net.util;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 请求body 工具类
 */

public class RequestBodyUtils {

    public static final String MULTIPART_FORM_DATA = "image/jpg";

    public static final String FILE_MULTIPART_FORM_DATA = "multipart/form-data";


    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    /**
     * 根据json数据生成 RequestBody
     * @param jsonString json 数据
     * @return RequestBody
     */
    public static RequestBody createJson(String jsonString) {
        OnlyUtils.checkNotNull(jsonString, "json not null!");
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
    }

    /**
     * @param name
     * @return
     */
    public static RequestBody createFile(String name) {
        OnlyUtils.checkNotNull(name, "name not null!");
        return RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), name);
    }

    /**
     * @param file
     * @return
     */
    public static RequestBody createFile(File file) {
        OnlyUtils.checkNotNull(file, "file not null!");
        return RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
    }

    /**
     * @param file
     * @return
     */
    public static RequestBody createImage(File file) {
        OnlyUtils.checkNotNull(file, "file not null!");
        return RequestBody.create(MediaType.parse("image/jpg; charset=utf-8"), file);
    }

    /**
     * 根据字节数组生成请求体
     * @param bytes
     * @return
     */
    public static RequestBody createBytes(byte[] bytes){
        OnlyUtils.checkNotNull(bytes,"bytes[]  is null");
        return RequestBody.create(MediaType.parse("application/octet-stream"),bytes);
    }
    /**
     * 根据字符串生成请求体
     * @param content
     * @return
     */
    public static RequestBody createText(String content){
        OnlyUtils.checkNotNull(content,"content  is null");
        return RequestBody.create(MediaType.parse("text/plain"), content);
    }

    /**
     * 获取MultipartBody.Part 对象 针对retrofit 上传文件
     *
     * @param partName
     * @param filePath
     * @return
     */
    public static MultipartBody.Part preparserPart(@NonNull String partName, @NonNull String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = MultipartBody.create(MediaType.parse(FILE_MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    /**
     * 获取MultipartBody.Part 对象 针对retrofit 上传文件
     *
     * @param partName
     * @param filePath
     * @return
     */
    public static MultipartBody.Part preparserFilePart(@NonNull String partName, @NonNull String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = MultipartBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    /**
     * 添加请求头
     *
     * @param builder
     * @param headers
     */
    public static Request.Builder appendHeaders(Request.Builder builder, Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty())
            return null;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        return builder.headers(headerBuilder.build());
    }

    /***
     * 为retrofit 上传文件 拼接参数
     *
     * @param filePath
     * @return
     */
    public static Map<String, RequestBody> preparserFileRequest(List<String> filePath) {

        Map<String, RequestBody> map = new HashMap<>();

        for (int i = 0; i < filePath.size(); i++) {
            File file = new File(filePath.get(i));
            map.put("file" + i + "\";filename=\"" + file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath.get(i))));
        }
        return map;
    }

    /**
     * 为retrofit 上传文件 拼接参数
     * @param file
     * @return
     */
    public static Map<String, RequestBody> preparserFileRequest(File file) {

        Map<String, RequestBody> map = new HashMap<>();

        map.put("file" + 1 + "\";filename=\"" + file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        return map;
    }


}
