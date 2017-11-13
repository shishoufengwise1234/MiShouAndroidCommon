package com.mishou.demo.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mishou.common.base.mvp.BaseMvpFragmentActivity;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 */

public class BaseMvpActivity extends BaseMvpFragmentActivity<MvpContect.Presenter> {



    @Override
    public void setPresenter(MvpContect.Presenter presenter) {

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

    }
}
