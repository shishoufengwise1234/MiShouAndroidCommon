package com.mishou.common.widgets.webview;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 *
 * https://x5.tencent.com/tbs/guide/sdkInit.html
 *
 * 对腾讯 x5内核webview 进行简单封装
 */

public class TbsWebView extends WebView{

    public TbsWebView(Context context) {
        super(context);
    }

    public TbsWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TbsWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TbsWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public TbsWebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }
}
