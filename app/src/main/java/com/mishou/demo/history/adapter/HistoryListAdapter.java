package com.mishou.demo.history.adapter;

import com.mishou.common.adapter.recyclerview.BaseQuickAdapter;
import com.mishou.common.adapter.recyclerview.BaseViewHolder;
import com.mishou.common.demo.R;
import com.mishou.demo.bean.HistoryBean;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public class HistoryListAdapter extends BaseQuickAdapter<HistoryBean,BaseViewHolder> {


    public HistoryListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {

        helper.setText(R.id.tv_text,item.getTitle());
    }
}
