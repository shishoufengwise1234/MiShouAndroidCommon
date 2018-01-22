package com.mishou.demo.history.presenter;

import com.google.gson.reflect.TypeToken;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.callback.CallClazzProxy;
import com.mishou.common.net.exception.ApiException;
import com.mishou.demo.Constants;
import com.mishou.demo.bean.HistoryBean;
import com.mishou.demo.bean.HistoryData;
import com.mishou.demo.history.contract.HistoryContract;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 *
 *
 * 重点展示如何获取数据
 */

public class HistoryPresenterImpl implements HistoryContract.Presenter {

    private HistoryContract.View view;

    public HistoryPresenterImpl(HistoryContract.View view){
        this.view = view;
    }



    @Override
    public void loadData(String key, String date) {


        get_Data(key,date);

//        post_Data(key,date);

    }

    /**
     *
     * post 方式 自定义APIresult  请求数据
     * @param key
     * @param date
     */
    private void post_Data(String key, String date) {


        OnlyHttp.get(Constants.HISTORY_LIST)
                .addParams("key",key)
                .addParams("date",date) //采用 class 代理方式解析数据
                .execute(new CallClazzProxy<HistoryData<List<HistoryBean>>,
                        List<HistoryBean>>(new TypeToken<List<HistoryBean>>(){}.getType()){})
                .subscribe(new Consumer<List<HistoryBean>>() {
                    @Override
                    public void accept(List<HistoryBean> historyBeans) throws Exception {

                        Logger.d("onSuccess");

                        view.showHistoryList(historyBeans);

                    }
                });
    }

    /**
     * 使用自定义 APIresult 方式请求数据  get 请求
     * @param key
     * @param date
     */
    private void get_Data(String key, String date) {

        //返回 disposable
        Disposable disposable = OnlyHttp.get(Constants.HISTORY_LIST)
                .addParams("key",key)
                .addParams("date",date)
                //使用 带有回调的class代理获取数据
                .execute(new CallBackProxy<HistoryData<List<HistoryBean>>, List<HistoryBean>>(new CallBack<List<HistoryBean>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(ApiException e) {

                        Logger.d("error" +e);
                    }

                    @Override
                    public void onSuccess(List<HistoryBean> historyBeans) {

                        Logger.d("onSuccess");

                        view.showHistoryList(historyBeans);
                    }
                }){

                });


    }

    @Override
    public void start(HistoryContract.View view) {

    }

    @Override
    public void destroy() {

    }
}
