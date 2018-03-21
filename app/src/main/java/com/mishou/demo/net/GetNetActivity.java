package com.mishou.demo.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.demo.R;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.CallBack;
import com.mishou.common.net.callback.CallClazzProxy;
import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.observer.CallBackSubscriber;
import com.mishou.demo.Constants;
import com.mishou.demo.bean.ApiResult5Bean;
import com.mishou.demo.bean.CustomApi;
import com.mishou.demo.bean.CustomData;
import com.mishou.demo.bean.Data;
import com.mishou.demo.bean.NowResult;
import com.mishou.demo.bean.TestApiResult5;
import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 */

public class GetNetActivity extends BaseMvpAppcompatActivity {


    @BindView(R.id.btn_send)
    Button btnSend;



    @Override
    public IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onShowLoading(int state) {

    }


    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowNetError(int state) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main_net;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initData() {

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                sendGet();

//                get_clazzproxy();

                get_Custom();
            }
        });




    }

    private void get_Custom() {

        Map<String, String> params = new LinkedHashMap<>();
        params.put("key", "6c20603237eef36dd7c0d1fe9c402072");
        params.put("address", "海淀上地金隅嘉华大厦");
        params.put("city", "北京");


        //返回 disposable 或者 observable 方便操作
        OnlyHttp.get("/v3/geocode/geo") //get 访问
                .baseUrl("http://restapi.amap.com")
                .addParams(params)
                .execute(new CallClazzProxy<CustomData<Data>,
                        Data>(new TypeToken<Data>(){}.getType()) {
                })
                .subscribe(new CallBackSubscriber<Data>(this, new CallBack<Data>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(ApiException e) {

                        Logger.d("error "+e);
                    }

                    @Override
                    public void onSuccess(Data s) {

                        Logger.d("s"+s);
                    }
                }));
    }

    private void get_clazzproxy() {

        OnlyHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new CallClazzProxy<TestApiResult5<List<ApiResult5Bean>>, List<ApiResult5Bean>>(new TypeToken<List<ApiResult5Bean>>(){}.getType()){})
                .subscribe(new CallBackSubscriber<List<ApiResult5Bean>>(this, new CallBack<List<ApiResult5Bean>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {

                        Logger.d("oncompleted");
                    }

                    @Override
                    public void onError(ApiException e) {

                        Logger.d("error"+e);
                    }

                    @Override
                    public void onSuccess(List<ApiResult5Bean> apiResult5Beans) {

                        Logger.d("onsuccess"+apiResult5Beans);

                    }
                }));
    }

    private void sendGet() {

//        Map<String,String> headers = new LinkedHashMap<>();
        Map<String,String> params = new LinkedHashMap<>();
        params.put("location","北京");
        params.put("key",Constants.KEY);
        params.put("unit","m");


        //返回 disposable 或者 observable 方便操作
        OnlyHttp.get(Constants.WEATHER_GET) //get 访问
                .addParams(params)//添加参数
                .addParams("location","北京")
                .addParams("key",Constants.KEY)
                .addParams("unit","m")
//                .execute(NowResult.class)
                .execute(new CallClazzProxy<CustomApi<List<NowResult>>,
                                List<NowResult>>(new TypeToken<List<NowResult>>(){}.getType()){})
                .subscribe(new CallBackSubscriber<List<NowResult>>(this, new CallBack<List<NowResult>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(ApiException e) {

                        Logger.d(e);
                    }

                    @Override
                    public void onSuccess(List<NowResult> nowResults) {

                    }
                }));


//                .execute(new ProgressCallBack<NowResult>(new OnProgressDialogListener() {
//                    @Override
//                    public Dialog createDialog() {
//                        return new LoadingDialog(GetNetActivity.this);
//                    }
//                }) {
//                    @Override
//                    public void onSuccess(NowResult nowResult) {
//
//                        Logger.d("onSuccess()");
//
//                        if (nowResult != null)
//                            Logger.d(nowResult);
//                    }
//                }
//                );

    }
}
