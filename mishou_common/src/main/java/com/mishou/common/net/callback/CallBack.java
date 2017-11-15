package com.mishou.common.net.callback;

import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.util.ClazzUtils;

import java.lang.reflect.Type;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 数据回调
 */

public abstract class CallBack<T> implements IType<T>{

    public abstract void onStart();

    public abstract void onCompleted();

    public abstract void onError(ApiException e);

    public abstract void onSuccess(T t);


    @Override
    public Type getType() {
        return ClazzUtils.findNeedClass(getClass());
    }

    public Type getRawType() {//获取需要解析的泛型T raw类型
        return ClazzUtils.findRawType(getClass());
    }
}
