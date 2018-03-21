package com.mishou.demo.base.mvp;

import android.os.Bundle;
import android.view.View;

import com.mishou.common.base.mvp.BaseMvpSupportFragment;
import com.mishou.common.base.mvp.IBasePresenter;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 */

public class BaseMvpFragmentTest extends BaseMvpSupportFragment {


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
    protected void setOnListener(View view) {

    }

    @Override
    protected void onLogic(Bundle savedInstanceState) {

    }
}
