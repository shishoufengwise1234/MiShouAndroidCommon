package com.mishou.common.net.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orhanobut.logger.Logger;

/**
 * Created by shishoufeng on 2018/1/5.
 * email:shishoufeng1227@126.com
 * <p>
 * 上传/下载 进度数据封装
 * <p>
 * <p>
 * 来自：https://github.com/JessYanCoding/ProgressManager/blob/master/progress/src/main/java/me/jessyan/progressmanager/body/ProgressInfo.java
 */

public class ProgressInfo implements Parcelable {

    private long contentLength; //数据总长度
    private long currentBytes; //本次调用距离上一次被调用的间隔时间内上传或下载的byte长度
    private long id; //如果同一个 Url 地址,上一次的上传或下载操作都还没结束,
    //又开始了新的上传或下载操作(比如用户点击多次点击上传或下载同一个 Url 地址,当然你也可以在上层屏蔽掉用户的重复点击),
    //此 id (请求开始时的时间)就变得尤为重要,用来区分正在执行的进度信息,因为是以请求开始时的时间作为 id ,所以值越大,说明该请求越新
    private boolean finish; //进度是否完成

    public ProgressInfo() {

    }
    public ProgressInfo(long id) {
        this.id = id;
    }

    public void setCurrentbytes(long currentbytes) {
        this.currentBytes = currentbytes;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public long getCurrentbytes() {
        return currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean isFinish() {
        return finish;
    }

    /**
     * 获取百分比,该计算舍去了小数点,如果你想得到更精确的值,请自行计算
     */
    public int getPercent() {
        if (getCurrentbytes() <= 0 || getContentLength() <= 0){
            return 0;
        }
        int progress = (int) ((getCurrentbytes() * 100) / getContentLength());
        Logger.d("getPercent: progress = "+progress);

        return progress;
    }


    @Override
    public String toString() {
        return "ProgressInfo{" +
                "contentLength=" + contentLength +
                ", currentBytes=" + currentBytes +
                ", id=" + id +
                ", finish=" + finish +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.contentLength);
        dest.writeLong(this.currentBytes);
        dest.writeLong(this.id);
        dest.writeByte(this.finish ? (byte) 1 : (byte) 0);
    }

    protected ProgressInfo(Parcel in) {
        this.contentLength = in.readLong();
        this.currentBytes = in.readLong();
        this.id = in.readLong();
        this.finish = in.readByte() != 0;
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>() {
        @Override
        public ProgressInfo createFromParcel(Parcel source) {
            return new ProgressInfo(source);
        }

        @Override
        public ProgressInfo[] newArray(int size) {
            return new ProgressInfo[size];
        }
    };
}
