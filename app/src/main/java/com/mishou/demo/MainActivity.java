package com.mishou.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.demo.R;

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
    public void setPresenter(Object presenter) {

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


    @OnClick({R.id.btn_image_loader, R.id.btn_image_picker, R.id.btn_http, R.id.btn_mvp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_image_loader:

                break;
            case R.id.btn_image_picker:
                break;
            case R.id.btn_http:
                break;
            case R.id.btn_mvp:
                break;
        }
    }
}
