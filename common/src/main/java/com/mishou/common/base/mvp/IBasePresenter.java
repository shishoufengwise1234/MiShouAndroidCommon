package com.mishou.common.base.mvp;

import android.support.annotation.Nullable;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 * <p>
 * MVP - presenter 基类
 */

public interface IBasePresenter<V extends IBaseView> {


    /**
     * 建立 view 绑定关系
     * <p>
     * 在 activity 、 fragment onStart()中主动调用
     * <p>
     * 子类重写此方法可 初始化相关资源等
     *
     * @param view view 可能为空 需作出非空判断
     */
    void start(@Nullable V view);

    /**
     * 解除presenter 绑定
     * <p>
     * 在 activity fragment onDestroy() 时主动调用
     * <p>
     * 子类重写此方法可释放相关资源 回收数据等
     */
    void destroy();

}
