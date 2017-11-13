package com.mishou.demo.base.mvp;

import com.mishou.common.base.mvp.IBasePresenter;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 */

public interface MvpContect {


    public interface Presenter extends IBasePresenter{


        void loadData();
    }



}
