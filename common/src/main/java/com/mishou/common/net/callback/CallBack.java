package com.mishou.common.net.callback;

import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.util.ClazzUtils;
import com.mishou.common.net.util.OnlyLog;

import java.lang.reflect.Type;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 数据回调
 */

public abstract class CallBack<T> implements IType<T>{

    /**
     * 开始订阅时调用
     */
    public abstract void onStart();

    /**
     * 完成之后回调
     */
    public abstract void onCompleted();

    /**
     * 出现异常回调
     * @param e 自定义异常
     */
    public abstract void onError(ApiException e);

    /**
     * 结果完成之后回调
     * @param t 数据
     */
    public abstract void onSuccess(T t);


    @Override
    public Type getType() {
        OnlyLog.d("CallBack -> getType()");
        return ClazzUtils.findNeedClass(getClass());
    }

    public Type getRawType() {//获取需要解析的泛型T raw类型
        OnlyLog.d("CallBack -> getRawType()");
        return ClazzUtils.findRawType(getClass());
    }
}
