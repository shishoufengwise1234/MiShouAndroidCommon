package com.mishou.demo.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.demo.base.mvp.contract.MyContract;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 */

public class BaseMvpActivity extends BaseMvpAppcompatActivity<MyContract.Presenter> implements MyContract.View{


    @Override
    public MyContract.Presenter createPresenter() {
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
    public void show() {

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

    }
}
