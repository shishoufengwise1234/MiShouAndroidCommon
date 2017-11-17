package com.mishou.demo.zhihu.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.mishou.common.adapter.recyclerview.BaseQuickAdapter;
import com.mishou.common.adapter.recyclerview.BaseViewHolder;
import com.mishou.common.demo.R;
import com.mishou.common.image.ImageLoader;
import com.mishou.common.utils.ListUtils;
import com.mishou.demo.DemoApplication;
import com.mishou.demo.zhihu.model.ZhihuData;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 *
 *
 * 这里是用的是 类库 com.mishou.common.adapter.recyclerview -> BaseQuickAdapter
 *
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */

public class ZhihuAdapter extends BaseQuickAdapter<ZhihuData.StoriesBean,BaseViewHolder> {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public ZhihuAdapter(int layoutResId, @Nullable List<ZhihuData.StoriesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhihuData.StoriesBean item) {

        Logger.d("ZhihuAdapter");
        //加载图片
        if (item != null){
            List<String> urls = item.getImages();
            if (!ListUtils.isEmpty(urls)){

                ImageView target = helper.getView(R.id.iv_zhihu);
                ImageLoader.getInstance().loadImage(context,urls.get(0),target);
            }
        }

        helper.setText(R.id.tv_zhihu_title,item.getTitle())
                .setTextColor(R.id.tv_zhihu_title,
                        ContextCompat.getColor(DemoApplication.getContext(),R.color.colorPrimary));



    }
}
