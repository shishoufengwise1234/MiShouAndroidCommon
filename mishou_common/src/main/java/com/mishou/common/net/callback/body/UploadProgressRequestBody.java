package com.mishou.common.net.callback.body;

import com.mishou.common.net.callback.OnUploadProgressListener;
import com.mishou.common.net.util.OnlyLog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 * <p>
 * 上传进度自定义监听
 */

public class UploadProgressRequestBody extends RequestBody {

    private RequestBody delegate;
    private OnUploadProgressListener onUploadProgressListener;


    public UploadProgressRequestBody(OnUploadProgressListener listener) {
        this.onUploadProgressListener = listener;
    }

    public UploadProgressRequestBody(RequestBody delegate, OnUploadProgressListener listener) {
        this.delegate = delegate;
        this.onUploadProgressListener = listener;
    }

    public void setRequestBody(RequestBody delegate) {
        this.delegate = delegate;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();

    }

    /**
     * 重写调用实际的响应体的contentLength
     */
    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            OnlyLog.e("UploadProgressRequestBody  contentLength() > " + e.getMessage());
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);

        delegate.writeTo(bufferedSink);

        bufferedSink.flush();
    }


    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;
        private long contentLength = 0;  //总字节长度，避免多次调用contentLength()方法
        private long lastRefreshUiTime;  //最后一次刷新的时间

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (contentLength <= 0) contentLength = contentLength(); //获得contentLength的值，后续不再调用
            //增加当前写入的字节数
            bytesWritten += byteCount;

            long curTime = System.currentTimeMillis();
            //每100毫秒刷新一次数据,防止频繁无用的刷新
            if (curTime - lastRefreshUiTime >= 100 || bytesWritten == contentLength) {

                if (onUploadProgressListener != null)
                    onUploadProgressListener.onUploadProgress(bytesWritten, contentLength, bytesWritten == contentLength);

                lastRefreshUiTime = System.currentTimeMillis();
            }
            OnlyLog.i("bytesWritten=" + bytesWritten + " ,totalBytesCount=" + contentLength);
        }
    }
}
