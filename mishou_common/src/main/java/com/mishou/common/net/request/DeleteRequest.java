package com.mishou.common.net.request;

import com.google.gson.reflect.TypeToken;
import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.callback.CallClazzProxy;
import com.mishou.common.net.function.ApiResultFunction;
import com.mishou.common.net.function.RetryFunction;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.observer.CallBackSubscriber;
import com.mishou.common.net.util.SchedulerUtils;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 * 删除请求
 */

public class DeleteRequest extends BaseRequest<DeleteRequest>{


    public DeleteRequest(String url) {
        super(url);
    }

    public <T> Observable<T> execute(Class<T> clazz) {
        return this.execute(new CallClazzProxy<ApiResult<T>, T>(clazz){});
    }

    public <T> Observable<T> execute(Type type) {
        return execute(new CallClazzProxy<ApiResult<T>, T>(type){});
    }

    /**
     * 发起请求
     *
     * @param proxy 代理类
     * @param <T>   返回对象
     * @return Observable
     */
    public <T> Observable<T> execute(CallClazzProxy<? extends ApiResult<T>, T> proxy) {
        return create().createObservable()
                .map(new ApiResultFunction<T>(null, proxy.getType()))
                .compose(isSyncRequest ? SchedulerUtils.<T>main() : SchedulerUtils.<T>io_main())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));

    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    /**
     * 发起请求
     * @param proxy 代理类
     * @param <T>   返回对象
     * @return Disposable
     */
    public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {

        return toObservable(createObservable(),proxy).subscribeWith(new CallBackSubscriber<T>(baseContext,proxy.getCallBack()));
    }


    private <T> Observable<T> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Type type;
        if (proxy != null){
            type = proxy.getType();
        }else{
            type = new TypeToken<ResponseBody>(){}.getType();
        }
        return observable.map(new ApiResultFunction(null,type))
                .compose(isSyncRequest ? SchedulerUtils.<T>main() : SchedulerUtils.<T>io_main())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));
    }


    @Override
    protected Observable<ResponseBody> createObservable() {
        return apiService.delete(url,baseParams.getUrlParamsMap());
    }
}
