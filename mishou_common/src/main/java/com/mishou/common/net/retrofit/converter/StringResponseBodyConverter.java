package com.mishou.common.net.retrofit.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {


    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
