package com.mishou.demo;

import android.content.Context;

import com.mishou.common.application.BaseApplication;
import com.mishou.common.net.OnlyHttp;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 */

public class DemoApplication extends BaseApplication {


    private ActivityLifecycleCallbacks callbacks;

    @Override
    protected void onAttach(Context base) {




    }

    @Override
    protected void initSDK(Context mContext) {

        callbacks = new ActivityLifecycleCallback();

        registerActivityLifecycleCallbacks(callbacks);

        OnlyHttp.getInstance()
                .init(mContext)
                .setBaseUrl("")
                .debug(null,true)
                .addInterceptor(null)
                .addNetworkInterceptor(null);

    }

    @Override
    protected void clearData() {

        unregisterActivityLifecycleCallbacks(callbacks);
    }
}
