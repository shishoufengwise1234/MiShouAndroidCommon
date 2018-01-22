package com.mishou.common.net.model;

/**
 * Created by ${shishoufeng} on 2016/10/18 0018.
 * email:shishoufeng1227@126.com
 *
 * 文件下载事件封装
 */
public class FileLoadEvent {

    /**
     * 文件大小
     */
    long total;
    /**
     * 已下载大小
     */
    long progress;

    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadEvent(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }
}
