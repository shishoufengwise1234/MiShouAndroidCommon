package com.mishou.common.net.file.download;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${shishoufeng} on 2016/10/18 0018.
 * email:shishoufeng1227@126.com
 * 文件下载回调
 */
public abstract class FileCallback implements Callback<ResponseBody> {

    /**
     * 订阅下载进度
     */
    private CompositeDisposable rxSubscriptions = new CompositeDisposable();
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress();
    }

    public void onSuccess(File file) {
        unsubscribe();
    }

    /**
     * 进度刷新回调
     *
     * @param progress
     * @param total
     */
    public abstract void progress(long progress, long total);

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            saveFile(response);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.d("读写文件出错..."+e.getMessage());
        }
    }

    /**
     * 保存
     *
     * @param response
     */
    private File saveFile(Response<ResponseBody> response) throws Exception {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            onSuccess(file);
            return file;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 订阅文件下载进度
     */
    private void subscribeLoadProgress() {
        rxSubscriptions.add(FileRxBus.getDefault()
                .toObservable(FileLoadEvent.class)
                .toFlowable(BackpressureStrategy.BUFFER)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FileLoadEvent>() {
                    @Override
                    public void accept(FileLoadEvent fileLoadEvent) throws Exception {
                        progress(fileLoadEvent.getProgress(), fileLoadEvent.getTotal());

                    }
                }));
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    private void unsubscribe() {
        if (!rxSubscriptions.isDisposed()) {
            rxSubscriptions.dispose();
        }
    }


}
