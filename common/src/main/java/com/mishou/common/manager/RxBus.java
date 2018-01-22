package com.mishou.common.manager;

import java.util.HashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 * <p>
 * rxbus 异步消息封装
 */

public class RxBus {

    private final Subject<Object> subject;

    private HashMap<String, CompositeDisposable> mSubscriptionMap;

    private RxBus() {
        subject = PublishSubject.create().toSerialized();
    }

    private static class RxBusBuilder {
        private static final RxBus BUS = new RxBus();
    }

    /**
     * 单例RxBus
     *
     * @return rxbus
     */
    public static RxBus getBus() {
        return RxBusBuilder.BUS;
    }

    /**
     * 发送一个新事件
     *
     * @param o
     */
    public void post(Object o) {
        subject.onNext(o);
    }

    /**
     * 返回特定类型的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return subject.ofType(eventType);
    }

    /**
     * 返回指定类型的带背压的Flowable实例
     *
     * @param <T>
     * @param type
     * @return
     */
    public <T> Flowable<T> getObservable(Class<T> type) {
        return subject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }

    /**
     * 一个默认的订阅方法
     *
     * @param <T>
     * @param type
     * @param next
     * @param error
     * @return
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error) {
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    /**
     * 是否已有观察者订阅
     *
     * @return
     */
    public boolean hasObservers() {
        return subject.hasObservers();
    }

    /**
     * 保存订阅后的disposable
     *
     * @param o
     * @param disposable
     */
    public void addSubscription(Object o, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(disposable);
        } else {
            //一次性容器,可以持有多个并提供 添加和移除。
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(key, disposables);
        }
    }

    /**
     * 取消订阅
     *
     * @param o 对象
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).dispose();
        }

        mSubscriptionMap.remove(key);
    }

}
