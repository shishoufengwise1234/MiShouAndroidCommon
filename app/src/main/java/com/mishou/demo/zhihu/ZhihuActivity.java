package com.mishou.demo.zhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.demo.R;
import com.mishou.common.utils.ToastUtils;
import com.mishou.common.widgets.pulltorefresh.PtrClassicFrameLayout;
import com.mishou.common.widgets.pulltorefresh.PtrDefaultHandler;
import com.mishou.common.widgets.pulltorefresh.PtrFrameLayout;
import com.mishou.common.widgets.pulltorefresh.PtrHandler;
import com.mishou.common.widgets.pulltorefresh.header.MaterialHeader;
import com.mishou.common.widgets.view.MultiStateView;
import com.mishou.demo.zhihu.adapter.ZhihuAdapter;
import com.mishou.demo.zhihu.contract.ZhihuContract;
import com.mishou.demo.zhihu.model.ZhihuData;
import com.mishou.demo.zhihu.presenter.ZhihuPresenterImpl;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public class ZhihuActivity extends BaseMvpAppcompatActivity<ZhihuContract.Presenter> implements ZhihuContract.View {


    @BindView(R.id.toolbar_zhihu)
    Toolbar toolbarZhihu;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.state_view)
    MultiStateView stateView;
    @BindView(R.id.ptr_toolbar)
    PtrClassicFrameLayout mPtrFrame;

    private ZhihuAdapter zhihuAdapter;


    @Override
    public ZhihuContract.Presenter createPresenter() {
        return new ZhihuPresenterImpl(this);
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
    protected int getLayoutView() {
        return R.layout.activity_zhihu_text;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        setSupportActionBar(toolbarZhihu);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerList.setLayoutManager(layoutManager);

        //自定义刷新header
         MaterialHeader header = new MaterialHeader(mContext);
        //自定义添加刷新头
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);

        mPtrFrame.setLoadingMinTime(5 * 1000);


        //刷新阻力值
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.autoRefresh();

        //将数据添加进stateview 中
        stateView.setViewForState(R.layout.layout_base_loading,MultiStateView.VIEW_STATE_LOADING);
        stateView.setViewForState(R.layout.layout_base_error,MultiStateView.VIEW_STATE_ERROR);
        stateView.setViewForState(R.layout.layout_base_empty,MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    protected void setOnListener() {

        toolbarZhihu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
            }
        });



        mPtrFrame.setPtrHandler(new PtrHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                presenter.loadZhihuData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

    }

    @Override
    protected void initData() {

        presenter.loadZhihuData();

    }

    @Override
    public void showDataError() {

        ToastUtils.showMessage(mContext,"加载失败");
    }

    @Override
    public void showDataSuccess(ZhihuData data) {

        mPtrFrame.refreshComplete();

        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        zhihuAdapter = new ZhihuAdapter(R.layout.item_zhihu_title,data.getStories());
        zhihuAdapter.setContext(mContext);

        recyclerList.setAdapter(zhihuAdapter);

    }

}
