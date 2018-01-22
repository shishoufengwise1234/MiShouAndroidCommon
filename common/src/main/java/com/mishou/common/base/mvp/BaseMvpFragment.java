package com.mishou.common.base.mvp;

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
import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 *
 * mvp  fragment 基类
 */

public abstract class BaseMvpFragment<P extends IBasePresenter> extends RxFragment implements IBaseView<P>{


    private static final String TAG = "BaseMvpFragment";

    //打印子类 类名 快速定位
    private String className = this.getClass().getSimpleName();

    protected Activity mActivity = null;

    protected Resources mResources = null;

    private Unbinder unbinder;

    protected P presenter;

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
     * @param baseMvpFragment
     * @param view
     */
    private void bindView(BaseMvpFragment baseMvpFragment, View view) {
        unbinder = ButterKnife.bind(baseMvpFragment,view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LogUtils.d(TAG,"onActivityCreated()"+className);

        presenter = createPresenter();

        onLogic(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        LogUtils.d(TAG,"onStart()"+className);

        if (presenter != null)
            presenter.start(this);
    }

    /***
     * 处理相关逻辑
     * @param savedInstanceState
     */
    protected abstract void onLogic(Bundle savedInstanceState);


    /***
     * 初始化view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@IdRes int viewId) {
        return (T) mActivity.findViewById(viewId);
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
        return (T) view.findViewById(viewId);
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
        return (T) dialog.findViewById(viewId);
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

        if (presenter != null)
            presenter.destroy();

        super.onDestroy();

        LogUtils.d(TAG,"onDestroy()"+className);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        LogUtils.d(TAG,"onDetach()"+className);

    }


}
