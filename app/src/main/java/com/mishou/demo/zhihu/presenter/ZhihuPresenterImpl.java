package com.mishou.demo.zhihu.presenter;

import android.support.annotation.Nullable;

import com.mishou.common.net.OnlyHttp;
import com.mishou.demo.Constants;
import com.mishou.demo.zhihu.api.ZhihuApiService;
import com.mishou.demo.zhihu.contract.ZhihuContract;
import com.mishou.demo.zhihu.model.ZhihuData;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public class ZhihuPresenterImpl implements ZhihuContract.Presenter {

    private ZhihuContract.View view;

    public ZhihuPresenterImpl(ZhihuContract.View view){
        this.view = view;
    }





    @Override
    public void loadZhihuData() {

        view.onShowLoading();

        OnlyHttp.custom(Constants.ZHIHU_LAST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .createRetrofit()
                .createApiService(ZhihuApiService.class)
                .getZhihuData(Constants.ZHIHU_LAST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuData>() {

                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ZhihuData data) {

                        view.showDataSuccess(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                        view.showDataError();
                    }

                    @Override
                    public void onComplete() {

                        OnlyHttp.cancelDisposable(disposable);
                    }
                });

    }


    @Override
    public void start(@Nullable ZhihuContract.View view) {

    }

    @Override
    public void destroy() {

    }
}
