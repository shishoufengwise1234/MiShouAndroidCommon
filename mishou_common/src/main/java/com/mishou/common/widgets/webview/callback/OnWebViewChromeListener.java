package com.mishou.common.widgets.webview.callback;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

/**
 * Created by Administrator on 2015/12/22.
 *
 * 处理WebView  页面事件接口
 */
public interface OnWebViewChromeListener {

    /**
     * 覆盖 WebViewChrome 中 onJsAlert() 实现自定义操作
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    boolean OnJsAlert(WebView view, String url, String message, JsResult result);

    /***
     * 覆盖 WebViewChrome 中 onJsConfirm() 实现自定义操作
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    boolean OnJsConfirm(WebView view, String url, String message, JsResult result);

    /***
     * 覆盖 WebViewChrome 中 onJsBeforeUnload() 实现自定义操作
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    boolean OnJsBeforeUnload(WebView view, String url, String message, JsResult result);

    /***
     * 覆盖 WebViewChrome 中 onJsPrompt() 实现自定义操作
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result
     * @return
     */
    boolean OnJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);


}
