package com.mishou.common.net.observer;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresPermission;

import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.ProgressCallBack;
import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.util.OnlyLog;

import io.reactivex.annotations.NonNull;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 * <p>
 * 回调订阅者
 */

public class CallBackSubscriber<T> extends BaseSubscriber<T> {

    public CallBack<T> mCallBack;

    public CallBackSubscriber(@NonNull Context context, CallBack<T> callBack) {
        super(context);
        mCallBack = callBack;
        if (callBack instanceof ProgressCallBack) {
            ((ProgressCallBack) callBack).subscription(this);
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @Override
    protected void onStart() {
        super.onStart();
        OnlyLog.d("CallBackSubscriber -> onStart()");
        if (mCallBack != null)
            mCallBack.onStart();
    }

    @Override
    public void onNext(@NonNull T t) {
        super.onNext(t);
        OnlyLog.d("CallBackSubscriber -> onNext()");
        if (mCallBack != null)
            mCallBack.onSuccess(t);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        OnlyLog.d("CallBackSubscriber -> onComplete()");
        if (mCallBack != null)
            mCallBack.onCompleted();

    }

    @Override
    protected void onErrorMessage(ApiException exception) {
        OnlyLog.d("CallBackSubscriber -> onErrorMessage()");

        if (mCallBack != null)
            mCallBack.onError(exception);

//        if (exception != null && exception.getCode() == OnlyConstants.DATA_SUCCESS){
//            if (mCallBack != null) mCallBack.onSuccess(null);
//        }else {
//            if (mCallBack != null)
//                mCallBack.onError(exception);
//        }
    }
}
