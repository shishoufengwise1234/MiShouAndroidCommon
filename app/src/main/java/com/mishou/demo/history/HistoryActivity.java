package com.mishou.demo.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mishou.common.adapter.recyclerview.BaseQuickAdapter;
import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.demo.R;
import com.mishou.common.utils.TimeUtils;
import com.mishou.common.utils.ui.JumpUtils;
import com.mishou.demo.Constants;
import com.mishou.demo.bean.HistoryBean;
import com.mishou.demo.history.adapter.HistoryListAdapter;
import com.mishou.demo.history.contract.HistoryContract;
import com.mishou.demo.history.presenter.HistoryPresenterImpl;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 * <p>
 * <p>
 * 历史上的今天
 */

public class HistoryActivity extends BaseMvpAppcompatActivity<HistoryContract.Presenter> implements HistoryContract.View {


    @BindView(R.id.toolbar_history)
    Toolbar toolbarHistory;
    @BindView(R.id.recycler_content)
    RecyclerView recyclerContent;

    private HistoryListAdapter adapter;

    @Override
    public HistoryContract.Presenter createPresenter() {
        return new HistoryPresenterImpl(this);
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
    public void showHistoryList(List<HistoryBean> historyBeans) {

        adapter.setNewData(historyBeans);

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_history_list;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        adapter = new HistoryListAdapter(R.layout.item_list_text);

        setSupportActionBar(toolbarHistory);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerContent.setLayoutManager(layoutManager);

        recyclerContent.setAdapter(adapter);
    }

    @Override
    protected void setOnListener() {

        toolbarHistory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                HistoryBean bean = (HistoryBean) adapter.getItem(position);
                if (bean != null){

                    Bundle bundle = new Bundle();
                    bundle.putString("eid",bean.getE_id());

                    JumpUtils.startActivity(mContext,HistoryDetailsActivity.class,bundle);
                }

            }
        });

    }

    @Override
    protected void initData() {

        //yyyy-MM-dd HH:mm:ss
        String time = TimeUtils.getCurTimeString();
        StringBuilder builder = new StringBuilder();

        builder.append(time.substring(5,7))
                .append("/")
                .append(time.substring(8,10));

        Logger.d("time ="+time+"builder = "+builder);

        presenter.loadData(Constants.HISTORY_KEY,builder.toString());
    }


}
