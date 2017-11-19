package com.mishou.common.net.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 * <p>
 * <p>
 * API service 基本访问方法
 * <p>
 * 1、减少API service 增加而代码产生冗余
 * 2、降低耦合
 * 3、隔离业务层
 */

public interface ApiService {


    /**
     * post 普通请求
     *
     * @param url  地址
     * @param maps 参数 map
     * @return Observable
     */
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> maps);

    /**
     * 传输 封装类型数据
     * 其中 对象数据是被转换成 json 数据
     *
     * @param url    地址
     * @param object class对象
     * @return Observable
     */
    @POST
    Observable<ResponseBody> postBody(@Url String url, @Body Object object);

    /**
     * get 请求
     *
     * @param url  地址
     * @param maps 参数
     * @return Observable
     */
    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * delete 请求
     *
     * @param url  地址
     * @param maps 参数
     * @return Observable
     */
    @DELETE
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * put 简单请求
     *
     * @param url  地址
     * @param maps 参数
     * @return Observable
     */
    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * post 请求 发送对象
     *
     * @param url    地址
     * @param object class 对象
     * @return Observable
     */
    @POST
    Observable<ResponseBody> putBody(@Url String url, @Body Object object);


    /**
     * 上传文件
     *
     * @param url  地址
     * @param maps 参数
     * @return Observable
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);

    /**
     * 采用 MultipartBody.Part 上传文件
     *
     * @param url   地址
     * @param parts MultipartBody.Part
     * @return Observable
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @Part() List<MultipartBody.Part> parts);

    /**
     * 下载文件
     *
     * @param fileUrl 文件地址
     * @return call
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * 发送json 数据
     *
     * @param url      地址
     * @param jsonBody json 请求体
     * @return Observable
     */
    @POST
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonBody);

    /**
     * 自定义请求
     *
     * @param url  地址
     * @param body 请求体
     * @return Observable
     */
    @POST
    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody body);
}
