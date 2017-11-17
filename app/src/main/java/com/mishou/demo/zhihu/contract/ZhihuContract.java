package com.mishou.demo.zhihu.contract;

import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.base.mvp.IBaseView;
import com.mishou.demo.zhihu.model.ZhihuData;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public interface ZhihuContract {
    interface Model {
    }

    interface View extends IBaseView<ZhihuContract.Presenter>{


        void showDataError();

        void showDataSuccess(ZhihuData data);
    }

    interface Presenter extends IBasePresenter {


        void loadZhihuData();
    }
}
