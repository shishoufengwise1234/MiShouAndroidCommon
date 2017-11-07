package com.mishou.common.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xingzhezuomeng on 16/8/8.
 */
public abstract class BaseSupportFragment extends RxFragment {

    private static final String TAG = "BaseSupportFragment";


    protected Activity mActivity = null;

    protected Resources mResources = null;

    //view 是否创建已完成
    protected boolean isViewCreated = false;

    //fragment 是否可见
    protected boolean isVisibeToUser = false;

    //数据是否加载完成
    protected boolean isLoadDataCompleted = false;

    private Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null)
            mActivity = activity;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: "+this.getClass().getSimpleName());

        mResources = getResources();
    }

    /***
     * 设置fragment 懒加载 与viewpager 结合使用时调用 单个加载fragment 不调用
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

//        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted)
//            loadData();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutView(),container,false);

        bindView(this,view);

        setOnListener(view);

        isViewCreated = true;

        return view;
    }

    /***
     * 加载view
     * @return
     */
    protected abstract int getLayoutView();


    /***
     * 设置监听事件
     * @param view
     */
    protected abstract void setOnListener(View view);


    /**
     * 注解view
     * @param baseSupportFragment
     * @param view
     */
    private void bindView(BaseSupportFragment baseSupportFragment, View view) {
        unbinder = ButterKnife.bind(baseSupportFragment,view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //当fragment可见时加载数据
//        if (getUserVisibleHint()){
//            loadData();
//        }

        onLogic(savedInstanceState);

    }

    /***
     * 处理相关逻辑
     * @param savedInstanceState
     */
    protected abstract void onLogic(Bundle savedInstanceState);


//    /***
//     * 加载数据
//     */
//    protected abstract void loadData();

    /***
     * 初始化view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getViewById(@IdRes int viewId) {
        return ButterKnife.findById(getActivity(), viewId);
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
        return ButterKnife.findById(view, viewId);
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
        return ButterKnife.findById(dialog, viewId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
