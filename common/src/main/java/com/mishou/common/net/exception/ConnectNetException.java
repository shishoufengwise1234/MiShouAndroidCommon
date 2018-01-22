package com.mishou.common.net.exception;

/**
 * Created by shishoufeng on 2018/1/16.
 * email:shishoufeng1227@126.com
 *
 *
 * 无法连接到网络异常
 */

public class ConnectNetException extends Exception{

    private int errCode;
    private String message;

    public ConnectNetException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
