package com.mishou.common.net.callback;

import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.model.ProgressInfo;

/**
 * Created by shishoufeng on 2018/1/5.
 * email:shishoufeng1227@126.com
 * <p>
 * 下载进度回调
 */

public interface OnDownloadProgressListener {


    /**
     * 开始下载回调
     */
    void onStart();

    /**
     * 异常回调
     *
     * @param e ApiException
     */
    void onError(ApiException e);

    /**
     * 下载进度回调
     *
     * @param info 下载回调封装类
     */
    void onProgress(ProgressInfo info);

    /**
     * 文件保存完成回调
     *
     * @param filePath 文件存储地址
     */
    void onDownloadCompleted(String filePath);
}
