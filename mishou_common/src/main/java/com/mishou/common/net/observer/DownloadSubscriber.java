package com.mishou.common.net.observer;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import com.mishou.common.net.callback.DownloadCallback;
import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.util.OnlyLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;

/**
 * Created by ${shishoufeng} on 17/11/21.
 * email:shishoufeng1227@126.com
 * <p>
 * 下载订阅者
 */

public class DownloadSubscriber<ResponseBody extends okhttp3.ResponseBody> extends BaseSubscriber<ResponseBody> {

    private static String APK_CONTENT_TYPE = "application/vnd.android.package-archive";
    private static String PNG_CONTENT_TYPE = "image/png";
    private static String JPG_CONTENT_TYPE = "image/jpg";

    private String savePath;
    private String saveName;
    private DownloadCallback mCallBack;

    private Context context;
    private long lastRefreshUiTime;


    public DownloadSubscriber(Context context, String savePath, String saveName, DownloadCallback callBack) {
        super(context);
        this.context = context;
        this.saveName = saveName;
        this.savePath = savePath;
        this.mCallBack = callBack;
        this.lastRefreshUiTime = System.currentTimeMillis();

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

        saveFile(responseBody);
    }

    /**
     * 保存文件
     *
     * @param body ResponseBody
     */
    private void saveFile(ResponseBody body) {

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
        //开始io读写
        try {
            //构建保存文件
            File futureStudioIconFile = new File(this.savePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                //文件总长度
                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                OnlyLog.d("file contentLength ->" + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    OnlyLog.d("file download: " + fileSizeDownloaded + " of " + fileSize);

                    //下载进度
                    float progress = fileSizeDownloaded * 1.0f / fileSize;
                    long curTime = System.currentTimeMillis();
                    OnlyLog.d("progress = " + progress + "  curtime = " + curTime + "lastRefreshUiTime = " + lastRefreshUiTime);


                    //每200毫秒刷新一次数据,防止频繁更新进度
                    if (curTime - lastRefreshUiTime >= 200 || progress == 1.0f) {
                        if (mCallBack != null) {

                            final long finalFileSizeDownloaded = fileSizeDownloaded;

                            Observable.just(finalFileSizeDownloaded)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Long>() {
                                        @Override
                                        public void accept(@NonNull Long aLong) throws Exception {
                                            OnlyLog.d("saveFile ----");
                                            mCallBack.onProgress(finalFileSizeDownloaded, fileSize, finalFileSizeDownloaded == fileSize);

                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(@NonNull Throwable throwable) throws Exception {
                                            OnlyLog.d("saveFile ---error-");
                                        }
                                    });
                        }

                        lastRefreshUiTime = System.currentTimeMillis();
                    }
                }
                //刷新缓冲区
                outputStream.flush();

                OnlyLog.d("file downloaded: " + fileSizeDownloaded + " of " + fileSize);

                //回调完成
                if (mCallBack != null) {

                    final String finalPath = savePath;
                    Observable.just(finalPath)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {
                                    OnlyLog.d("saveFile ---onDownloadCompleted()-");
                                    mCallBack.onDownloadCompleted(finalPath);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                                    OnlyLog.d("saveFile ---onDownloadCompleted()- error");
                                }
                            });
                    OnlyLog.d("file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                }

            } catch (IOException e) {
                callbackError(e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            callbackError(e);
        }
    }


    @Override
    public void onComplete() {
        super.onComplete();
        OnlyLog.d("DownloadSubscriber -> onStart()");

//        if (callBack != null)
//            callBack.onCompleted();

    }

    @Override
    protected void onErrorMessage(ApiException exception) {
        OnlyLog.d("DownloadSubscriber -> onStart()");

        callbackError(exception);
    }

    /**
     * 处理异常
     *
     * @param exception 异常
     */
    private void callbackError(Exception exception) {

        if (mCallBack != null) {

            Observable.just(new ApiException(exception, OnlyConstants.ERROR_CODE.DOWNLOAD_FILE_ERROR))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ApiException>() {
                        @Override
                        public void accept(ApiException e) throws Exception {
                            OnlyLog.d("DownloadSubscriber --callbackError()-- accept -> " + e);
                            mCallBack.onError(e);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            OnlyLog.d("DownloadSubscriber --callbackError()-- accept -> " + throwable);

                        }
                    });

        }
    }


}
