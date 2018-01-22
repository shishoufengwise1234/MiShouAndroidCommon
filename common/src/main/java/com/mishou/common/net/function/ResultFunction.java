package com.mishou.common.net.function;

import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.exception.ServerException;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.util.OnlyLog;

import io.reactivex.functions.Function;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 * <p>
 * 结果转换器  将 ApiResult<T> 数据转换成 T
 */

public class ResultFunction<T> implements Function<ApiResult<T>, T> {


    @Override
    public T apply(ApiResult<T> tApiResult) throws Exception {
        OnlyLog.d("ResultFunction  ->  apply()");

        if (tApiResult != null) {  //正确数据

            if (tApiResult.getData() != null && tApiResult.isOk()) {  //正确数据

                return tApiResult.getData();
            } else{  //不是正确数据直接将结果抛出 error
                throw new ServerException(tApiResult.getCode(), tApiResult.getMessage() != null ? tApiResult.getMessage() : "数据出错,请稍后重试");
            }
        } else {
            throw new ServerException(OnlyConstants.RESULT_CAST_ERROR, "数据出错,请稍后重试");
        }

        //暂时取消 强转换处理
//        if (tApiResult != null){
//            if (tApiResult.getData() != null && tApiResult.isOk()){  //正确数据
//
//                return tApiResult.getData();
//            }else if (tApiResult.getData() == null && tApiResult.isOk() ){  //访问成功 但是 data = null
//
//                return tApiResult.getData();
//            }else if (tApiResult.getData() == null ){  // data = null
//
//                throw new ServerException(tApiResult.getCode(),tApiResult.getMessage() == null ? "T is null ":tApiResult.getMessage());
//            }else{  //请求失败
//
//                throw new ServerException(tApiResult.getCode(),tApiResult.getMessage() == null ? "request error ":tApiResult.getMessage());
//            }
//        }else{
//             //默认数据
//            throw new ServerException(OnlyConstants.ERROR_CODE.RESULT_CAST_ERROR,"数据转换错误 apiResult -> T = error");
//        }

    }


}
