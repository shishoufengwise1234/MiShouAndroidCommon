package com.mishou.common.net.callback;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 上传进度监听
 */

public interface OnUploadProgressListener {

    /**
     *
     * 上传回调
     * @param bytesWritten 当前上传长度
     * @param contentLength 文件总长度
     * @param done 是否已经上传完成
     */
    void onUploadProgress(long bytesWritten, long contentLength, boolean done);
}
