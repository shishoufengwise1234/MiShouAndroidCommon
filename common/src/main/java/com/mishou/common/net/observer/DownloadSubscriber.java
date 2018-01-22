package com.mishou.common.net.observer;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import com.mishou.common.net.callback.MainDownloadProgressCallBack;
import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.model.ProgressInfo;
import com.mishou.common.net.util.OnlyLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/21.
 * email:shishoufeng1227@126.com
 * <p>
 * 下载订阅者
 */

public class DownloadSubscriber extends BaseSubscriber<ResponseBody> {

    private static final String APK_CONTENT_TYPE = "application/vnd.android.package-archive";
    private static final String PNG_CONTENT_TYPE = "image/png";
    private static final String JPG_CONTENT_TYPE = "image/jpg";

    private String savePath;
    private String saveName;
    private MainDownloadProgressCallBack mCallBack;
    private Context context;

    private long mRefreshTime = OnlyConstants.DEFAULT_REFRESH_TIME;

    private ProgressInfo mProgressInfo;


    public DownloadSubscriber(Context context, String savePath, String saveName, MainDownloadProgressCallBack callBack) {
        super(context);
        this.context = context;
        this.saveName = saveName;
        this.savePath = savePath;
        this.mCallBack = callBack;
        //获取当前时间戳值 作为上传/下载 ID
        this.mProgressInfo = new ProgressInfo(System.currentTimeMillis());
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @Override
    protected void onStart() {
        super.onStart();
        OnlyLog.d("DownloadSubscriber -> onStart()");

        if (mCallBack != null)
            mCallBack.onStart();
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        OnlyLog.d("DownloadSubscriber -> onNext()");

        try {
            saveFile(responseBody);
        } catch (IOException e) {
            OnlyLog.e("saveFile", e);
            callbackError(e);
        }
    }

    /**
     * 保存文件
     *
     * @param body ResponseBody
     */
    private void saveFile(ResponseBody body) throws IOException {


        long lastRefreshTime = 0L;  //最后一次刷新的时间

        //文件后缀
        String fileSuffix = "";
        String bodyType = "";
        MediaType mediaType = body.contentType();
        if (mediaType != null) {
            bodyType = mediaType.toString();
        }
        if (!TextUtils.isEmpty(this.saveName)) {
            if (!this.saveName.contains(".")) {

                if (bodyType.equals(APK_CONTENT_TYPE)) {
                    fileSuffix = ".apk";
                } else if (bodyType.equals(PNG_CONTENT_TYPE)) {
                    fileSuffix = ".png";
                } else if (bodyType.equals(JPG_CONTENT_TYPE)) {
                    fileSuffix = ".jpg";
                } else {
                    if (mediaType != null) {
                        fileSuffix = "." + mediaType.subtype();
                    }
                }
                saveName = saveName + fileSuffix;
            }
        } else { //取当前时间戳命名
            saveName = System.currentTimeMillis() + fileSuffix;
        }

        if (this.savePath == null) {
            this.savePath = context.getExternalFilesDir(null) + File.separator + this.saveName;
        } else {
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            savePath = savePath + File.separator + saveName;
            savePath = savePath.replaceAll("//", "/");
        }

        OnlyLog.d("savePath = " + this.savePath);
        //构建保存文件
        File futureStudioIconFile = new File(this.savePath);

        //文件总长度
        final long fileCountSize = body.contentLength();
        OnlyLog.d("file contentLength ->" + fileCountSize);

        if (mProgressInfo.getContentLength() == 0) { //避免重复调用 contentLength()
            mProgressInfo.setContentLength(fileCountSize);
        }

        //文件流
        InputStream inputStream = body.byteStream();
        //输出文件流
        FileOutputStream fos = new FileOutputStream(futureStudioIconFile);
        //读取文件大小
        byte[] fileReader = new byte[1024 * 4];

        int bytesRead;
        long totalBytesRead = 0L;

        while (true) {

            bytesRead = inputStream.read(fileReader);
            if (bytesRead == -1) {  //读写完成

                break;
            }

            //写入文件
            fos.write(fileReader, 0, bytesRead);

            // 读取了多少文件
            totalBytesRead += bytesRead;

            //下载进度
            float progress = totalBytesRead * 1.0f / fileCountSize;

            //当前刷新时间
            long curTime = System.currentTimeMillis();

            if (curTime - lastRefreshTime >= mRefreshTime || progress == 1.0f || totalBytesRead == mProgressInfo.getContentLength()) {
                final long finalTotalBytesRead = totalBytesRead;

                if (mCallBack != null) {

                    mProgressInfo.setCurrentbytes(finalTotalBytesRead);
                    mProgressInfo.setFinish(finalTotalBytesRead == mProgressInfo.getContentLength());

                    //回调进度
                    mCallBack.onProgress(mProgressInfo);
                }
                lastRefreshTime = curTime;
            }
        }

        //刷新缓冲区
        fos.flush();

        //关闭文件流
        inputStream.close();
        fos.close();

        //回调完成
        if (mCallBack != null) {

            final String finalPath = savePath;

            mCallBack.onDownloadCompleted(finalPath);
        }

    }

    @Override
    public void onComplete() {
        super.onComplete();
        OnlyLog.d("DownloadSubscriber -> onComplete()");

    }

    @Override
    protected void onErrorMessage(ApiException exception) {
        OnlyLog.d("DownloadSubscriber -> onErrorMessage()");

        callbackError(exception);
    }

    /**
     * 处理异常
     *
     * @param exception 异常
     */

    private void callbackError(Exception exception) {

        OnlyLog.d("DownloadSubscriber --callbackError ---");

        if (mCallBack != null) {

            mCallBack.onError(new ApiException(exception, OnlyConstants.DOWNLOAD_FILE_ERROR));
        }
    }


}
