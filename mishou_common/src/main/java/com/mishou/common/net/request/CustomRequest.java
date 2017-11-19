package com.mishou.common.net.request;

import android.support.annotation.NonNull;

import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.function.ResultFunction;
import com.mishou.common.net.function.RetryFunction;
import com.mishou.common.net.model.ApiResult;
import com.mishou.common.net.observer.CallBackSubscriber;
import com.mishou.common.net.transformer.ResponseErrorTransformer;
import com.mishou.common.net.util.OnlyLog;
import com.mishou.common.net.util.SchedulerUtils;

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
     *
     * @param service class 对象
     * @param <T>     custom API service
     */
    public <T> T createApiService(@NonNull final Class<T> service) {
        checkRetrofit();
        return retrofit.create(service);
    }

    /**
     * 处理自定义 ApiResult 数据
     * <p>
     *
     * @param observable 目标 observable
     * @param <T>        class 对象
     * @return 与调用方相同
     */
    public <T> Observable<T> execute(@NonNull Observable<T> observable) {
        checkRetrofit();

        return observable.compose(SchedulerUtils.<T>ioAndMain())
                .compose(new ResponseErrorTransformer<T>())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));

    }

    /**
     * 自定义 observable 请求 添加 框架CallBack 回调
     *
     * @param observable 目标 observable
     * @param callBack   回调
     * @param <T>        目标class 对象
     */
    public <T> void execute(@NonNull Observable<T> observable,@NonNull  CallBack<T> callBack) {
        execute(observable, new CallBackSubscriber<>(baseContext, callBack));
    }

    /**
     * 自定义 observable 请求自定义 订阅者
     *
     * @param observable 目标 observable
     * @param subscriber Observer 自定义订阅者
     * @param <T>        目标class 对象
     */
    public <T> void execute(@NonNull Observable observable, @NonNull Observer<T> subscriber) {
        observable.compose(isSyncRequest ? SchedulerUtils.<T>main() : SchedulerUtils.<T>io_main())
                .subscribe(subscriber);
    }


    /**
     * 调用call返回一个Observable,针对ApiResult的业务<T>
     * 举例：如果你给的是一个Observable<ApiResult<AuthModel>> 那么返回的<T>是AuthModel
     */
    /**
     * 处理 ApiResult 数据
     * <p>
     * 默认添加对结果转换 APIResult -> T
     *
     * @param observable 目标 observable
     * @param <T>        T
     * @return Observable<T>
     */
    public <T> Observable<T> executeApi(@NonNull Observable<ApiResult<T>> observable) {
        checkRetrofit();
        return observable
                .map(new ResultFunction<T>())
                .compose(SchedulerUtils.<T>ioAndMain())
                .compose(new ResponseErrorTransformer<T>())
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay));
    }

    /**
     * 直接使用callback 获取数据
     * 注：数据格式必须是 完全符合框架APIResult 格式
     *
     * @param callBack CallBack回调
     * @param <T>      class 对象
     * @return Disposable
     */
    public <T> Disposable executeApi(@NonNull Observable<T> observable,@NonNull  CallBack<T> callBack) {
        return execute(observable, new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    /**
     * 使用带有回调方式 代理获取数据
     * 使用自定义 APIResult 数据格式
     * <p>
     * 默认添加对结果转换 APIResult -> T
     *
     * @param proxy 自定义 APIResult
     * @param <T>   返回对象
     * @return Disposable
     */
    public <T> Disposable execute(@NonNull Observable<T> observable,@NonNull  CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return toObservable(observable, proxy)
                .subscribeWith(new CallBackSubscriber<T>(baseContext, proxy.getCallBack()));
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

    /**
     * 创建retrofit 对象
     */
    public CustomRequest createRetrofit() {
        return create();
    }

    private void checkRetrofit() {
        if (retrofit == null) {
            createRetrofit();
//            throw new NullPointerException("Retrofit is null  please init create() ");
        }
    }
}
