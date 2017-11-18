package com.mishou.demo.bean;

import com.mishou.common.net.model.ApiResult;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 */

public class TestApiResult5<T> extends ApiResult<T> {


    @Override
    public boolean isOk() {
        return true;
    }
}
