package com.mishou.demo.base.mvp;

import android.os.Bundle;
import android.view.View;

import com.mishou.common.base.mvp.BaseMvpSupportFragment;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 */

public class BaseMvpFragmentTest extends BaseMvpSupportFragment {


    @Override
    public void setPresenter(Object presenter) {

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
    protected void setOnListener(View view) {

    }

    @Override
    protected void onLogic(Bundle savedInstanceState) {

    }
}
