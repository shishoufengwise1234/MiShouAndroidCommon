package com.mishou.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.demo.R;
import com.mishou.common.utils.ui.JumpUtils;
import com.mishou.demo.history.HistoryActivity;
import com.mishou.demo.net.HttpMainActivity;
import com.mishou.demo.webview.BaseWebViewActivity;
import com.mishou.demo.zhihu.ZhihuActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 */

public class MainActivity extends BaseMvpAppcompatActivity {


    @BindView(R.id.btn_image_loader)
    Button btnImageLoader;
    @BindView(R.id.btn_image_picker)
    Button btnImagePicker;
    @BindView(R.id.btn_http)
    Button btnHttp;
    @BindView(R.id.btn_mvp)
    Button btnMvp;


    @Override
    public IBasePresenter createPresenter() {
        return null;
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
    protected int getLayoutView() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_image_loader, R.id.btn_image_picker,
            R.id.btn_http, R.id.btn_mvp, R.id.btn_zhihu, R.id.btn_history, R.id.btn_webview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_image_loader:
                JumpUtils.startActivity(this, ImageLoaderActivity.class);
                break;
            case R.id.btn_image_picker:
                break;
            case R.id.btn_http:

                JumpUtils.startActivity(this, HttpMainActivity.class);
                break;
            case R.id.btn_mvp:
                break;
            case R.id.btn_zhihu:
                JumpUtils.startActivity(mContext, ZhihuActivity.class);
                break;
            case R.id.btn_history:
                JumpUtils.startActivity(this, HistoryActivity.class);
                break;
            case R.id.btn_webview:
                JumpUtils.startActivity(this, BaseWebViewActivity.class);
                break;
        }
    }
}
