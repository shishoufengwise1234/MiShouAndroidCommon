package com.mishou.common.net.request;

import com.google.gson.reflect.TypeToken;
import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.function.ApiResultFunction;
import com.mishou.common.net.function.ResultFunction;
import com.mishou.common.net.function.RetryFunction;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.observer.CallBackSubscriber;
import com.mishou.common.net.transformer.ResponseErrorTransformer;
import com.mishou.common.net.util.OnlyLog;
import com.mishou.common.net.util.SchedulerUtils;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 * <p>
 * 自定义请求
 */

public class CustomRequest extends BaseRequest<CustomRequest> {


    public CustomRequest(String url) {
        super("");
        OnlyLog.d("CustomRequest url = " + url);
    }

    /**
     * 创建API service 对象
     * @param service class 对象
     * @param <T> custom API service
     */
    public <T> T createApiService(final Class<T> service) {
        checkRetrofit();
        return retrofit.create(service);
    }

    /**
     * 调用call返回一个Observable<T>
     * 举例：如果你给的是一个Observable<ApiResult<AuthModel>> 那么返回的<T>是一个ApiResult<AuthModel>
     */
    public <T> Observable<T> call(Observable<T> observable) {
        checkRetrofit();

        return observable.compose(SchedulerUtils.<T>ioAndMain())
                .compose(new ResponseErrorTransformer<T>())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));

    }

    public <T> void call(Observable<T> observable, CallBack<T> callBack) {
        call(observable, new CallBackSubscriber<>(baseContext, callBack));
    }

    public <T> void call(Observable observable, Observer<T> subscriber) {
        observable.compose(SchedulerUtils.io_main())
                .subscribe(subscriber);
    }


    /**
     * 调用call返回一个Observable,针对ApiResult的业务<T>
     * 举例：如果你给的是一个Observable<ApiResult<AuthModel>> 那么返回的<T>是AuthModel
     */
    public <T> Observable<T> apiCall(Observable<ApiResult<T>> observable) {
        checkRetrofit();
        return observable
                .map(new ResultFunction<T>())
                .compose(SchedulerUtils.<T>ioAndMain())
                .compose(new ResponseErrorTransformer<T>())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));
    }

    public <T> Disposable apiCall(Observable<T> observable, CallBack<T> callBack) {
        return call(observable, new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    public <T> Disposable call(Observable<T> observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return toObservable(createObservable(), proxy).subscribeWith(new CallBackSubscriber<T>(baseContext, proxy.getCallBack()));
    }

    private <T> Observable<T> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Type type;
        if (proxy != null) {
            type = proxy.getType();
        } else {
            type = new TypeToken<ResponseBody>() {
            }.getType();
        }

        return observable.map(new ApiResultFunction(null, type))
                .compose(isSyncRequest ? SchedulerUtils.<T>main() : SchedulerUtils.<T>io_main())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));


    }

    @Override
    protected Observable<ResponseBody> createObservable() {
        OnlyLog.d("CustomRequest > createObservable()");
        return null;
    }

    @Override
    protected CustomRequest create() {
        OnlyLog.d("CustomRequest > create()");
        return super.create();
    }

    private void checkRetrofit() {
        if (retrofit == null) {
            throw new NullPointerException("Retrofit is null  please init create() ");
        }
    }
}
