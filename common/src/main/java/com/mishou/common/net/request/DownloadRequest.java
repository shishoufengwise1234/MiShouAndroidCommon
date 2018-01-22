package com.mishou.common.net.request;

import android.support.annotation.NonNull;

import com.mishou.common.net.callback.MainDownloadProgressCallBack;
import com.mishou.common.net.function.RetryFunction;
import com.mishou.common.net.observer.DownloadSubscriber;
import com.mishou.common.net.util.OnlyLog;
import com.mishou.common.net.util.OnlyUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 * <p>
 * 下载请求
 */

public class DownloadRequest extends BaseRequest<DownloadRequest> {


    private String savePath = null;
    private String saveName = null;

    public DownloadRequest(String url) {
        super(url);
    }

    /**
     * 文件保存地址
     */
    // TODO: 17/11/16 文件保存地址问题 需考虑 以下相关问题 1、读取SD卡权限问题 2、兼容Android 7.0 fileprovider 问题
    public DownloadRequest savePath(@NonNull String path) {
        OnlyUtils.checkNotNull(path, "path is null");
        this.savePath = path;
        return this;
    }

    /**
     * 文件保存名称
     */
    public DownloadRequest saveName(@NonNull String fileName) {
        this.saveName = fileName;
        return this;
    }

    /**
     * 开始下载文件
     *
     * @param callBack 回调
     * @return Disposable
     */
    public Disposable startDownload(@NonNull final MainDownloadProgressCallBack callBack) {

        return create().createObservable()
                .retryWhen(new RetryFunction(retryCount, retryDelay, retryIncreaseDelay))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new DownloadSubscriber(baseContext, savePath, saveName, callBack));

    }

    @Override
    protected Observable<ResponseBody> createObservable() {
        OnlyLog.d("DownloadRequest createObservable() ....");
        return apiService.downloadFile(url);
    }


}
