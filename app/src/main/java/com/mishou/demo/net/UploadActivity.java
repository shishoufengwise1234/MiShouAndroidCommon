package com.mishou.demo.net;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mishou.common.base.mvp.BaseMvpFragmentActivity;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.MainUploadProgressCallBack;
import com.mishou.common.net.callback.OnProgressDialogListener;
import com.mishou.common.net.callback.ProgressCallBack;

import java.io.File;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 */

public class UploadActivity extends BaseMvpFragmentActivity {




    @Override
    public Object createPresenter() {
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
        return 0;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initData() {


        //上传文件示例
        OnlyHttp.post("")
                .addHeaders("","")
                .addFileparams("key", new File(""), new MainUploadProgressCallBack() {
                    @Override
                    protected void onMainUploadProgress(long bytesRead, long contentLength, boolean done) {

                    }
                })
                .execute(new ProgressCallBack<Object>(new OnProgressDialogListener() {
                    @Override
                    public Dialog createDialog() {
                        return null;
                    }
                }) {
                    @Override
                    public void onSuccess(Object o) {

                    }
                });
    }
}
