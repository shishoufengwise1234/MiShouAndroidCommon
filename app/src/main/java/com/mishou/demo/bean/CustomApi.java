package com.mishou.demo.bean;

import com.mishou.common.net.model.ApiResult;
import com.orhanobut.logger.Logger;


/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 */

public class CustomApi<T> extends ApiResult<T> {


    private T HeWeather6;


    @Override
    public T getData() {
        return HeWeather6;
    }


    @Override
    public boolean isOk() {
        Logger.d("isOk()");
        return true;
    }
}
