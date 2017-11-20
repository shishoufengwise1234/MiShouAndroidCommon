package com.mishou.demo.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mishou.common.base.BaseFragmentActivity;
import com.mishou.common.demo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${shishoufeng} on 17/11/20.
 * email:shishoufeng1227@126.com
 */

public class BaseWebViewActivity extends BaseFragmentActivity {


    @BindView(R.id.btn_native_webview)
    Button btnNativeWebview;
    @BindView(R.id.btn_tbs_webview)
    Button btnTbsWebview;
    @BindView(R.id.fl_webview_content)
    FrameLayout flWebviewContent;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_base_webview;
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

    @OnClick({R.id.btn_native_webview, R.id.btn_tbs_webview})
    public void onViewClicked(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_native_webview:

                transaction.replace(R.id.fl_webview_content, new NtiveFragment());
                transaction.commit();
                break;
            case R.id.btn_tbs_webview:

                transaction.replace(R.id.fl_webview_content,new TbsFragment());
                transaction.commit();
                break;
        }
    }
}
