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
            if (tApiResult != null){ //将服务器code 和 msg 转换过去
                throw new ServerException(tApiResult.getCode(),tApiResult.getMsg()== null ? "数据转换错误 apiResult -> T = error":tApiResult.getMsg());
            }else{  //默认数据
                throw new ServerException(OnlyConstants.ERROR_CODE.RESULT_CAST_ERROR,"数据转换错误 apiResult -> T = error");
            }
        }
    }
}
