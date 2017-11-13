package com.mishou.common.net.file.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ${shishoufeng} on 17/11/13.
 * email:shishoufeng1227@126.com
 *
 * 下载API
 */

public interface ApiDownLoad {


    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
