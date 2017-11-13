package com.mishou.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.mishou.common.BuildConfig;
import com.mishou.common.manager.ActivityManager;
import com.mishou.common.utils.LogUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 *
 *activity 基类
 *
 */
public abstract class BaseAppcompatActivity extends RxAppCompatActivity {

    private static final String TAG = "BaseAppcompatActivity";

    //打印子类 类名 快速定位
    private String className = this.getClass().getSimpleName();

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
        setContentView(getLayoutView());

        ActivityManager.getActivityManager().addActivity(this);

        bindView();

        mResources = getResources();

        initView(savedInstanceState);

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

    /***
     * 加载view
     * @return view layoutRes
     */
    protected abstract int getLayoutView();

    /**
     * 初始化相关view
     * @param savedInstanceState bundle 数据
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

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
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@IdRes int viewId) {
        return this.findViewById(viewId);
    }

    /***
     * 初始化view
     *
     * @param view   view 资源
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@NonNull View view, @IdRes int viewId) {
        return view.findViewById(viewId);
    }

    /***
     * 根据 资源初始化view
     *
     * @param dialog
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@NonNull Dialog dialog, @IdRes int viewId) {
        return dialog.findViewById(viewId);
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(TAG, "onStart: "+className);
        mActivity = this;

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        LogUtils.d(TAG, "onReStart: "+className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume: "+className);

        if (!BuildConfig.DEBUG) {
            MobclickAgent.onResume(mContext);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        LogUtils.d(TAG, "onPause: "+className);

        if (!BuildConfig.DEBUG) {
            MobclickAgent.onPause(mContext);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        LogUtils.d(TAG, "onStop: "+className);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LogUtils.d(TAG, "onDestroy: "+className);

        if (unbinder != null) unbinder.unbind();

        ActivityManager.getActivityManager().finishActivity(this);

    }



}
