package com.mishou.common.net.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.util.OnlyLog;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 自定义 API 解析异常
 */

public class ApiException extends Exception {


    private int code = 0;
    private String message;

    public ApiException(Throwable throwable,int code){
        this.code = code;
        this.message = throwable.getMessage();
    }
    public ApiException(String msg,int code){
        this.code = code;
        this.message = msg;
    }

    /**
     * 检验API result 是否正确
     * @param apiResult result
     * @return true 正确
     */
    public static boolean isOk(ApiResult apiResult) {
        return apiResult != null && apiResult.isOk();
    }

    /**
     * 判断是何异常
     * @param e Throwable
     * @return ApiException
     */
    public static ApiException parseException(Throwable e) {
        OnlyLog.d("-----parseException()-----");
        ApiException ex;
        if (e instanceof HttpException) { //http 异常
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, httpException.code());

            ex.message = httpException.getMessage();
            return ex;
        } else if (e instanceof ServerException) { //自定义服务器异常
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getErrCode());
            ex.message = resultException.getMessage();
            return ex;
        } else if (e instanceof JsonSyntaxException
                || e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ex = new ApiException(e, OnlyConstants.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, OnlyConstants.CAST_ERROR);
            ex.message = "类型转换错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, OnlyConstants.NETWORD_ERROR);
            ex.message = "连接出错";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, OnlyConstants.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, OnlyConstants.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(e, OnlyConstants.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, OnlyConstants.UNKNOWNHOST_ERROR);
            ex.message = "无法解析该域名/无网络连接";
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, OnlyConstants.NULLPOINTER_EXCEPTION);
            ex.message = "服务器出错,请稍后重试";
            return ex;
        } else {
            ex = new ApiException(e, OnlyConstants.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
