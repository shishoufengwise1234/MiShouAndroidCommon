package com.mishou.demo.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.demo.R;
import com.mishou.common.utils.ui.JumpDataUtils;
import com.mishou.common.widgets.view.MultiStateView;
import com.mishou.demo.bean.HistoryDetailsBean;
import com.mishou.demo.history.contract.HistoryDetailsContract;
import com.mishou.demo.history.presenter.HistoryDetailsPresenterImpl;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 */

public class HistoryDetailsActivity extends BaseMvpAppcompatActivity<HistoryDetailsContract.Presenter> implements HistoryDetailsContract.View {


    @BindView(R.id.toolbar_history_details)
    Toolbar toolbarHistoryDetails;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.state_view)
    MultiStateView stateView;

    @Override
    public HistoryDetailsContract.Presenter createPresenter() {
        return new HistoryDetailsPresenterImpl(this);
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

//    @Override
//    public void onShowLoading() {
//
//        stateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
//    }
//
//    @Override
//    public void onHideLoading() {
//
//        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
//    }
//
//    @Override
//    public void onShowNetError() {
//
//        stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
//    }

    @Override
    public void showDetailsData(HistoryDetailsBean detailsBean) {

        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        //设置数据
        toolbarHistoryDetails.setTitle(detailsBean.getTitle());
        tvContent.setText(detailsBean.getContent());
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_history_details;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        setSupportActionBar(toolbarHistoryDetails);
    }

    @Override
    protected void setOnListener() {

        toolbarHistoryDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

        String eid = JumpDataUtils.getString(getIntent(),"eid");

        presenter.loadDetailsData(eid);
    }


}
