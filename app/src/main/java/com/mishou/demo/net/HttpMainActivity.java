package com.mishou.demo.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.mishou.common.base.mvp.BaseMvpActivity;
import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.demo.R;
import com.mishou.common.utils.ui.JumpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 */

public class HttpMainActivity extends BaseMvpActivity {


    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.btn_post)
    Button btnPost;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.btn_custom)
    Button btnCustom;


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
        return R.layout.activity_http_main;
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


    @OnClick({R.id.btn_get, R.id.btn_post, R.id.btn_download, R.id.btn_upload,R.id.btn_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get:

                JumpUtils.startActivity(this,GetNetActivity.class);
                break;
            case R.id.btn_post:
                break;
            case R.id.btn_download:
                JumpUtils.startActivity(this,DownloadActivity.class);
                break;
            case R.id.btn_upload:
                break;
            case R.id.btn_custom:

                JumpUtils.startActivity(this,CustomHttpActivity.class);
                break;
        }
    }
}
