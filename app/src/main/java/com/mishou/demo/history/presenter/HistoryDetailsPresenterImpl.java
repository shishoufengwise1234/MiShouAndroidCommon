package com.mishou.demo.history.presenter;

import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.callback.CallClazzProxy;
import com.mishou.common.net.callback.SimpleCallBack;
import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.observer.CallBackSubscriber;
import com.mishou.demo.Constants;
import com.mishou.demo.DemoApplication;
import com.mishou.demo.bean.HistoryData;
import com.mishou.demo.bean.HistoryDetailsBean;
import com.mishou.demo.bean.HistoryRequest;
import com.mishou.demo.history.api.HistoryApiService;
import com.mishou.demo.history.contract.HistoryDetailsContract;
import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 */

public class HistoryDetailsPresenterImpl implements HistoryDetailsContract.Presenter {

    private HistoryDetailsContract.View viewHis;

    public HistoryDetailsPresenterImpl(HistoryDetailsContract.View view) {
        this.viewHis = view;
    }

    @Override
    public void loadDetailsData(String eid) {

        //参数
        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", Constants.HISTORY_KEY);
        params.put("e_id", eid);


//        get_callback(params); //get 请求直接使用 callback 回调
//        get_callback_proxy(params);//get 使用 callback 代理回调
//        get_simple_callback(params);//simplecallback 简单回调
//        get_observable(params); //获取 observable 自定义处理
//        get_disposable(params);
//        get_custom(params); //自定义请求
//        get_custom_apiservice(params);
//
        post_callback(params);
//        post_callback_proxy(params);
//        post_simple_callback(params);
//        post_observable(params);
//        post_disposable(params);
//        post_custom(params);
//        post_custom_apiservice(params);

    }

    private void post_custom_apiservice(Map<String, String> params) {


    }

    private void post_custom(Map<String, String> params) {


    }

    private void post_disposable(Map<String, String> params) {


    }

    private void post_observable(Map<String, String> params) {


    }

    private void post_simple_callback(Map<String, String> params) {


    }

    private void post_callback_proxy(Map<String, String> params) {


    }

    private void post_callback(Map<String, String> params) {

        HistoryRequest request = new HistoryRequest();
        request.setKey(Constants.HISTORY_KEY);
        request.setE_id(params.get("e_id"));


        //直接使用 callback 回调获取数据
        OnlyHttp.post(Constants.HISTORY_DETAILS)
//                .addParams(params) //直接添加参数
                .object(request) //使用对象传参数
                .execute(new CallBackProxy<HistoryData<List<HistoryDetailsBean>>, List<HistoryDetailsBean>>(new CallBack<List<HistoryDetailsBean>>() {
                    @Override
                    public void onStart() {

                        Logger.d("onstart");
                    }

                    @Override
                    public void onCompleted() {

                        Logger.d("oncompleted");
                    }

                    @Override
                    public void onError(ApiException e) {

                        onErrorCallback(e);
                    }

                    @Override
                    public void onSuccess(List<HistoryDetailsBean> historyDetailsBeans) {

                        Logger.d("onsuccess");
                        onReuslt(historyDetailsBeans);

                    }
                }) {
                });

    }

    private void get_custom_apiservice(Map<String, String> params) {

        //自定义请求 参考 此方法
        get_custom(params);

    }

    private void get_custom(Map<String, String> params) {

        //自定义请求
        Observable<HistoryData<List<HistoryDetailsBean>>> observable = OnlyHttp.custom(Constants.HISTORY_DETAILS)
                .createRetrofit()
                .createApiService(HistoryApiService.class)
                .getHistoryDetails(Constants.HISTORY_DETAILS, params);

       observable.subscribeOn(Schedulers.io())
               .unsubscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new CallBackSubscriber<HistoryData<List<HistoryDetailsBean>>>(DemoApplication.getContext(), new CallBack<HistoryData<List<HistoryDetailsBean>>>() {
                   @Override
                   public void onStart() {

                   }

                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(ApiException e) {

                       onErrorCallback(e);
                   }

                   @Override
                   public void onSuccess(HistoryData<List<HistoryDetailsBean>> listHistoryData) {

                       onReuslt(listHistoryData.getData());
                   }
               }));



    }

    private void get_disposable(Map<String, String> params) {

        //Disposable 可以 参考 此方法
        get_observable(params);
    }

    private void get_observable(Map<String, String> params) {

        //自定义获取 Observable
        Observable<List<HistoryDetailsBean>> observable = OnlyHttp.get(Constants.HISTORY_DETAILS)
                .addParams(params)
                .execute(new CallClazzProxy<HistoryData<List<HistoryDetailsBean>>, List<HistoryDetailsBean>>(
                        new TypeToken<List<HistoryDetailsBean>>() {
                        }.getType()
                ) {
                });
        //自定义处理
        Disposable disposable = observable.subscribe(new Consumer<List<HistoryDetailsBean>>() {
                                                         @Override
                                                         public void accept(List<HistoryDetailsBean> historyDetailsBeans) throws Exception {

                                                             Logger.d("accept  ");

                                                             onReuslt(historyDetailsBeans);

                                                         }

                                                     },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Logger.d("accept" + throwable);
                        onErrorCallback(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                        Logger.d("run");
                    }
                }
        );

        //可取消订阅 防止oom
//        OnlyHttp.cancelDisposable(disposable);

    }

    private void get_simple_callback(Map<String, String> params) {

        //使用 框架提供 simpleCallback 回调 可只关心 业务处理
        OnlyHttp.get(Constants.HISTORY_DETAILS)
                .addParams(params)
                .execute(new CallBackProxy<HistoryData<List<HistoryDetailsBean>>, List<HistoryDetailsBean>>(new SimpleCallBack<List<HistoryDetailsBean>>() {
                    @Override
                    public void onError(ApiException e) {
                        onErrorCallback(e);
                    }

                    @Override
                    public void onSuccess(List<HistoryDetailsBean> historyDetailsBeans) {

                        onReuslt(historyDetailsBeans);
                    }
                }) {
                });

    }

    private void get_callback_proxy(Map<String, String> params) {

        //直接返回 observable 自定义处理
        Observable<List<HistoryDetailsBean>> listObservable = OnlyHttp.get(Constants.HISTORY_DETAILS)
                .addParams(params)//通过代理方式获取数据  使用 class 代理回调
                .execute(new CallClazzProxy<HistoryData<List<HistoryDetailsBean>>, List<HistoryDetailsBean>>(
                        new TypeToken<List<HistoryDetailsBean>>() {
                        }.getType()
                ) {
                });

        //使用框架提供的 CallBackSubscriber 订阅者 方便统一处理  推荐使用
        listObservable.subscribe(new CallBackSubscriber<List<HistoryDetailsBean>>(DemoApplication.getContext(), new CallBack<List<HistoryDetailsBean>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(ApiException e) {

                onErrorCallback(e);
            }

            @Override
            public void onSuccess(List<HistoryDetailsBean> historyDetailsBeans) {

                onReuslt(historyDetailsBeans);
            }
        }
        ));

    }

    private void get_callback(Map<String, String> params) {

        //直接使用 callback 回调获取数据
        OnlyHttp.get(Constants.HISTORY_DETAILS)
                .addParams(params)
                .execute(new CallBackProxy<HistoryData<List<HistoryDetailsBean>>, List<HistoryDetailsBean>>(new CallBack<List<HistoryDetailsBean>>() {
                    @Override
                    public void onStart() {

                        Logger.d("onstart");
                    }

                    @Override
                    public void onCompleted() {

                        Logger.d("oncompleted");
                    }

                    @Override
                    public void onError(ApiException e) {

                        onErrorCallback(e);
                    }

                    @Override
                    public void onSuccess(List<HistoryDetailsBean> historyDetailsBeans) {

                        Logger.d("onsuccess");
                        onReuslt(historyDetailsBeans);

                    }
                }) {
                });


    }

    private void onErrorCallback(Throwable e) {
        Logger.d("onerror" + e);
        viewHis.onShowNetError();
    }

    private void onReuslt(List<HistoryDetailsBean> historyDetailsBeans) {

        viewHis.showDetailsData(historyDetailsBeans.get(0));
    }


    @Override
    public void start(@Nullable HistoryDetailsContract.View view) {

        Logger.d("start" + view);


        viewHis.onShowLoading();
    }

    @Override
    public void destroy() {
        Logger.d("destroy");


    }
}
