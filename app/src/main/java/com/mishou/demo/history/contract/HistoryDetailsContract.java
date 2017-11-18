package com.mishou.demo.history.contract;

import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.base.mvp.IBaseView;
import com.mishou.demo.bean.HistoryDetailsBean;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 */

public interface HistoryDetailsContract {
    interface Model {
    }

    interface View extends IBaseView<Presenter>{


        void showDetailsData(HistoryDetailsBean detailsBean);
    }

    interface Presenter extends IBasePresenter<View>{


        void loadDetailsData(String eid);
    }
}
