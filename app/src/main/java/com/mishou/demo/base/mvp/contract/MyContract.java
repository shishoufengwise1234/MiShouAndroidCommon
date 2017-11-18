package com.mishou.demo.base.mvp.contract;

import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.base.mvp.IBaseView;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 */

public interface MyContract {
    interface Model {
    }

    interface View extends IBaseView<Presenter>{

        void show();
    }

    interface Presenter extends IBasePresenter<View>{


    }
}
