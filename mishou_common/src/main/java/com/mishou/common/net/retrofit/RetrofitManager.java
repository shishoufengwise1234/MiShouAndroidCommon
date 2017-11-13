package com.mishou.common.net.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mishou.common.net.okhttp.OkHttp;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xingzhezuomeng on 16/8/9.
 */
public class RetrofitManager {

    private static final String TAG = "RetrofitManager";

    private static RetrofitManager retrofitManager = null;

    private Retrofit retrofit = null;

    private RetrofitManager(){

    }

    public static RetrofitManager getInstance(){
        if (retrofitManager == null)
            retrofitManager = new RetrofitManager();
        return retrofitManager;
    }


    public Retrofit getRetrofit(@NonNull Context context) {

        if (retrofit != null)
            return retrofit;

        if (context == null){
            Log.d(TAG, "getRetrofit() called with: " + "context = [" + context + "]");
            return null;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttp.getDefaultOkHttpClient(context))
                .build();

        return retrofit;
    }
}
