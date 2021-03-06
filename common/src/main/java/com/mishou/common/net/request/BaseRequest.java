package com.mishou.common.net.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.api.ApiService;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.function.ApiResultFunction;
import com.mishou.common.net.function.RetryFunction;
import com.mishou.common.net.https.HttpsUtils;
import com.mishou.common.net.interceptor.HttpHeaderInterceptor;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.model.HttpParams;
import com.mishou.common.net.util.OnlyLog;
import com.mishou.common.net.util.OnlyUtils;
import com.mishou.common.net.util.SchedulerUtils;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 * <p>
 * 请求base 定义请求方式
 * <p>
 * 1、自定义请求
 * 2、默认请求
 * 3、可转换成 call 、observable 自定义处理
 */

public abstract class BaseRequest<R extends BaseRequest> {

    //获取子类名称
    protected String className = this.getClass().getSimpleName();

    protected String url;                                                  //请求url
    protected String baseUrl;                                              //BaseUrl

    protected long readTimeOut = 0;                                            //读超时
    protected long writeTimeOut = 0;                                           //写超时
    protected long connectTimeout = 0;                                         //链接超时

    protected int retryCount;                                              //重试次数默认3次
    protected int retryDelay;                                              //延迟xxms重试
    protected int retryIncreaseDelay;                                      //叠加延迟
    protected boolean isSyncRequest;                                       //是否是同步请求

    //基类context 对象
    protected Context baseContext = null;

    protected Retrofit retrofit;
    protected ApiService apiService;
    //基类 OkHttpClient
    protected OkHttpClient okHttpClient;
    protected HttpUrl httpUrl;
    //代理类
    protected Proxy proxy;
    //基类 Gson 对象
    protected Gson baseGson;

    //HTTPS SSL 认证参数
    protected HttpsUtils.SSLParams sslParams = null;
    protected HostnameVerifier hostnameVerifier = null;

    //retrofit 转换器集合
    protected List<Converter.Factory> converterFactories = new ArrayList<>();

    //retrofit 变换集合
    protected List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

    //OKhttp 网络拦截器
    protected final List<Interceptor> networkInterceptors = new ArrayList<>();
    //OKhttp 普通拦截器
    protected final List<Interceptor> interceptors = new ArrayList<>();


    //请求头
    protected Map<String, String> baseHeaders = new LinkedHashMap<>();
    //请求参数
    protected HttpParams baseParams = new HttpParams();


    public BaseRequest(String url) {
        OnlyLog.d("BaseRequest > url =" + url + " class name > " + className);
        this.url = OnlyUtils.checkNotNull(url, "url(base) is null");

        OnlyHttp config = OnlyHttp.getInstance();

        this.baseContext = config.getContext();
        this.baseUrl = config.getBaseUrl();

        //使用 httpUrl 检测baseURL 是否正确
        if (!TextUtils.isEmpty(this.baseUrl))
            httpUrl = HttpUrl.parse(baseUrl);


        retryCount = config.getRetryCount();                              //超时重试次数
        retryDelay = config.getRetryDelay();                              //超时重试延时
        retryIncreaseDelay = config.getRetryIncreaseDelay();              //超时重试叠加延时
    }

    public R readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return (R) this;
    }

    public R writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return (R) this;
    }

    public R connectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return (R) this;
    }

    /**
     * 局部设置 base URL
     *
     * @param baseUrl baseUrl
     */
    public R baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        if (!TextUtils.isEmpty(this.baseUrl))
            httpUrl = HttpUrl.parse(baseUrl);
        return (R) this;
    }

    /**
     * 设置 OKHttp 代理类
     *
     * @param proxy 代理类
     */
    public R okProxy(Proxy proxy) {
        this.proxy = proxy;
        return (R) this;
    }

    /**
     * 支持设置外部gson 对象解析数据
     *
     * @param gson
     */
    public R gson(Gson gson) {
        this.baseGson = gson;
        return (R) this;
    }

    public R retryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");

        this.retryCount = retryCount;
        return (R) this;
    }

    public R retryDelay(int retryDelay) {
        if (retryDelay < 0) throw new IllegalArgumentException("retryDelay must > 0");

        this.retryDelay = retryDelay;
        return (R) this;
    }

    public R retryIncreaseDelay(int retryIncreaseDelay) {
        if (retryIncreaseDelay < 0)
            throw new IllegalArgumentException("retryIncreaseDelay must > 0");

        this.retryIncreaseDelay = retryIncreaseDelay;
        return (R) this;
    }

    /**
     * 添加拦截器
     */
    public R addInterceptor(Interceptor interceptor) {
        this.interceptors.add(OnlyUtils.checkNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    public R addNetworkInterceptor(Interceptor interceptor) {
        this.networkInterceptors.add(OnlyUtils.checkNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    /**
     * 设置Converter.Factory,默认GsonConverterFactory.create()
     */
    public R addConverterFactory(Converter.Factory factory) {
        this.converterFactories.add(factory);
        return (R) this;
    }

    /**
     * 设置CallAdapter.Factory,默认RxJavaCallAdapterFactory.create()
     */
    public R addCallAdapterFactory(CallAdapter.Factory factory) {
        this.adapterFactories.add(factory);
        return (R) this;
    }


    /**
     * https的全局访问规则
     */
    public R hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return (R) this;
    }

    /**
     * https的全局自签名证书
     */
    public R certificates(InputStream... certificates) {
        this.sslParams = HttpsUtils.getSslSocketFactory(null, null, certificates);
        return (R) this;
    }

    /**
     * https双向认证证书
     */
    public R certificates(InputStream bksFile, String password, InputStream... certificates) {
        this.sslParams = HttpsUtils.getSslSocketFactory(bksFile, password, certificates);
        return (R) this;
    }

    /**
     * 添加头信息
     */
    public R addHeaders(String key, String value) {
        this.baseHeaders.put(key, value);
        return (R) this;
    }

    /**
     * 添加头信息
     */
    public R addHeaders(Map<String, String> headers) {
        this.baseHeaders.putAll(headers);
        return (R) this;
    }

    /**
     * 添加参数
     */
    public R addParams(String key, String value) {
        this.baseParams.put(key, value);
        return (R) this;
    }

    /**
     * 添加参数
     */
    public R addParams(Map<String, String> map) {
        this.baseParams.put(map);
        return (R) this;
    }

    /**
     * 移除参数
     */
    public R removeParams(String key) {
        this.baseParams.remove(key);
        return (R) this;
    }

    /**
     * 清除所有参数
     */
    public R removeAllParams() {
        this.baseParams.clear();
        return (R) this;
    }

    /**
     * 是否异步请求
     *
     * @param syncRequest true new thread  false is mainThread
     */
    public R syncRequest(boolean syncRequest) {
        this.isSyncRequest = syncRequest;
        return (R) this;
    }

    /**
     * 生成 Observable 将要发送请求的观察者
     *
     * @return Observable<ResponseBody>
     */
    protected abstract Observable<ResponseBody> createObservable();


    /**
     * 对 Observable 进行改造
     * 1、添加结果转换器
     * 2、添加失败重试机制
     *
     * @param observable 目标 observable
     * @param proxy      CallBackProxy 目标代理类
     * @param <T>        目标class 对象 APIResult -> T
     * @return Observable<T>
     */
    protected <T> Observable<T> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Type type;
        if (proxy != null) {
            type = proxy.getType();
        } else {
            type = new TypeToken<ResponseBody>() {
            }.getType();
        }

        return observable.map(new ApiResultFunction(this.baseGson, type))
                .compose(isSyncRequest ? SchedulerUtils.<T>main() : SchedulerUtils.<T>io_main())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));

    }

    /**
     * 根据当前的请求参数，生成对应的OkHttpClient
     */
    @NonNull
    private OkHttpClient.Builder generateOkClient() {

        OkHttpClient.Builder builder;

        if (readTimeOut <= 0 && writeTimeOut <= 0
                && connectTimeout <= 0 && sslParams == null
                && hostnameVerifier == null && proxy == null && baseHeaders.isEmpty()) {

            OnlyLog.d("BaseRequest --- OnlyHttp.getOkHttpClientBuilder() ");
            //本类数据没有更改情况下 直接使用全局初始化 OkHttpClient.Builder
            builder = OnlyHttp.getOkHttpClientBuilder();

        } else {  //根据临时设置数据 重新产生 OkHttpClient.Builder

            OkHttpClient.Builder newClientBuilder = OnlyHttp.getOkHttpClient().newBuilder();

            //设置 读、写、连接 时间
            if (readTimeOut > 0)
                newClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            if (writeTimeOut > 0)
                newClientBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
            if (connectTimeout > 0)
                newClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);

            //设置 HTTPS 证书
            if (hostnameVerifier != null)
                newClientBuilder.hostnameVerifier(hostnameVerifier);
            if (sslParams != null)
                newClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

            //设置 OKhttp 网络代理
            if (proxy != null) newClientBuilder.proxy(proxy);


            //现将基类 header 添加进拦截器
            HttpHeaderInterceptor headerInterceptor = new HttpHeaderInterceptor(baseHeaders);
            newClientBuilder.addInterceptor(headerInterceptor);

            builder = newClientBuilder;

            OnlyLog.d("BaseRequest --- OnlyHttp.getOkHttpClient().newBuilder() ");
        }
        //添加网络拦截器
        if (interceptors != null && interceptors.size() > 0){

            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        //添加网络拦截器
        if (networkInterceptors != null && networkInterceptors.size() > 0) {
            for (Interceptor interceptor : networkInterceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        return builder;
    }

    /**
     * 根据当前的请求参数，生成对应的Retrofit
     */
    @NonNull
    private Retrofit.Builder generateRetrofit() {

        //本类转换器、未改动时默认使用 全局配置
        if (converterFactories.isEmpty() && adapterFactories.isEmpty()) {

            OnlyLog.d("BaseRequest --- OnlyHttp.getRetrofitBuilder().baseUrl(baseUrl) ");
            return OnlyHttp.getRetrofitBuilder().baseUrl(baseUrl);

        } else {

            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            if (!converterFactories.isEmpty()) {
                //添加转换器
                for (Converter.Factory converterFactory : converterFactories) {
                    retrofitBuilder.addConverterFactory(converterFactory);
                }
            } else {
                //获取全局的对象重新设置
                List<Converter.Factory> listConverterFactory = OnlyHttp.getRetrofit().converterFactories();
                for (Converter.Factory factory : listConverterFactory) {
                    retrofitBuilder.addConverterFactory(factory);
                }
            }
            if (!adapterFactories.isEmpty()) {
                for (CallAdapter.Factory adapterFactory : adapterFactories) {
                    retrofitBuilder.addCallAdapterFactory(adapterFactory);
                }
            } else {
                //获取全局的对象重新设置
                List<CallAdapter.Factory> listAdapterFactory = OnlyHttp.getRetrofit().callAdapterFactories();
                for (CallAdapter.Factory factory : listAdapterFactory) {
                    retrofitBuilder.addCallAdapterFactory(factory);
                }
            }
            OnlyLog.d("BaseRequest --- new -- retrofitBuilder.baseUrl(baseUrl) ");
            return retrofitBuilder.baseUrl(baseUrl);
        }
    }

    /**
     * 将 OKhttp 对象 、retrofit、rxjava 组合成 retrofit式数据
     */
    protected R create() {

        OkHttpClient.Builder okHttpClientBuilder = generateOkClient();

        Retrofit.Builder retrofitBuilder = generateRetrofit();
        //增加RxJava2CallAdapterFactory
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        this.okHttpClient = okHttpClientBuilder.build();

        retrofitBuilder.client(okHttpClient);
        this.retrofit = retrofitBuilder.build();

        //生成 base API service 对象
        apiService = retrofit.create(ApiService.class);

        return (R) this;
    }


}
