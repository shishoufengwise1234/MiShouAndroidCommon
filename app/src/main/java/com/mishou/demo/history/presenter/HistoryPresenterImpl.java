package com.mishou.demo.history.presenter;

import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallBackProxy;
import com.mishou.common.net.exception.ApiException;
import com.mishou.demo.Constants;
import com.mishou.demo.bean.HistoryBean;
import com.mishou.demo.bean.HistoryData;
import com.mishou.demo.history.contract.HistoryContract;
import com.orhanobut.logger.Logger;

import java.util.List;

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
    public void start() {

    }

    @Override
    public void loadData(String key, String date) {


        get_Data(key,date);

    }

    private void get_Data(String key, String date) {

        OnlyHttp.get(Constants.HISTORY_LIST)
                .addParams("key",key)
                .addParams("date",date)
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
}
