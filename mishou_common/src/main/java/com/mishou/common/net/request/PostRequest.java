package com.mishou.common.net.request;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/14.
 * email:shishoufeng1227@126.com
 *
 * post 请求
 */

public class PostRequest extends BaseBodyRequest<PostRequest>{


    public PostRequest(String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> createObservable() {
        return null;
    }
}
