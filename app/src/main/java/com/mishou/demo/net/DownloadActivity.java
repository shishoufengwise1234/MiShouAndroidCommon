package com.mishou.demo.net;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mishou.common.base.mvp.BaseMvpActivity;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.FileCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 */

public class DownloadActivity extends BaseMvpActivity {





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
        return 0;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initData() {


        Call call = OnlyHttp.download("")
                .savePath("")
                .saveName("")
                .startDownload(new FileCallback() {
                    @Override
                    public void progress(long progress, long total) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

        //cancel
        OnlyHttp.cancelCall(call);

    }
}
