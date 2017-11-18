package com.mishou.demo.history.presenter;

import android.support.annotation.Nullable;

import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.exception.ApiException;
import com.mishou.demo.Constants;
import com.mishou.demo.bean.HistoryData;
import com.mishou.demo.bean.HistoryDetailsBean;
import com.mishou.demo.history.contract.HistoryDetailsContract;
import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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


        get_callback(params); //get 请求直接使用 callback 回调
//        get_callback_proxy(params);//get 使用 callback 代理回调
//        get_simple_callback(params);
//        get_observable(params);
//        get_disposable(params);
//        get_custom(params);
//        get_custom_apiservice(params);
//
//        post_callback(params);
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


    }

    private void get_custom_apiservice(Map<String, String> params) {


    }

    private void get_custom(Map<String, String> params) {


    }

    private void get_disposable(Map<String, String> params) {


    }

    private void get_observable(Map<String, String> params) {


    }

    private void get_simple_callback(Map<String, String> params) {


    }

    private void get_callback_proxy(Map<String, String> params) {

        //通过代理方式获取数据  使用 class 代理回调
        OnlyHttp.get(Constants.HISTORY_DETAILS)
                .addParams(params)
                .execute(new CallBackProxy<HistoryData<List<HistoryDetailsBean>>, List<HistoryDetailsBean>>(new CallBack<List<HistoryDetailsBean>>() {
                    @Override
                    public void onStart() {
                        Logger.d("start");
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d("oncompleted");
                    }

                    @Override
                    public void onError(ApiException e) {
                        Logger.d("onerror");
                    }

                    @Override
                    public void onSuccess(List<HistoryDetailsBean> historyDetailsBeans) {
                        Logger.d("onsuccess");

                        if (historyDetailsBeans != null) {
                            HistoryDetailsBean beans = historyDetailsBeans.get(0);

                            viewHis.showDetailsData(beans);
                        }

                    }
                }) {
                });

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

    private void onErrorCallback(ApiException e) {
        Logger.d("onerror"+e);

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
