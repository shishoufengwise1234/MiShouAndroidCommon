package com.mishou.common.net.observer;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mishou.common.net.exception.ApiException;

import okhttp3.ResponseBody;

/**
 * Created by ${shishoufeng} on 17/11/16.
 * email:shishoufeng1227@126.com
 *
 * 下载文件服务订阅者
 */

public class DownloadSubscriber extends BaseSubscriber<ResponseBody>{




    public DownloadSubscriber(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onErrorMessage(ApiException exception) {

    }
}
