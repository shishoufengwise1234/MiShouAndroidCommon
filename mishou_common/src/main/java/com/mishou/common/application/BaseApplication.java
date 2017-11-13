package com.mishou.common.application;

import android.app.Application;
import android.content.Context;

import com.mishou.common.utils.LogUtils;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 *
 * 基类 application
 */

public abstract class BaseApplication extends Application{


    private static final String TAG = "BaseApplication";

    private static Context mContext;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        LogUtils.d(TAG,"attachBaseContext()");

        onAttach(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.d("onCreate()");

        mContext = getApplicationContext();

        initSDK(mContext);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        LogUtils.e(TAG,"onLowMemory()");

        clearData();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        LogUtils.e(TAG,"onTerminate()");
    }

    /**
     * 初始化SDK
     * @param base
     */
    protected abstract void onAttach(Context base);

    /***
     *
     * 初始化 相关第三方SDK
     * @param mContext
     */
    protected abstract void initSDK(Context mContext);


    /**
     * 内存回收 清除数据
     */
    protected abstract void clearData();



    public static Context getContext() {
        return mContext;

    }
}
