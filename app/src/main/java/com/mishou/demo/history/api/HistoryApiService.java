package com.mishou.demo.history.api;

import com.mishou.demo.bean.HistoryData;
import com.mishou.demo.bean.HistoryDetailsBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by ${shishoufeng} on 17/11/19.
 * email:shishoufeng1227@126.com
 */

public interface HistoryApiService {


    @GET
    Observable<HistoryData<List<HistoryDetailsBean>>> getHistoryDetails(@Url String url, @QueryMap Map<String,String> map);
}
