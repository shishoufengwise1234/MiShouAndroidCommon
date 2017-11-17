package com.mishou.common.net.function;

import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.exception.ServerException;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.util.OnlyLog;

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
        OnlyLog.d("ResultFunction  ->  apply()");
        if (tApiResult != null && tApiResult.isOk()){
            return tApiResult.getData();
        }else{
            throw new ServerException(OnlyConstants.ERROR_CODE.RESULT_CAST_ERROR,"apiResult is error");
        }
    }
}
