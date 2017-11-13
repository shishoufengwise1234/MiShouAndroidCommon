package com.mishou.common.net.file.download;

/**
 * Created by ${shishoufeng} on 2016/10/18 0018.
 * email:shishoufeng1227@126.com
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
