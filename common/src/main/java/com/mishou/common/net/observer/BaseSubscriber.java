package com.mishou.common.net.observer;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.util.OnlyLog;
import com.mishou.common.net.util.OnlyUtils;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 订阅者基类 抽象相关逻辑
 *
 * 1、统一处理 网络判断
 * 2、集中处理 网络错误、异常等
 */

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    private WeakReference<Context> contextWeakReference = null;

    public BaseSubscriber(@NonNull Context context){
        this.contextWeakReference = new WeakReference<>(context);
    }

    // TODO: 17/11/15 进行网络监测时需添加 android.permission.ACCESS_NETWORK_STATE 权限 暂时用注解提醒
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @Override
    protected void onStart() {
        super.onStart();

        OnlyLog.d("BaseSubscriber ----- onStart() ----- ");

        if (contextWeakReference != null && contextWeakReference.get() != null){
            //网络检测 没有网络直接 onComplete
            if (!OnlyUtils.isNetworkAvailable(contextWeakReference.get())){
//                ToastUtils.showCenter(contextWeakReference.get(),"网络未连接");

                //网络未连接抛出异常
                ApiException exception = new ApiException("网络未连接",OnlyConstants.NOT_CONNECT);
                //结束订阅
                onError(exception);

//                onComplete();
            }
        }

    }

    @Override
    public void onNext(T t) {

        OnlyLog.d("BaseSubscriber ----- onNext() ----- ");
    }

    @Override
    public final void onError(Throwable e) {

        OnlyLog.d("BaseSubscriber ----- onError() ----- ");
        //统一处理 异常
        if (e instanceof ApiException){
            onErrorMessage((ApiException) e);
        }else{
            ApiException exception = ApiException.parseException(e);
            onErrorMessage(exception);
        }

    }


    @Override
    public void onComplete() {

        OnlyLog.d("BaseSubscriber ----- onComplete() ----- ");
    }

    /**
     * 自定义异常
     * @param exception ApiException
     */
    protected abstract void onErrorMessage(ApiException exception);


}
