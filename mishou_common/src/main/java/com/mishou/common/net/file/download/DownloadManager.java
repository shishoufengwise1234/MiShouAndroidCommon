package com.mishou.common.net.file.download;

import android.support.annotation.NonNull;

import com.mishou.common.net.okhttp.OkHttp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${shishoufeng} on 2016/10/18 0018.
 * email:shishoufeng1227@126.com
 * <p/>
 * 文件下载
 */
public class DownloadManager {

    private static DownloadManager manager;
    private ApiDownLoad apiService = null;

    private static final String BASE_DOWN_LOAD_URL = "http://mishou.com/";

    /***
     * 下载call对象
     */
    private Call<ResponseBody> responseBodyCall;

    public static DownloadManager getManager() {
        if (manager != null)
            return manager;

        manager = new DownloadManager();
        return manager;
    }

    private DownloadManager() {
        initApiService();
    }

    /**
     * 获取ApiService 对象
     *
     * @return
     */
    private void initApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_DOWN_LOAD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttp.getFileOkHttpClient())
                .build();
        apiService = retrofit.create(ApiDownLoad.class);
    }

    /**
     * 下载文件
     *
     * @param filePath 文件地址
     * @param fileCallback
     */
    public void downloadFile(@NonNull String filePath, @NonNull FileCallback fileCallback) {
        if (apiService == null) {
            initApiService();
        } else {
            this.responseBodyCall = apiService.downloadFile(filePath);
            this.responseBodyCall.enqueue(fileCallback);
        }

    }

    /**
     * 取消下载
     */
    public void cancelDownload() {
        if (responseBodyCall != null && responseBodyCall.isCanceled() == false) {
            responseBodyCall.cancel();
        }
    }


}
