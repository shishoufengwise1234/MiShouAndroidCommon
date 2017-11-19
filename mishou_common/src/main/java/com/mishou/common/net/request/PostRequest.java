package com.mishou.common.net.request;

import android.support.annotation.NonNull;

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

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 * <p>
 * post 请求
 */

public class PostRequest extends BaseBodyRequest<PostRequest> {


    public PostRequest(String url) {
        super(url);
    }

    /**
     * 使用 class 代理 获取数据
     * 注：数据格式必须是 完全符合框架APIResult 格式
     *
     * @param clazz class 对象
     * @param <T>   class 对象
     * @return Observable<T>
     */
    public <T> Observable<T> execute(@NonNull Class<T> clazz) {
        return execute(new CallClazzProxy<ApiResult<T>, T>(clazz) {
        });
    }

    /**
     * 使用 class 代理获取数据
     * 注：数据格式必须是 完全符合框架APIResult 格式
     *
     * @param type class 的type 对象
     * @param <T>  class 对象
     * @return Observable<T>
     */
    public <T> Observable<T> execute(@NonNull Type type) {
        return execute(new CallClazzProxy<ApiResult<T>, T>(type) {
        });
    }

    /**
     * class 代理获取数据
     * 使用自定义 APIResult 数据格式
     * <p>
     * 默认添加对结果转换 APIResult -> T
     *
     * @param proxy 自定义 APIResult
     * @param <T>   返回对象
     * @return Observable<T>
     */
    public <T> Observable<T> execute(@NonNull CallClazzProxy<? extends ApiResult<T>, T> proxy) {
        return create().createObservable()
                .map(new ApiResultFunction<T>(null, proxy.getType()))
                .compose(isSyncRequest ? SchedulerUtils.<T>main() : SchedulerUtils.<T>io_main())
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
    public <T> Disposable execute(@NonNull CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
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
    public <T> Disposable execute(@NonNull CallBackProxy<? extends ApiResult<T>, T> proxy) {

        return toObservable(create().createObservable(), proxy)
                .subscribeWith(new CallBackSubscriber<T>(baseContext, proxy.getCallBack()));

    }

}
