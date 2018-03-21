package com.mishou.common.net.callback.body;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.mishou.common.net.callback.OnDownloadProgressListener;
import com.mishou.common.net.config.OnlyConstants;
import com.mishou.common.net.exception.ApiException;
import com.mishou.common.net.model.ProgressInfo;
import com.mishou.common.net.util.OnlyLog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by shishoufeng on 2018/1/5.
 * email:shishoufeng1227@126.com
 * <p>
 * 下载进度监听
 */

public class DownloadProgressResponseBody extends ResponseBody {


    private OnDownloadProgressListener onDownloadProgressListener;
    private Response targetResponse;

    private final ProgressInfo mProgressInfo;

    private long mRefreshTime = OnlyConstants.DEFAULT_REFRESH_TIME;

    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    public DownloadProgressResponseBody(Response response, OnDownloadProgressListener listener) {
        this.targetResponse = response;
        this.onDownloadProgressListener = listener;//OnDownloadProgressListener listener this is 回调

        //获取当前时间戳值 作为上传/下载 ID
        this.mProgressInfo = new ProgressInfo();
    }

    @Override
    public MediaType contentType() {
        try {
            return targetResponse.body().contentType();
        } catch (Exception e) {
            OnlyLog.e("download", e);
            return null;
        }
    }

    @Override
    public long contentLength() {
        try {
            return targetResponse.body().contentLength();
        } catch (Exception e) {
            e.printStackTrace();
            OnlyLog.e("download", e);
            return -1L;
        }
    }

    @Override
    public BufferedSource source() {

        return Okio.buffer(source(targetResponse.body().source()));
    }


    private Source source(Source source) {

        return new ForwardingSource(source) {

            private long totalBytesRead = 0L;
            private long lastRefreshTime = 0L;  //最后一次刷新的时间
            private long tempSize = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = 0L;
                try {
                    bytesRead = super.read(sink, byteCount);
                } catch (IOException e) {
                    e.printStackTrace();

                    OnlyLog.e(e);
                    //回调通知失败
                    if (onDownloadProgressListener != null) {
                        onDownloadProgressListener.onError(new ApiException("下载出错", OnlyConstants.DOWNLOAD_FILE_ERROR));
                    }

                    throw e;
                }
                if (mProgressInfo.getContentLength() == 0) { //避免重复调用 contentLength()
                    mProgressInfo.setContentLength(DownloadProgressResponseBody.this.contentLength());
                }
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                tempSize += bytesRead != -1 ? bytesRead : 0;

                long curTime = SystemClock.elapsedRealtime();

                if (curTime - lastRefreshTime >= mRefreshTime || bytesRead == -1 || totalBytesRead == mProgressInfo.getContentLength()) {
                    final long finalBytesRead = bytesRead;
                    final long finalTotalBytesRead = totalBytesRead;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            if (onDownloadProgressListener != null) {

                                mProgressInfo.setCurrentbytes(finalTotalBytesRead);
                                mProgressInfo.setFinish(finalBytesRead == -1 && finalTotalBytesRead == mProgressInfo.getContentLength());

                                //回调进度
                                onDownloadProgressListener.onProgress(mProgressInfo);
                            }

                        }
                    });

                    lastRefreshTime = curTime;
                    tempSize = 0;
                }
                return bytesRead;
            }
        };
    }
}
