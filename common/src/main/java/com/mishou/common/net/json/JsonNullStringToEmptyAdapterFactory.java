package com.mishou.common.net.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by shishoufeng on 2017/12/26.
 * email:shishoufeng1227@126.com
 *
 * json 数据转换器
 */

public class JsonNullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == String.class) {
            return (TypeAdapter<T>) new StringAdapter();
        }
        return null;
    }

    //注册string 类型转换器
    public static class StringAdapter extends TypeAdapter<String> {

        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {  //如果数据是null 转换成 ""
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}
