package com.mishou.common.net.request;

import com.mishou.common.net.annotation.UploadType;
import com.mishou.common.net.callback.OnUploadProgressListener;
import com.mishou.common.net.callback.body.UploadProgressRequestBody;
import com.mishou.common.net.model.HttpParams;
import com.mishou.common.net.util.OnlyUtils;
import com.mishou.common.net.util.RequestBodyUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 * <p>
 * body 传输数据基类
 */

public abstract class BaseBodyRequest<R extends BaseBodyRequest> extends BaseRequest<R> {


    protected String string;
    protected MediaType mediaType;
    //上传的Json
    protected String json;
    //上传的字节数据
    protected byte[] bs;
    //上传的对象
    protected Object object;
    //请求体
    protected RequestBody requestBody;

    private int mUploadType = PART;

    /**
     * 采用 MultipartBody.Part方式上传 推荐此方式上传
     */
    public static final int PART = 0x11;
    /**
     * 采用 Map RequestBody方式上传
     */
    public static final int BODY = 0x13;


    public BaseBodyRequest(String url) {
        super(url);

        this.baseParams = new HttpParams();
    }

    public R requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return (R) this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    public R string(String string) {
        this.string = string;
        this.mediaType = MediaType.parse("text/plain");
        return (R) this;
    }

    public R string(String string, String mediaType) {
        this.string = string;
        OnlyUtils.checkNotNull(mediaType, "mediaType==null");
        this.mediaType = MediaType.parse(mediaType);
        return (R) this;
    }

    public R object(Object object) {
        this.object = object;
        return (R) this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    public R json(String json) {
        this.json = json;
        return (R) this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    public R uploadBytes(byte[] bs) {
        this.bs = bs;
        return (R) this;
    }

    // 上传文件
    public R addFileparams(String key, File file, OnUploadProgressListener responseCallBack) {
        this.baseParams.put(key, file, responseCallBack);
        return (R) this;
    }

    public R addFileparams(String key, InputStream stream, String fileName, OnUploadProgressListener responseCallBack) {
        this.baseParams.put(key, stream, fileName, responseCallBack);
        return (R) this;
    }

    public R addFileparams(String key, byte[] bytes, String fileName, OnUploadProgressListener responseCallBack) {
        this.baseParams.put(key, bytes, fileName, responseCallBack);
        return (R) this;
    }

    public R addFileParams(String key, List<File> files, OnUploadProgressListener responseCallBack) {
        this.baseParams.putFileParams(key, files, responseCallBack);
        return (R) this;
    }

    public R addFileWrapperParams(String key, List<HttpParams.FileWrapper> fileWrappers) {
        this.baseParams.putFileWrapperParams(key, fileWrappers);
        return (R) this;
    }

    public R params(String key, File file, String fileName, OnUploadProgressListener responseCallBack) {
        this.baseParams.put(key, file, fileName, responseCallBack);
        return (R) this;
    }

    public <T> R addFileparams(String key, T file, String fileName, MediaType contentType, OnUploadProgressListener responseCallBack) {
        this.baseParams.put(key, file, fileName, contentType, responseCallBack);
        return (R) this;
    }

    /**
     * 上传文件的方式，默认part方式上传
     */
    public R uploadType(@UploadType int type) {
        this.mUploadType = type;
        return (R) this;
    }

    @Override
    protected Observable<ResponseBody> createObservable() {
        if (this.requestBody != null) { //自定义的请求体
            return apiService.postBody(url, this.requestBody);
        } else if (this.json != null) {//上传的Json
            return apiService.postJson(url, RequestBodyUtils.createJson(this.json));
        } else if (this.object != null) {//自定义的请求object
            return apiService.postBody(url, this.object);
        } else if (this.string != null) {//上传的文本内容
            RequestBody body = RequestBody.create(this.mediaType, this.string);
            return apiService.postBody(url, body);
        } else if (this.bs != null) {//上传的字节数据
            return apiService.postBody(url, RequestBodyUtils.createBytes(this.bs));
        }
        if (this.baseParams.getFileParamsMap().isEmpty()) {
            return apiService.post(url, this.baseParams.getUrlParamsMap());
        } else {
            if (this.mUploadType == PART) {//part方式上传
                return uploadFilesWithParts();
            } else {//body方式上传
                return uploadFilesWithBody();
            }
        }
    }

    /**
     * 拼接 part 方式上传文件
     *
     * @return
     */
    protected Observable<ResponseBody> uploadFilesWithParts() {
        List<MultipartBody.Part> parts = new ArrayList<>();
        //拼接参数键值对
        for (Map.Entry<String, String> mapEntry : this.baseParams.getUrlParamsMap().entrySet()) {
            parts.add(MultipartBody.Part.createFormData(mapEntry.getKey(), mapEntry.getValue()));
        }
        //拼接文件
        for (Map.Entry<String, List<HttpParams.FileWrapper>> entry : this.baseParams.getFileParamsMap().entrySet()) {
            List<HttpParams.FileWrapper> fileValues = entry.getValue();
            for (HttpParams.FileWrapper fileWrapper : fileValues) {
                MultipartBody.Part part = addFile(entry.getKey(), fileWrapper);
                parts.add(part);
            }
        }
        return apiService.uploadFiles(url, parts);
    }

    /**
     * 拼接map 方式长传文件类型
     *
     * @return
     */
    protected Observable<ResponseBody> uploadFilesWithBody() {
        Map<String, RequestBody> mBodyMap = new HashMap<>();
        //拼接参数键值对
        for (Map.Entry<String, String> mapEntry : this.baseParams.getUrlParamsMap().entrySet()) {
            mBodyMap.put(mapEntry.getKey(), RequestBodyUtils.createText(mapEntry.getValue()));
        }
        //拼接文件
        for (Map.Entry<String, List<HttpParams.FileWrapper>> entry : this.baseParams.getFileParamsMap().entrySet()) {
            List<HttpParams.FileWrapper> fileValues = entry.getValue();
            for (HttpParams.FileWrapper fileWrapper : fileValues) {
                RequestBody requestBody = getRequestBody(fileWrapper);
                UploadProgressRequestBody uploadProgressRequestBody = new UploadProgressRequestBody(requestBody, fileWrapper.getOnUploadProgressListener());
                mBodyMap.put(entry.getKey(), uploadProgressRequestBody);
            }
        }
        return apiService.uploadFiles(url, mBodyMap);
    }

    /**
     * 构建文件
     *
     * @param key         健
     * @param fileWrapper 文件对象
     * @return MultipartBody.Part 文件片段
     */
    private MultipartBody.Part addFile(String key, HttpParams.FileWrapper fileWrapper) {
        //MediaType.parse("application/octet-stream", file)
        RequestBody requestBody = getRequestBody(fileWrapper);

        OnlyUtils.checkNotNull(requestBody, "requestBody==null fileWrapper.file must is File/InputStream/byte[]");
        //包装RequestBody，在其内部实现上传进度监听
        if (fileWrapper.getOnUploadProgressListener() != null) {
            UploadProgressRequestBody uploadProgressRequestBody = new UploadProgressRequestBody(requestBody, fileWrapper.getOnUploadProgressListener());
            return MultipartBody.Part.createFormData(key, fileWrapper.getFileName(), uploadProgressRequestBody);
        } else {
            return MultipartBody.Part.createFormData(key, fileWrapper.getFileName(), requestBody);
        }
    }


    /**
     * 根据文件类型 封装不同 RequestBody
     *
     * @param fileWrapper FileWrapper
     * @return RequestBody
     */
    private RequestBody getRequestBody(HttpParams.FileWrapper fileWrapper) {
        RequestBody requestBody = null;
        if (fileWrapper.file instanceof File) {
            requestBody = RequestBody.create(fileWrapper.getContentType(), (File) fileWrapper.file);
        } else if (fileWrapper.file instanceof InputStream) {
            //requestBody = RequestBodyUtils.create(RequestBodyUtils.MEDIA_TYPE_MARKDOWN, (InputStream) fileWrapper.file);
            requestBody = RequestBodyUtils.create(fileWrapper.getContentType(), (InputStream) fileWrapper.file);
        } else if (fileWrapper.file instanceof byte[]) {
            requestBody = RequestBody.create(fileWrapper.getContentType(), (byte[]) fileWrapper.file);
        }
        return requestBody;
    }
}
