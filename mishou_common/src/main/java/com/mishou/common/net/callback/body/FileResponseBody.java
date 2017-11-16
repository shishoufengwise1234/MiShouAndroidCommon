package com.mishou.common.net.callback.body;


import com.mishou.common.net.event.FileRxBus;
import com.mishou.common.net.model.FileLoadEvent;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * 自定义 文件 ResponseBody
 */
public class FileResponseBody extends ResponseBody {

    Response originalResponse;

    public FileResponseBody(Response originalResponse) {
        this.originalResponse = originalResponse;
    }

    @Override
    public MediaType contentType() {
        try {
            return originalResponse.body().contentType();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long contentLength() {
        try {
            return originalResponse.body().contentLength();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public BufferedSource source() {
        try {
            return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
                long bytesReaded = 0;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                    FileRxBus.getDefault().post(new FileLoadEvent(contentLength(), bytesReaded));
                    return bytesRead;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
