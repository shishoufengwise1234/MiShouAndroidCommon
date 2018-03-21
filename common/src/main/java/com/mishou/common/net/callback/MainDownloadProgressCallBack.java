package com.mishou.common.net.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.model.ProgressInfo;
import com.mishou.common.net.util.OnlyLog;

import java.lang.ref.WeakReference;

/**
 * Created by shishoufeng on 2018/1/5.
 * email:shishoufeng1227@126.com
 * <p>
 * 可在主线程中更新UI 的下载回调
 */

public abstract class MainDownloadProgressCallBack implements OnDownloadProgressListener {


    private static final int DOWNLOAD_PROGRESS = 0x1101;
    private static final int DOWNLOAD_START = 0x1102;
    private static final int DOWNLOAD_ERROR = 0x1103;
    private static final int DOWNLOAD_COMPLETED = 0x1104;

    private final Handler mHandler = new UIHandler(Looper.getMainLooper(), MainDownloadProgressCallBack.this);

    //处理UI层的Handler子类
    private static class UIHandler extends Handler {
        //弱引用
        private final WeakReference<MainDownloadProgressCallBack> mainUploadProgressCallBackWeakReference;

        public UIHandler(Looper looper, MainDownloadProgressCallBack uiProgressResponseListener) {
            super(looper);
            mainUploadProgressCallBackWeakReference = new WeakReference<>(uiProgressResponseListener);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case DOWNLOAD_START:    //开始下载
                    OnlyLog.d("DOWNLOAD_START  ");
                    MainDownloadProgressCallBack callBackStart = mainUploadProgressCallBackWeakReference.get();
                    if (callBackStart != null) {
                        callBackStart.onUIStart();
                    }
                    break;

                case DOWNLOAD_ERROR:    //下载异常
                    OnlyLog.d("DOWNLOAD_ERROR  ");
                    MainDownloadProgressCallBack callBackError = mainUploadProgressCallBackWeakReference.get();
                    ApiException exception = (ApiException) msg.obj;
                    if (callBackError != null) {
                        callBackError.onUIDownloadError(exception);
                    }

                    break;

                case DOWNLOAD_PROGRESS:     //下载回调
                    OnlyLog.d("DOWNLOAD_PROGRESS  ");
                    MainDownloadProgressCallBack callBack = mainUploadProgressCallBackWeakReference.get();
                    if (callBack != null) {
                        //获得进度实体类
                        ProgressInfo info = (ProgressInfo) msg.obj;
                        //回调抽象方法
                        if (info != null) {
                            callBack.onUIProgress(info);
                        } else {
                            OnlyLog.e("下载进度回调失败 info = null");
                        }
                    }
                    break;
                case DOWNLOAD_COMPLETED:    //下载完成
                    OnlyLog.d("DOWNLOAD_COMPLETED  ");
                    MainDownloadProgressCallBack callBackCompleted = mainUploadProgressCallBackWeakReference.get();
                    String filePath = (String) msg.obj;
                    if (callBackCompleted != null) {
                        callBackCompleted.onUIDownloadCompleted(filePath);
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    /**
     * 开始下载
     */
    @Override
    public void onStart() {
        OnlyLog.d("onStart()  ");

        Message message = new Message();
        message.what = DOWNLOAD_START;

        mHandler.sendMessage(message);
    }

    /**
     * 下载出错
     *
     * @param e 异常
     */
    @Override
    public void onError(ApiException e) {
        OnlyLog.d("onError()  ");

        Message message = new Message();
        message.what = DOWNLOAD_ERROR;
        message.obj = e;

        mHandler.sendMessage(message);
    }

    /**
     * 下载完成
     *
     * @param filePath 文件存储地址
     */
    @Override
    public void onDownloadCompleted(String filePath) {
        OnlyLog.d("onDownloadCompleted()  ");

        Message message = new Message();
        message.what = DOWNLOAD_COMPLETED;
        message.obj = filePath;

        mHandler.sendMessage(message);
    }

    /**
     * 发送消息
     *
     * @param info 下载回调封装类
     */
    @Override
    public void onProgress(ProgressInfo info) {
        OnlyLog.d("onProgress()  ");

        Message message = new Message();
        message.what = DOWNLOAD_PROGRESS;
        message.obj = info;

        mHandler.sendMessage(message);
    }

    /**
     * 开始下载
     */
    public abstract void onUIStart();

    /**
     * 进度回调
     * <p>
     * 可在 UI线程更新UI
     *
     * @param info 进度封装
     */
    public abstract void onUIProgress(ProgressInfo info);

    /**
     * 下载异常回调
     *
     * @param e 异常
     */
    public abstract void onUIDownloadError(ApiException e);


    /**
     * 下载完成
     *
     * @param filePath 文件路径
     */
    public abstract void onUIDownloadCompleted(String filePath);
}
