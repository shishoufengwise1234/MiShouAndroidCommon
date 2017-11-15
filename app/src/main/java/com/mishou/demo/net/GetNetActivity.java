package com.mishou.demo.net;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mishou.common.base.mvp.BaseMvpAppcompatActivity;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.net.callback.OnProgressDialogListener;
import com.mishou.common.net.callback.ProgressCallBack;
import com.mishou.demo.bean.User;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 */

public class GetNetActivity extends BaseMvpAppcompatActivity {


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

        Map<String,String> headers = new LinkedHashMap<>();
        Map<String,String> params = new LinkedHashMap<>();

        //返回 disposable 或者 observable 方便操作
        Disposable disposable = OnlyHttp.get("") //get 访问
                .addHeaders(headers) //添加请求头
                .addParams(params)//添加参数
                .syncRequest(true)//是否异步发送
                .addInterceptor(null)//添加自定义拦截器
                .addNetworkInterceptor(null)//添加网络拦截器
                .gson(new Gson())//添加自定义gson转换解析对象
                .execute(new ProgressCallBack<User>(new OnProgressDialogListener() {
                    @Override
                    public Dialog createDialog() {
                        //生成dialog 请求开始时 显示 取消dialog时取消请求  请求结束 关闭dialog
                        return new Dialog(GetNetActivity.this);
                    }
                }) {
                    @Override
                    public void onSuccess(User user) {
                        //回调数据
                    }
                });
        //关闭订阅者 防止内存泄漏 可结合 RxLifecycle 一起使用
        if (!disposable.isDisposed())
            disposable.dispose();


    }
}
