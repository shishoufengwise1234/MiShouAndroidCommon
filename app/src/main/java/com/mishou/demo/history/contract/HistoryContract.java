package com.mishou.demo.history.contract;

import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.base.mvp.IBaseView;
import com.mishou.demo.bean.HistoryBean;

import java.util.List;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public interface HistoryContract {
    interface Model {
    }

    interface View extends IBaseView<Presenter>{

        void showHistoryList(List<HistoryBean> historyBeans);

    }

    interface Presenter extends IBasePresenter<View>{

        void loadData(String key,String date);
    }
}
