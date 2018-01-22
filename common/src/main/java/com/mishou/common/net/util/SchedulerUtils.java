package com.mishou.common.net.util;

import com.mishou.common.net.function.ResponseErrorFunction;
import com.mishou.common.net.function.ResultFunction;
import com.mishou.common.net.model.ApiResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 调度器相关
 */

public class SchedulerUtils {

    /**
     * io 线程发送数据 main 订阅事件
     *
     * 不添加任何转换器
     *
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> ioAndMain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                OnlyLog.i("isDisposed() = " + disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                OnlyLog.i("doFinally()");
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * io 线程发送数据 main 订阅事件
     *
     * 1、添加结果转换
     * 2、添加结果异常转换
     *
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<ApiResult<T>, T> io_main() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<ApiResult<T>> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ResultFunction<T>()) //添加结果转换
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                OnlyLog.i("doOnSubscribe（）" + disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                OnlyLog.i("doFinally()");
                            }
                        })
                        .onErrorResumeNext(new ResponseErrorFunction<T>());
            }
        };
    }

    /**
     * UI 线程发送数据 并订阅事件
     * 添加结果异常转换
     *
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<ApiResult<T>, T> main() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<ApiResult<T>> upstream) {
                return upstream
                        .map(new ResultFunction<T>())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                OnlyLog.i("isDisposed() = " + disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                OnlyLog.i("doFinally()");
                            }
                        })
                        .onErrorResumeNext(new ResponseErrorFunction<T>());
            }
        };
    }
}
