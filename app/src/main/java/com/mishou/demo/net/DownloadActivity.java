package com.mishou.demo.net;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.mishou.common.base.mvp.BaseMvpActivity;
import com.mishou.common.base.mvp.IBasePresenter;
import com.mishou.common.demo.R;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 */

public class DownloadActivity extends BaseMvpActivity {

    private ProgressDialog dialog;

    @BindView(R.id.btn_start_download)
    Button btnStartDownload;


    @Override
    public IBasePresenter createPresenter() {
        return null;
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


    @Override
    protected int getLayoutView() {
        return R.layout.activity_download_test;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
        dialog.setMessage("正在下载...");
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        dialog.setTitle("下载文件");
        dialog.setMax(100);
    }

    @Override
    protected void setOnListener() {

        btnStartDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDownload();
            }
        });

    }

    private void startDownload() {
//
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ms_test/";
//        String name = "wuba.apk";
//
//        OnlyHttp.download(Constants.DOWNLOAD_TEST)
//                .savePath(path)
//                .saveName(name)
//                .execute(new DownloadCallback<String>() {
//                    @Override
//                    public void onProgress(long bytesRead, long contentLength, boolean done) {
//
////                        Logger.d("progress = "+bytesRead+"  total = "+contentLength);
//                        int progress = (int) (bytesRead * 100 / contentLength);
//                        dialog.setProgress(progress);
//                        if (done) {
//                            dialog.setMessage("下载完成");
//                        }
//                    }
//
//                    @Override
//                    public void onDownloadCompleted(String filePath) {
//
//                        Logger.d("onsuccess"+filePath);
//
//                        ToastUtils.showMessage(mContext,filePath);
//
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onStart() {
//
//                        dialog.show();
//                    }
//
//                    @Override
//                    public void onError(ApiException e) {
//
//                        dialog.dismiss();
//                    }
//
//
//                });
    }

    @Override
    protected void initData() {



    }
}
