package com.mishou.demo.net;

import com.mishou.demo.bean.NowResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public interface CustomApiService {



    @GET
    Observable<NowResult> getWeather(@Url String url, @QueryMap Map<String,String> map);
}
