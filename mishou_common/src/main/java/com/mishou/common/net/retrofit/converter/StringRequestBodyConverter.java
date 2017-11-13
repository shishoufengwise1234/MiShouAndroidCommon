package com.mishou.common.net.retrofit.converter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class StringRequestBodyConverter implements Converter<String, RequestBody> {


    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");


    @Override
    public RequestBody convert(String value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, value);
    }
}
