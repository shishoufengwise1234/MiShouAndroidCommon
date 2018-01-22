package com.mishou.common.net.model;

import com.mishou.common.net.callback.OnUploadProgressListener;

import java.io.File;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 *
 * 请求参数封装
 */

public class HttpParams {

    /**
     * 普通的键值对参数
     */
    private LinkedHashMap<String, String> urlParamsMap;
    /**
     * 文件的键值对参数
     */
    private LinkedHashMap<String, List<FileWrapper>> fileParamsMap;

    public HttpParams() {
        init();
    }

    public HttpParams(String key, String value) {
        init();
        put(key, value);
    }

    private void init() {
        urlParamsMap = new LinkedHashMap<>();
        fileParamsMap = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, String> getUrlParamsMap() {
        return urlParamsMap;
    }

    public LinkedHashMap<String, List<FileWrapper>> getFileParamsMap() {
        return fileParamsMap;
    }

    public void put(HttpParams params) {
        if (params != null) {
            if (params.urlParamsMap != null && !params.urlParamsMap.isEmpty())
                urlParamsMap.putAll(params.urlParamsMap);

            if (params.fileParamsMap != null && !params.fileParamsMap.isEmpty()) {
                fileParamsMap.putAll(params.fileParamsMap);
            }
        }
    }

    /**
     * 添加上传参数 URL 中
     */
    public void put(Map<String, String> params) {
        if (params == null || params.isEmpty()) return;
        this.urlParamsMap.putAll(params);
    }

    /**
     * 添加上传 参数 URL 中
     */
    public void put(String key, String value) {
        this.urlParamsMap.put(key, value);
    }

    //添加文件参数
    public <T extends File> void put(String key, T file, OnUploadProgressListener onUploadProgressListener) {
        put(key, file, file.getName(), onUploadProgressListener);
    }

    public <T extends File> void put(String key, T file, String fileName, OnUploadProgressListener onUploadProgressListener) {
        put(key, file, fileName, guessMimeType(fileName), onUploadProgressListener);
    }

    public <T extends InputStream> void put(String key, T file, String fileName, OnUploadProgressListener onUploadProgressListener) {
        put(key, file, fileName, guessMimeType(fileName), onUploadProgressListener);
    }

    public void put(String key, byte[] bytes, String fileName, OnUploadProgressListener onUploadProgressListener) {
        put(key, bytes, fileName, guessMimeType(fileName), onUploadProgressListener);
    }

    public void put(String key, FileWrapper fileWrapper) {
        if (key != null && fileWrapper != null) {
            put(key, fileWrapper.file, fileWrapper.fileName, fileWrapper.contentType, fileWrapper.onUploadProgressListener);
        }
    }

    public <T> void put(String key, T countent, String fileName, MediaType contentType, OnUploadProgressListener onUploadProgressListener) {
        if (key != null) {
            List<FileWrapper> fileWrappers = fileParamsMap.get(key);
            if (fileWrappers == null) {
                fileWrappers = new ArrayList<>();
                fileParamsMap.put(key, fileWrappers);
            }
            fileWrappers.add(new FileWrapper(countent, fileName, contentType, onUploadProgressListener));
        }
    }

    public <T extends File> void putFileParams(String key, List<T> files, OnUploadProgressListener onUploadProgressListener) {
        if (key != null && files != null && !files.isEmpty()) {
            for (File file : files) {
                put(key, file, onUploadProgressListener);
            }
        }
    }

    public void putFileWrapperParams(String key, List<FileWrapper> fileWrappers) {
        if (key != null && fileWrappers != null && !fileWrappers.isEmpty()) {
            for (FileWrapper fileWrapper : fileWrappers) {
                put(key, fileWrapper);
            }
        }
    }

    public void removeUrl(String key) {
        urlParamsMap.remove(key);
    }

    public void removeFile(String key) {
        fileParamsMap.remove(key);
    }

    public void remove(String key) {
        removeUrl(key);
        removeFile(key);
    }

    public void clear() {
        urlParamsMap.clear();
        fileParamsMap.clear();
    }

    private MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        path = path.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }

    /**
     * 文件类型的包装类
     */
    public static class FileWrapper<T> {
        public T file;//可以是
        private String fileName;
        private MediaType contentType;
        private long fileSize;
        private OnUploadProgressListener onUploadProgressListener;

        public FileWrapper(T file, String fileName, MediaType contentType,
                           OnUploadProgressListener onUploadProgressListener) {
            this.file = file;
            this.fileName = fileName;
            this.contentType = contentType;
            if (file instanceof File) {
                this.fileSize = ((File) file).length();
            } else if (file instanceof byte[]) {
                this.fileSize = ((byte[]) file).length;
            }
            this.onUploadProgressListener = onUploadProgressListener;
        }

        public MediaType getContentType() {
            return contentType;
        }

        public void setContentType(MediaType contentType) {
            this.contentType = contentType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public OnUploadProgressListener getOnUploadProgressListener() {
            return onUploadProgressListener;
        }

        public void setOnUploadProgressListener(OnUploadProgressListener onUploadProgressListener) {
            this.onUploadProgressListener = onUploadProgressListener;
        }

        @Override
        public String toString() {
            return "FileWrapper{" + "countent=" + file + ", fileName='" + fileName + ", contentType=" + contentType + ", fileSize=" + fileSize + '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParamsMap.entrySet()) {
            if (result.length() > 0) result.append("&");
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        for (ConcurrentHashMap.Entry<String, List<FileWrapper>> entry : fileParamsMap.entrySet()) {
            if (result.length() > 0) result.append("&");
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return result.toString();
    }
}
