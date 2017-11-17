package com.mishou.common.net.function;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.util.ClazzUtils;
import com.mishou.common.net.util.OnlyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 * <p>
 * <p>
 * 对结果进行转换 APIResult / 其他
 */

public class ApiResultFunction<T> implements Function<ResponseBody, ApiResult<T>> {


    private Gson mGson = null;
    private Type type;

    public ApiResultFunction(@Nullable Gson gson, @NonNull Type type) {
        if (gson == null) {
            this.mGson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .serializeNulls()
                    .create();
        } else {
            this.mGson = gson;
        }
        this.type = type;
    }


    /**
     * 添加默认数据转换
     * @param responseBody 响应数据
     * @return  ApiResult
     * @throws Exception
     */
    @Override
    public ApiResult<T> apply(@io.reactivex.annotations.NonNull ResponseBody responseBody) throws Exception {

        OnlyLog.d("ApiResultFunction -> apply()");

        ApiResult<T> apiResult = new ApiResult<>();

        apiResult.setCode(-1);

        if (type instanceof ParameterizedType) {    //自定义ApiResult

            final Class<T> cls = (Class<T>) ((ParameterizedType) type).getRawType();

            //判断是否子类是否继承了 ApiResult
            if (ApiResult.class.isAssignableFrom(cls)) {
                final Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                final Class clazz = ClazzUtils.getClass(params[0], 0);
                final Class rawType = ClazzUtils.getClass(type, 0);

                try {
                    String json = responseBody.string();
                    //增加是List<String>判断错误的问题
                    if (!List.class.isAssignableFrom(rawType) && clazz.equals(String.class)) {
                        apiResult.setData((T) json);
                        apiResult.setCode(0);

                    } else {
                        ApiResult result = mGson.fromJson(json, type);
                        if (result != null) {
                            apiResult = result;
                        } else {
                            apiResult.setMsg("json is null");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    apiResult.setMsg(e.getMessage());
                } finally {
                    responseBody.close();
                }
            } else {
                apiResult.setMsg("ApiResult.class.isAssignableFrom(cls) err!!");
            }
        } else {//默认ApiResult 格式数据
            try {
                final String json = responseBody.string();
                final Class<T> clazz = ClazzUtils.getClass(type, 0);

                //目标数据 string 类型
                if (clazz.equals(String.class)) {

                    final ApiResult result = parseApiResult(json, apiResult);
                    if (result != null) {
                        apiResult = result;
                        apiResult.setData((T) json);
                    } else {
                        apiResult.setMsg("json is null");
                    }
                } else {

                    final ApiResult result = parseApiResult(json, apiResult);
                    if (result != null) {
                        apiResult = result;
                        if (apiResult.getData() != null) {
                            T data = mGson.fromJson(apiResult.getData().toString(), clazz);
                            apiResult.setData(data);
                        } else {
                            apiResult.setMsg("ApiResult's data is null");
                        }
                    } else {
                        apiResult.setMsg("json is null");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                apiResult.setMsg(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                apiResult.setMsg(e.getMessage());
            } finally {
                responseBody.close();
            }
        }
        return apiResult;
    }

    /**
     * 解析 开头 json 数据
     * @param json json
     * @param apiResult ApiResult
     * @return  ApiResult
     */
    private ApiResult parseApiResult(String json, ApiResult apiResult) throws JSONException {
        if (TextUtils.isEmpty(json))
            return null;

        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("code")) {
            apiResult.setCode(jsonObject.getInt("code"));
        }
        if (jsonObject.has("data")) {
            apiResult.setData(jsonObject.getString("data"));
        }
        if (jsonObject.has("msg")) {
            apiResult.setMsg(jsonObject.getString("msg"));
        }
        return apiResult;
    }
}
