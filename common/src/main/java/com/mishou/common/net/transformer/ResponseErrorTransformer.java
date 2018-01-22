package com.mishou.common.net.transformer;

import com.mishou.common.net.function.ResponseErrorFunction;
import com.mishou.common.net.util.OnlyLog;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 *
 * 对结果进行错误转换
 */

public class ResponseErrorTransformer<T> implements ObservableTransformer<T,T>{


    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        OnlyLog.d("ResponseErrorTransformer -- apply()");
        return upstream.onErrorResumeNext(new ResponseErrorFunction<T>());
    }
}
