package com.mishou.common.net.function;

import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.exception.ServerException;
import com.mishou.common.net.model.ApiResult;

import io.reactivex.functions.Function;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 结果转换器  将 ApiResult<T> 数据转换成 T
 */

public class ResultFunction<T> implements Function<ApiResult<T>,T> {


    @Override
    public T apply(ApiResult<T> tApiResult) throws Exception {
        if (tApiResult != null && tApiResult.isOk()){
            return tApiResult.getData();
        }else{
            throw new ServerException(OnlyConstants.ERROR_CODE.SSL_ERROR,"apiResult is error");
        }
    }
}
