package com.mishou.demo.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.demo.R;
import com.mishou.common.net.OnlyHttp;
import com.mishou.demo.Constants;
import com.mishou.demo.bean.NowResult;
import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public class CustomHttpActivity extends BaseMvpAppcompatActivity {


    @BindView(R.id.btn_send_get)
    Button btnSendGet;


    @Override
    public Object createPresenter() {
        return null;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowNetError() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_send_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_get:

                sendGet();
                break;
        }
    }

    private void sendGet() {

        Map<String,String> params = new LinkedHashMap<>();
        params.put("location","北京");
        params.put("key",Constants.KEY);
        params.put("unit","m");

        //自定义APIservice 接口请求
        OnlyHttp.custom(Constants.WEATHER_GET)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .createRetrofit()
                .createApiService(CustomApiService.class)
                .getWeather(Constants.WEATHER_GET,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NowResult>() {
                    @Override
                    public void accept(NowResult nowResult) throws Exception {

                        Logger.d("onSuccess"+nowResult);
                    }
                });
//                .subscribe(new CallBackSubscriber<NowResult>(this, new CallBack<NowResult>() {
//                    @Override
//                    public void onStart() {
//
//                        Logger.d("onStart");
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                        Logger.d("onCompleted");
//                    }
//
//                    @Override
//                    public void onError(ApiException e) {
//
//                        Logger.d("onError");
//                    }
//
//                    @Override
//                    public void onSuccess(NowResult nowResult) {
//
//                        Logger.d("onSuccess"+nowResult);
//                    }
//                }));
    }
}
