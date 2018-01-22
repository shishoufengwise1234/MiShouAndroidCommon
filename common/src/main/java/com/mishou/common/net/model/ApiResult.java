package com.mishou.common.net.model;

import com.mishou.common.net.config.OnlyConstants;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 * <p>
 * <p>
 * 封装标准 ApiResult 格式 返回数据
 * <p>
 * 对 API result 进行拓展时需 extends ApiResult<T>
 */

public class ApiResult<T> {

    private int code;
    private String message;
    private String elapsedTime;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断数据是否正确
     */
    public boolean isOk() {
        return code == OnlyConstants.DATA_SUCCESS;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", elapsedTime='" + elapsedTime + '\'' +
                ", data=" + data +
                '}';
    }
}
