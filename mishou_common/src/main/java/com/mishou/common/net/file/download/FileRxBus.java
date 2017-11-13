package com.mishou.common.net.file.download;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by ${shishoufeng} on 2016/10/18 0018.
 * email:shishoufeng1227@126.com
 *
 * 文件下载自定义rxbus 发送事件
 */
public class FileRxBus {

    private static volatile FileRxBus mInstance;
    private final Subject<Object> bus;

    private FileRxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    /**
     * 单例RxBus
     *
     * @return
     */
    public static FileRxBus getDefault() {
        FileRxBus rxBus = mInstance;
        if (mInstance == null) {
            synchronized (FileRxBus.class) {
                rxBus = mInstance;
                if (mInstance == null) {
                    rxBus = new FileRxBus();
                    mInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 发送一个新事件
     *
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 返回特定类型的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
