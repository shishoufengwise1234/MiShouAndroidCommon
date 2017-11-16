package com.mishou.common.net.callback;

import android.support.annotation.NonNull;

import com.google.gson.internal.$Gson$Types;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.util.ClazzUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 回调代理
 */

public abstract class CallBackProxy<T extends ApiResult<R>,R> implements IType<T>{

    CallBack<R> mCallBack;

    public CallBackProxy(@NonNull CallBack<R> callBack) {
        this.mCallBack = callBack;
    }

    public CallBack getCallBack() {
        return this.mCallBack;
    }

    @Override
    public Type getType() {//CallBack代理方式，获取需要解析的Type

        Type typeArguments = null;
        if (mCallBack != null) {
            Type rawType = mCallBack.getRawType();//如果用户的信息是返回List需单独处理
            if (List.class.isAssignableFrom(ClazzUtils.getClass(rawType, 0))
                    || Map.class.isAssignableFrom(ClazzUtils.getClass(rawType, 0))) {

                typeArguments = mCallBack.getType();
            } else {
                Type type = mCallBack.getType();
                typeArguments = ClazzUtils.getClass(type, 0);
            }
        }
        if (typeArguments == null) {
            typeArguments = ResponseBody.class;
        }
        Type rawType = ClazzUtils.findNeedType(getClass());

        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }
        return $Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments);
    }
}
