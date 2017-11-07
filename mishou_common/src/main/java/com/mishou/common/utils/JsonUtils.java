package com.mishou.common.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Created by ${shishoufeng} on 16/8/28.
 * email:shishoufeng1227@126.com
 */
public class JsonUtils {

    /**
     * 讲map封装成json数据
     * @param data
     * @return
     */
    public static JSONObject map2Json(Map<?, ?> data) {
        JSONObject object = new JSONObject();

        for (Map.Entry<?, ?> entry : data.entrySet()) {
            String key = (String) entry.getKey();
            if (key == null) {
                throw new NullPointerException("key == null");
            }
            try {
                object.put(key, wrap(entry.getValue()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return object;
    }

    /**
     * 讲collection 集合装换成json数据
     * @param data
     * @return
     */
    public static JSONArray collection2Json(Collection<?> data) {
        JSONArray jsonArray = new JSONArray();
        if (data != null) {
            for (Object aData : data) {
                jsonArray.put(wrap(aData));
            }
        }
        return jsonArray;
    }

    /**
     * 将对象转换成json数据
     * @param data
     * @return
     * @throws JSONException
     */
    public static JSONArray object2Json(Object data) throws JSONException {
        if (!data.getClass().isArray()) {
            throw new JSONException("Not a primitive data: " + data.getClass());
        }
        final int length = Array.getLength(data);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < length; ++i) {
            jsonArray.put(wrap(Array.get(data, i)));
        }

        return jsonArray;
    }

    private static Object wrap(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return collection2Json((Collection<?>) o);
            } else if (o.getClass().isArray()) {
                return object2Json(o);
            }
            if (o instanceof Map) {
                return map2Json((Map<?, ?>) o);
            }

            if (o instanceof Boolean || o instanceof Byte || o instanceof Character || o instanceof Double || o instanceof Float || o instanceof Integer || o instanceof Long
                    || o instanceof Short || o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 讲string转换json数据
     * @param json
     * @return
     */
    public static JSONObject string2JSONObject(String json) {
        JSONObject jsonObject = null;
        try {
            JSONTokener jsonParser = new JSONTokener(json);
            jsonObject = (JSONObject) jsonParser.nextValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
