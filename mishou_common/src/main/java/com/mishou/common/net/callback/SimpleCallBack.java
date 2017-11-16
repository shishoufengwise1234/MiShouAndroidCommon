package com.mishou.common.net.callback;


import com.mishou.common.net.util.OnlyLog;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 * 抽离 复杂回调 专心于业务数据回调
 *
 * 剥离 onStart 、onCompleted
 * 子类 可专心处理 onError、onSuccess
 */

public abstract class SimpleCallBack<T> extends CallBack<T>{


    @Override
    public void onStart() {
        OnlyLog.d("SimpleCallBack > onStart()");
    }

    @Override
    public void onCompleted() {

        OnlyLog.d("SimpleCallBack > onCompleted()");

    }

}
