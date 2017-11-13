package com.mishou.common.base.mvp;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 *
 * MVP - view 基类
 *
 * view  base
 */

public interface IBaseView<P> {

    /**
     *注入 presenter
     * @param presenter 目标presenter
     */
    void setPresenter(P presenter);

    /**
     * 显示加载动画
     */
    void onShowLoading();

    /**
     * 隐藏加载
     */
    void onHideLoading();

    /**
     * 显示网络错误
     */
    void onShowNetError();


}
