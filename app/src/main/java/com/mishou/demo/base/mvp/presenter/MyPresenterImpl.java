package com.mishou.demo.base.mvp.presenter;

import android.support.annotation.Nullable;

import com.mishou.demo.base.mvp.contract.MyContract;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 */

public class MyPresenterImpl implements MyContract.Presenter {

    /**
     *
     * @param view view 可能为空 需作出非空判断
     */
    @Override
    public void start(@Nullable MyContract.View view) {

    }

    /**
     *
     */
    @Override
    public void destroy() {

    }
}
