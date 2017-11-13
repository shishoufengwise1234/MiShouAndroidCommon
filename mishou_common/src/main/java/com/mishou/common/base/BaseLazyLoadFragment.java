package com.mishou.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mishou.common.utils.LogUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 *
 * 懒加载 基类 fragment
 */

public abstract class BaseLazyLoadFragment extends RxFragment {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    private static final String TAG = "BaseLazyLoadFragment";

    //打印子类 类名 快速定位
    private String className = this.getClass().getSimpleName();

    protected Activity mActivity = null;

    protected Resources mResources = null;

    private Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        LogUtils.d(TAG,"onAttach()" +className);

        if (activity != null)
            mActivity = activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.d(TAG,"onCreate()" +className);

        mResources = getResources();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LogUtils.d(TAG,"onCreateView()" +className);

        View view = inflater.inflate(getLayoutView(),container,false);

        bindView(this,view);

        setOnListener(view);

        return view;


    }

    /***
     * 加载view
     * @return layoutRes
     */
    protected abstract int getLayoutView();


    /***
     * 设置监听事件
     * @param view contentView
     */
    protected abstract void setOnListener(View view);


    /**
     * 注解view
     * @param baseLazyLoadFragment
     * @param view
     */
    private void bindView(BaseLazyLoadFragment baseLazyLoadFragment, View view) {
        unbinder = ButterKnife.bind(baseLazyLoadFragment,view);
    }

    /**
     * 判断view 是否已经可见
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }


    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LogUtils.d(TAG,"onActivityCreated()"+className);

        onLogic(savedInstanceState);

        isViewInitiated = true;
        prepareFetchData();

    }

    @Override
    public void onStart() {
        super.onStart();

        LogUtils.d(TAG,"onStart()"+className);
    }

    /***
     * 处理相关逻辑
     * @param savedInstanceState
     */
    protected abstract void onLogic(Bundle savedInstanceState);

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /***
     * 初始化view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@IdRes int viewId) {
        return mActivity.findViewById(viewId);
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
    public void onPause() {
        super.onPause();

        LogUtils.d(TAG,"onPause()"+className);
    }

    @Override
    public void onStop() {
        super.onStop();

        LogUtils.d(TAG,"onStop()"+className);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        LogUtils.d(TAG,"onDestroyView()"+className);

        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LogUtils.d(TAG,"onDestroy()"+className);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        LogUtils.d(TAG,"onDetach()"+className);

    }


}
