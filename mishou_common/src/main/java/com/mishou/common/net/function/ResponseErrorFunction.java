package com.mishou.common.net.function;

import com.mishou.common.net.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 对结果进行 异常转换
 */

public class ResponseErrorFunction<T> implements Function<Throwable,Observable<T>> {


    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ApiException.parseException(throwable));
    }
}
