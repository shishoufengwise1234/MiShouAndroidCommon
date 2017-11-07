package com.mishou.common.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.mishou.common.BuildConfig;
import com.mishou.common.ui.ActivityManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xingzhezuomeng on 16/8/8.
 */
public abstract class BaseAppcompatActivity extends RxAppCompatActivity {

    private static final String TAG = "BaseAppcompatActivity";

    protected Activity mActivity = null;

    protected Resources mResources = null;
    public Context mContext;

    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: "+this.getClass().getSimpleName());

        initWindow();
        mContext = this;
        setContentLayout(savedInstanceState);

        ActivityManager.getActivityManager().addActivity(this);

        bindView();

        mResources = getResources();

        initView();

        setOnListener();

        initData();

    }

    private void bindView() {

        unbinder = ButterKnife.bind(this);

    }

    protected void initWindow(){
        //去除title
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     *
     * 初始化view
     * @return
     */
    protected abstract void setContentLayout(Bundle savedInstanceState);

    /**
     * 初始化相关view
     */
    protected abstract void initView();

    /**
     * 为view 设置监听事件
     */
    protected abstract void setOnListener();

    /***
     * 初始化data
     */
    protected abstract void initData();


    /***
     * 初始化view
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@IdRes int viewId){
        return ButterKnife.findById(this,viewId);
    }

    /***
     *
     * 初始化view
     * @param view view 资源
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@NonNull View view, @IdRes int viewId){
        return ButterKnife.findById(view,viewId);
    }

    /***
     *
     * 根据 资源初始化view
     * @param dialog
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@NonNull Dialog dialog, @IdRes int viewId){
        return ButterKnife.findById(dialog,viewId);
    }


    @Override
    protected void onStart() {
        super.onStart();

        mActivity = this;

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onResume(mContext);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onPause(mContext);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null) unbinder.unbind();

        mActivity = null;

        ActivityManager.getActivityManager().finishActivity(this);

    }



}
