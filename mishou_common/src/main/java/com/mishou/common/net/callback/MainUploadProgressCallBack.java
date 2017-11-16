package com.mishou.common.net.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 * 上传进度回调
 *
 * 可在UI 线程中更新UI 的回调
 *
 * 内部使用 handler 回调至主线程
 */

public abstract class MainUploadProgressCallBack implements OnUploadProgressListener{


    private static final int RESPONSE_UPDATE = 0x02;

    private final Handler mHandler = new UIHandler(Looper.getMainLooper(), MainUploadProgressCallBack.this);

    //处理UI层的Handler子类
    private static class UIHandler extends Handler {
        //弱引用
        private final WeakReference<MainUploadProgressCallBack> mainUploadProgressCallBackWeakReference;

        public UIHandler(Looper looper, MainUploadProgressCallBack uiProgressResponseListener) {
            super(looper);
            mainUploadProgressCallBackWeakReference = new WeakReference<>(uiProgressResponseListener);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESPONSE_UPDATE:
                    MainUploadProgressCallBack mainUploadProgressCallBack = mainUploadProgressCallBackWeakReference.get();
                    if (mainUploadProgressCallBack != null) {
                        //获得进度实体类
                        ProgressModel progressModel = (ProgressModel) msg.obj;
                        //回调抽象方法
                        mainUploadProgressCallBack.onMainUploadProgress(progressModel.getCurrentBytes(),
                                progressModel.getContentLength(), progressModel.isDone());
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    /**
     *
     * @param bytesWritten 当前上传长度
     * @param contentLength 文件总长度
     * @param done 是否已经上传完成
     */
    @Override
    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
        //通过Handler发送进度消息
        Message message = Message.obtain();
        message.obj = new ProgressModel(bytesWritten, contentLength, done);
        message.what = RESPONSE_UPDATE;
        mHandler.sendMessage(message);
    }

    /**
     * UI层回调抽象方法
     *
     * @param bytesRead     当前读取响应体字节长度
     * @param contentLength 总字节长度
     * @param done          是否读取完成
     */
    protected abstract void onMainUploadProgress(long bytesRead, long contentLength, boolean done);

    public class ProgressModel implements Serializable {
        //当前读取字节长度
        private long currentBytes;
        //总字节长度
        private long contentLength;
        //是否读取完成
        private boolean done;

        public ProgressModel(long currentBytes, long contentLength, boolean done) {
            this.currentBytes = currentBytes;
            this.contentLength = contentLength;
            this.done = done;
        }

        public long getCurrentBytes() {
            return currentBytes;
        }

        public ProgressModel setCurrentBytes(long currentBytes) {
            this.currentBytes = currentBytes;
            return this;
        }

        public long getContentLength() {
            return contentLength;
        }

        public ProgressModel setContentLength(long contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        public boolean isDone() {
            return done;
        }

        public ProgressModel setDone(boolean done) {
            this.done = done;
            return this;
        }

        @Override
        public String toString() {
            return "ProgressModel{" +
                    "currentBytes=" + currentBytes +
                    ", contentLength=" + contentLength +
                    ", done=" + done +
                    '}';
        }
    }
}
