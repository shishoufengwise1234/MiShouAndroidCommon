package com.mishou.common.base.mvp;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 * <p>
 * MVP - view 基类
 * <p>
 * view  base
 */

public interface IBaseView<P extends IBasePresenter> {


    /**
     * 注入 presenter
     *
     * @return 目标presenter
     */
    P createPresenter();

    /**
     * 显示加载动画
     *
     * @param state 拓展参数
     */
    void onShowLoading(int state);

    /**
     * 隐藏加载
     */
    void onHideLoading();

    /**
     * 显示网络错误
     *
     * @param state 拓展参数
     */
    void onShowNetError(int state);


}
