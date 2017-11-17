package com.mishou.demo.zhihu.api;

import com.mishou.demo.zhihu.model.ZhihuData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public interface ZhihuApiService {


    @GET
    Observable<ZhihuData> getZhihuData(@Url String url);
}
