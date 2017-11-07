package com.mishou.common.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mishou.common.gson.NullStringToEmptyAdapterFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ${shishoufeng} on 16/8/28.
 * email:shishoufeng1227@126.com
 */
public class GsonUtils {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }


    /***
     * 对象转换成json数据
     *
     * @param ts
     * @return
     */
    public static String objectToJson(@NonNull Object ts) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(ts);
    }

    /***
     * 按照type 转换成json
     *
     * @param ts
     * @param type
     * @return
     */
    public static String objectToJson(@NonNull Object ts, @NonNull Type type) {
        String jsonStr;
        if (gson == null) {
            gson = new Gson();
        }
        jsonStr = gson.toJson(ts, type);
        return jsonStr;
    }


    /**
     * 序列化对象数据
     *
     * @param ts
     * @param dateformat
     * @return
     */
    public static String objectToJsonDateSerializer(@NonNull Object ts, @NonNull final String dateformat) {
        String jsonStr = null;
        gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Date.class,
                        new JsonSerializer<Date>() {
                            public JsonElement serialize(Date src,
                                                         Type typeOfSrc,
                                                         JsonSerializationContext context) {
                                SimpleDateFormat format = new SimpleDateFormat(dateformat);
                                return new JsonPrimitive(format.format(src));
                            }
                        }).setDateFormat(dateformat).create();
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }


    /**
     * 解析成集合数据
     *
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(@NonNull String jsonStr) {
        List<?> objList = null;
        if (gson == null) {
            gson = new Gson();
        }
        Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
        }.getType();
        objList = gson.fromJson(jsonStr, type);
        return objList;
    }


    /**
     * 按照type 解析成集合数据
     *
     * @param jsonStr
     * @param type
     * @return
     */
    public static List<?> jsonToList(@NonNull String jsonStr, @NonNull Type type) {
        List<?> objList;
        if (gson == null) {
            gson = new Gson();
        }
        objList = gson.fromJson(jsonStr, type);
        return objList;
    }


    /**
     * 解析成map集合
     *
     * @param jsonStr
     * @return
     */
    public static Map<?, ?> jsonToMap(@NonNull String jsonStr) {
        Map<?, ?> objMap;
        if (gson == null) {
            gson = new Gson();
        }
        Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
        }.getType();
        objMap = gson.fromJson(jsonStr, type);
        return objMap;
    }

    /**
     * 解析成JavaBean
     *
     * @param jsonStr
     * @param cl
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(@NonNull String jsonStr, @NonNull Class<T> cl) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(jsonStr, cl);
    }
    /**
     * 解析成JavaBean
     *
     * @param g
     * @param jsonStr
     * @param cl
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(Gson g, String jsonStr, Class<T> cl) {
        T obj = null;
        if (g != null) {
            obj = g.fromJson(jsonStr, cl);
        }else if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }else{
            gson = new Gson();
            obj = gson.fromJson(jsonStr, cl);
        }
        return obj;
    }
    /**
     * 解析成JavaBean 可将字符串的null字符编程""
     *
     * @param jsonStr
     * @param cl
     * @param <T>
     * @return
     */
    public static <T> T jsonToBeanNullStringToEmpty(String jsonStr, Class<T> cl) {
        Gson gson  = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        return gson.fromJson(jsonStr, cl);
    }


    /**
     * 解析序列化数据
     *
     * @param jsonStr
     * @param cl
     * @param pattern
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBeanDateSerializer(@NonNull String jsonStr, @NonNull Class<T> cl,
                                                 @NonNull final String pattern) {
        Object obj = null;
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context)
                            throws JsonParseException {
                        SimpleDateFormat format = new SimpleDateFormat(pattern);
                        String dateStr = json.getAsString();
                        try {
                            return format.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).setDateFormat(pattern).create();
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return (T) obj;
    }


    /**
     * 根据指定key解析数据
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static Object getJsonValue(@NonNull String jsonStr, @NonNull String key) {
        Object rulsObj = null;
        Map<?, ?> rulsMap = jsonToMap(jsonStr);
        if (rulsMap != null && rulsMap.size() > 0) {
            rulsObj = rulsMap.get(key);
        }
        return rulsObj;
    }


}
