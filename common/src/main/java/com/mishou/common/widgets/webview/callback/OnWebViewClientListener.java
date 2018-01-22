package com.mishou.common.widgets.webview.callback;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * Created by Administrator on 2015/12/22.
 *
 * 处理WebView 加载URL 方法  接口
 */
public interface OnWebViewClientListener {

    /***
     * 覆盖WebViewClient 中onLoadResource() 实现自定义操作
     * @param view
     * @param url
     */
    void OnLoadResource(WebView view, String url);

    /***
     * 覆盖WebViewClient 中onPageCommitVisible() 实现自定义操作
     * @param view
     * @param url
     */
    void OnPageCommitVisible(WebView view, String url);

    /***
     * 覆盖WebViewClient 中onPageFinished() 实现自定义操作
     * @param view
     * @param url
     */
    void OnPageFinished(WebView view, String url);

    /****
     * 覆盖WebViewClient 中onPageStarted() 实现自定义操作
     * @param view
     * @param url
     * @param favicon
     */
    void OnPageStarted(WebView view, String url, Bitmap favicon);

    /***
     * 覆盖WebViewClient shouldOverrideUrlLoading() 实现自定义操作
     * @param view
     * @param url
     * @return
     */
    boolean ShouldOverrideUrlLoading(WebView view, String url);

    /***
     * 覆盖WebViewClient onReceivedError() 实现自定义异常处理
     * @param view
     * @param request
     * @param error
     */
    void OnReceivedError(WebView view, WebResourceRequest request, WebResourceError error);
}
