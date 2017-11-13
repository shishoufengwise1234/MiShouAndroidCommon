package com.mishou.common.net.retrofit.converter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class JsonRequestBodyConverter<T> implements Converter<JSONObject, RequestBody> {


    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(JSONObject value) throws IOException {
        return RequestBody.create(MEDIA_TYPE,value.toString());
    }
}
