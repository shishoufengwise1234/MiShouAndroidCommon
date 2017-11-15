package com.mishou.common.net.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mishou.common.net.request.BaseBodyRequest.BODY;
import static com.mishou.common.net.request.BaseBodyRequest.PART;

/**
 * Created by ${shishoufeng} on 17/11/15.
 * email:shishoufeng1227@126.com
 *
 * 上传文件方式
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({PART,BODY})
public @interface UploadType {
}
