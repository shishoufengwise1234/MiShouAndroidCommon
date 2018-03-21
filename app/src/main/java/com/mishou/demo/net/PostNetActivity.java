package com.mishou.demo.net;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.request.BaseBodyRequest;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 */

public class PostNetActivity extends BaseMvpAppcompatActivity{




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


        OnlyHttp.post("")
                .json("")
                .syncRequest(false)
                .uploadType(BaseBodyRequest.PART);
    }
}
