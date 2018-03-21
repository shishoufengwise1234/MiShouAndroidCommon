package com.mishou.common.net.callback;

/**
 * Created by ${shishoufeng} on 17/11/21.
 * email:shishoufeng1227@126.com
 *
 * 文件下载回调
 */

public abstract class DownloadCallback<T> extends CallBack<T> {

    public  DownloadCallback(){

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onSuccess(T t) {

    }

    /**
     *
     *下载进度回调
     * 可在UI线程更新UI
     *
     * @param bytesRead 当前下载长度
     * @param contentLength 文件总长度
     * @param done 是否下载完成
     */
    public abstract void onProgress(long bytesRead, long contentLength, boolean done );

    /**
     * 文件保存完成回调
     * @param filePath 文件存储地址
     */
    public abstract void onDownloadCompleted(String filePath);

}
