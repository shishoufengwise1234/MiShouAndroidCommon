package com.mishou.common.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mishou.common.net.https.HttpsUtils;
import com.mishou.common.net.interceptor.HttpLoggerInterceptor;
import com.mishou.common.net.request.CustomRequest;
import com.mishou.common.net.request.GetRequest;
import com.mishou.common.net.request.PostRequest;
import com.mishou.common.net.util.OnlyLog;
import com.mishou.common.net.util.OnlyUtils;

import java.io.InputStream;
import java.net.Proxy;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.disposables.Disposable;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 *
 * 网络请求唯一入口
 *
 * 1、基本框架 OKhttp + Retrofit2 + Rxjava2
 * 2、链式调用
 * 3、支持完全自定义操作
 * 4、
 */

public class OnlyHttp {

    private static final String TAG = "OnlyHttp";

    private static final int DEFAULT_MILLISECONDS = 60 * 1000;        //默认的超时时间
    private static final int DEFAULT_RETRY_COUNT = 3;                 //默认重试次数
    private static final int DEFAULT_RETRY_INCREASEDELAY = 0;         //默认重试叠加时间
    private static final int DEFAULT_RETRY_DELAY = 500;               //默认重试延时

    private int mRetryCount = DEFAULT_RETRY_COUNT;                    //重试次数默认3次
    private int mRetryDelay = DEFAULT_RETRY_DELAY;                    //延迟xxms重试
    private int mRetryIncreaseDelay = DEFAULT_RETRY_INCREASEDELAY;    //叠加延迟

    //全局BaseUrl
    private String mBaseUrl;
    //okhttp请求的客户端
    private OkHttpClient.Builder okHttpClientBuilder;
    //Retrofit请求Builder
    private Retrofit.Builder retrofitBuilder;
    //全局context 对象 用于网络检测
    private Context mContext;


    private OnlyHttp(){

        okHttpClientBuilder = new OkHttpClient.Builder();

        okHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier());
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        retrofitBuilder = new Retrofit.Builder();
    }

    private static volatile  OnlyHttp singleton = null;

    public static OnlyHttp getInstance() {
        if (singleton == null) {
            synchronized (OnlyHttp.class) {
                if (singleton == null) {
                    singleton = new OnlyHttp();
                }
            }
        }
        return singleton;
    }

    /**
     * 初始化context 对象
     * 建议放到 application 中
     * @param context  context
     */
    public OnlyHttp init(Context context){
        this.mContext = OnlyUtils.checkNotNull(context,"context is null");
        return this;
    }

    public Context getContext(){
        return this.mContext;
    }

    /**
     * 调试模式,默认打开所有的异常调试
     */
    public OnlyHttp debug(String tag) {
        debug(tag, true);
        return this;
    }

    /**
     * 打开调试模式
     * @param tag 日志 tag
     * @param isPrintException logger 日志拦截器开关
     *
     */
    public OnlyHttp debug(String tag, boolean isPrintException) {
        String tempTag = TextUtils.isEmpty(tag)? TAG : tag;
        if(isPrintException){

            HttpLoggerInterceptor loggerInterceptor = new HttpLoggerInterceptor(tempTag,isPrintException);
            loggerInterceptor.setLevel(HttpLoggerInterceptor.BODY);

            this.okHttpClientBuilder.addInterceptor(loggerInterceptor);
        }
        //打开log 开关
        OnlyLog.configure(tag,isPrintException);

        return this;
    }

    /**
     *  返回默认 OkHttpClient
     * @return OkHttpClient
     */
    public static OkHttpClient getOkHttpClient() {
        return getInstance().okHttpClientBuilder.build();
    }

    /**
     *  返回默认 Retrofit
     * @return Retrofit
     */
    public static Retrofit getRetrofit() {
        return getInstance().retrofitBuilder.build();
    }

    /**
     * 返回
     * @return OkHttpClient.Builder
     */
    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        return getInstance().okHttpClientBuilder;
    }

    /**
     * 对外暴露 Retrofit
     * @return  Retrofit.Builder
     */
    public static Retrofit.Builder getRetrofitBuilder() {
        return getInstance().retrofitBuilder;
    }

    /**
     * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
     * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
     * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
     */
    private class DefaultHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            OnlyLog.d(TAG,"verify()");
            return true;
        }
    }

    /**
     * https的全局访问规则
     */
    public OnlyHttp setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.okHttpClientBuilder.hostnameVerifier(hostnameVerifier);
        return this;
    }

    /**
     * https的全局自签名证书
     */
    public OnlyHttp setCertificates(InputStream... certificates) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, certificates);
        this.okHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    /**
     * https双向认证证书
     */
    public OnlyHttp setCertificates(InputStream bksFile, String password, InputStream... certificates) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(bksFile, password, certificates);
        this.okHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    /**
     * 全局读取超时时间
     */
    public OnlyHttp setReadTimeOut(long readTimeOut) {
        this.okHttpClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * 全局写入超时时间
     */
    public OnlyHttp setWriteTimeOut(long writeTimeout) {
        this.okHttpClientBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * 全局连接超时时间
     */
    public OnlyHttp setConnectTimeout(long connectTimeout) {
        this.okHttpClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * 超时重试次数
     */
    public OnlyHttp setRetryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");

        this.mRetryCount = retryCount;
        return this;
    }

    /**
     * 超时重试次数
     */
    public int getRetryCount() {
        return this.mRetryCount;
    }

    /**
     * 超时重试延迟时间
     */
    public OnlyHttp setRetryDelay(int retryDelay) {
        if (retryDelay < 0) throw new IllegalArgumentException("retryDelay must > 0");

        this.mRetryDelay = retryDelay;
        return this;
    }

    /**
     * 超时重试延迟时间
     */
    public int getRetryDelay() {
        return this.mRetryDelay;
    }

    /**
     * 超时重试延迟叠加时间
     */
    public OnlyHttp setRetryIncreaseDelay(int retryIncreaseDelay) {
        if (retryIncreaseDelay < 0)
            throw new IllegalArgumentException("retryIncreaseDelay must > 0");

        this.mRetryIncreaseDelay = retryIncreaseDelay;
        return this;
    }

    /**
     * 超时重试延迟叠加时间
     */
    public int getRetryIncreaseDelay() {
        return this.mRetryIncreaseDelay;
    }

    /**
     * 添加全局拦截器
     */
    public OnlyHttp addInterceptor(Interceptor interceptor) {
        this.okHttpClientBuilder.addInterceptor(OnlyUtils.checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 添加全局网络拦截器
     */
    public OnlyHttp addNetworkInterceptor(Interceptor interceptor) {
        this.okHttpClientBuilder.addNetworkInterceptor(OnlyUtils.checkNotNull(interceptor, "interceptor == null"));
        return this;
    }


    /**
     * 全局设置代理
     */
    public OnlyHttp setOkproxy(Proxy proxy) {
        this.okHttpClientBuilder.proxy(OnlyUtils.checkNotNull(proxy, "proxy == null"));
        return this;
    }

    /**
     * 全局设置请求的连接池
     */
    public OnlyHttp setOkConnectionPool(ConnectionPool connectionPool) {
        this.okHttpClientBuilder.connectionPool(OnlyUtils.checkNotNull(connectionPool, "connectionPool == null"));
        return this;
    }

    /**
     * 全局为Retrofit设置自定义的OkHttpClient
     */
    public OnlyHttp setOkclient(OkHttpClient client) {
        this.retrofitBuilder.client(OnlyUtils.checkNotNull(client, "client == null"));
        return this;
    }

    /**
     * 全局设置Converter.Factory,默认GsonConverterFactory.create()
     */
    public OnlyHttp addConverterFactory(Converter.Factory factory) {
        this.retrofitBuilder.addConverterFactory(OnlyUtils.checkNotNull(factory, "factory == null"));
        return this;
    }

    /**
     * 全局设置CallAdapter.Factory,默认RxJavaCallAdapterFactory.create()
     */
    public OnlyHttp addCallAdapterFactory(CallAdapter.Factory factory) {
        this.retrofitBuilder.addCallAdapterFactory(OnlyUtils.checkNotNull(factory, "factory == null"));
        return this;
    }

    /**
     * 全局设置Retrofit callbackExecutor
     */
    public OnlyHttp setCallbackExecutor(Executor executor) {
        this.retrofitBuilder.callbackExecutor(OnlyUtils.checkNotNull(executor, "executor == null"));
        return this;
    }

    /**
     * 全局设置Retrofit对象Factory
     */
    public OnlyHttp setCallFactory(okhttp3.Call.Factory factory) {
        this.retrofitBuilder.callFactory(OnlyUtils.checkNotNull(factory, "factory == null"));
        return this;
    }

    /**
     * 全局设置baseurl
     */
    public OnlyHttp setBaseUrl(String baseUrl) {
        this.mBaseUrl = OnlyUtils.checkNotNull(baseUrl, "baseUrl == null");
        return this;
    }

    /**
     * 获取全局baseurl
     */
    public String getBaseUrl() {
        return this.mBaseUrl;
    }

    /**
     * get请求
     */
    public static GetRequest get(@NonNull String url) {
        return new GetRequest(url);
    }

    /**
     * post请求
     */
    public static PostRequest post(@NonNull String url) {
        return new PostRequest(url);
    }

    public static CustomRequest customRequest(@NonNull String url){
        return new CustomRequest(url);
    }

    /**
     * 取消订阅
     */
    public static void cancelSubscription(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
