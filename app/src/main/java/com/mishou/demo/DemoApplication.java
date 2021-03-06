package com.mishou.demo;

import android.content.Context;

import com.mishou.common.application.BaseApplication;
import com.mishou.common.demo.BuildConfig;
import com.mishou.common.image.ImageLoader;
import com.mishou.common.net.OnlyHttp;
import com.mishou.common.utils.LogUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 */

public class DemoApplication extends BaseApplication {

    private static final String TAG = "Common";


    private ActivityLifecycleCallbacks callbacks;

    @Override
    protected void onAttach(Context base) {




    }

    @Override
    protected void initSDK(Context mContext) {

        callbacks = new ActivityLifecycleCallback();

        registerActivityLifecycleCallbacks(callbacks);

        //类库 log 开关
        LogUtils.configure(TAG,BuildConfig.DEBUG);

        OnlyHttp.getInstance()
                .init(mContext)
                .setRetryCount(3)
                .setRetryDelay(1000)
                .setRetryIncreaseDelay(1000)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .setBaseUrl(Constants.WEATHER_BASE)
                .debug(null,BuildConfig.DEBUG);

        //打开图片加载 debug 模式
        ImageLoader.getInstance().setDebug(mContext,BuildConfig.DEBUG);


        initLogger();

        //加载x5内核
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

                Logger.d("onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {

                Logger.d("onViewInitFinished  "+b);
            }
        });


        //初始化 leakcanary
        LeakCanary.install(this);


    }

    private void initLogger() {

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }

    @Override
    protected void clearData() {

        unregisterActivityLifecycleCallbacks(callbacks);
    }
}
