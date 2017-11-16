package com.mishou.common.net.request;

import android.support.annotation.NonNull;

import com.mishou.common.net.callback.FileCallback;
import com.mishou.common.net.interceptor.DownloadInterceptor;
import com.mishou.common.net.util.OnlyLog;
import com.mishou.common.net.util.OnlyUtils;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 * 下载请求
 */

public class DownloadRequest extends BaseRequest<DownloadRequest>{


    private String savePath = null;
    private String saveName = null;

    public DownloadRequest(String url) {
        super(url);
    }

    /**
     * 文件保存地址
     *
     */
    // TODO: 17/11/16 文件保存地址问题 需考虑 以下相关问题 1、读取SD卡权限问题 2、兼容Android 7.0 fileprovider 问题
    public DownloadRequest savePath(@NonNull String path){
        OnlyUtils.checkNotNull(path,"path is null");
        this.savePath = path;
        return this;
    }

    /**
     * 文件保存名称
     */
    public DownloadRequest saveName(@NonNull String fileName){
        this.saveName = fileName;
        return this;
    }

    /**
     * 开始下载文件
     * @param callback 回调
     * @return retrofit -> Call
     */
    public Call<ResponseBody> startDownload(@NonNull FileCallback callback){

        OnlyUtils.checkNotNull(callback,"callback is null");

        DownloadInterceptor interceptor = new DownloadInterceptor();

        callback.setDestFileDir(savePath);
        callback.setDestFileName(saveName);

        Call<ResponseBody> targetCall = create().addInterceptor(interceptor)
                .createCall();
        targetCall.enqueue(callback);

        return targetCall;
    }

    private Call<ResponseBody> createCall(){
        OnlyUtils.checkNotNull(url,"url is null");
        return apiService.downloadFile(url);
    }

    @Override
    protected Observable<ResponseBody> createObservable() {
        OnlyLog.d("DownloadRequest createObservable() ....");
        return null;
    }


}
