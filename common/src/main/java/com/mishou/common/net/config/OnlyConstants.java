package com.mishou.common.net.config;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 * <p>
 * 常量信息
 */

public class OnlyConstants {


    //对应HTTP的状态码

    public static final int BADREQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;


    /**
     * 默认刷新间隔时间 避免频繁调用
     */
    public static final int DEFAULT_REFRESH_TIME = 100;


    /**
     * 未知错误
     */
    public static final int UNKNOWN = 4004;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = UNKNOWN + 1;

    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 2005;

    /**
     * 证书出错
     */
    public static final int SSL_ERROR = HTTP_ERROR + 1;

    /**
     * 连接超时
     */
    public static final int TIMEOUT_ERROR = SSL_ERROR + 1;

    /**
     * 调用错误
     */
    public static final int INVOKE_ERROR = TIMEOUT_ERROR + 1;
    /**
     * 类转换错误
     */
    public static final int CAST_ERROR = INVOKE_ERROR + 1;
    /**
     * 请求取消
     */
    public static final int REQUEST_CANCEL = CAST_ERROR + 1;
    /**
     * 未知主机错误
     * <p>
     * 没有网络
     */
    public static final int UNKNOWNHOST_ERROR = 10009;

    /**
     * 空指针错误
     */
    public static final int NULLPOINTER_EXCEPTION = 2046;

    /**
     * 数据转换出错
     */
    public static final int RESULT_CAST_ERROR = 2000;

    /**
     * 下载文件出错
     */
    public static final int DOWNLOAD_FILE_ERROR = 2001;

    /**
     * data 数据为空
     */
    public static final int API_RESULT_DATA_NULL = 2010;


    /**
     * 请求成功
     */
    public static final int DATA_SUCCESS = 200;

    /**
     * 网络未连接异常code
     */
    public static final int NOT_CONNECT = 2003;

    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 2004;




}
