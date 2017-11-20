package com.mishou.demo.webview;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Button;

import com.mishou.common.base.BaseSupportFragment;
import com.mishou.common.demo.R;
import com.mishou.common.widgets.webview.BaseWebView;
import com.mishou.common.widgets.webview.callback.OnWebViewChromeListener;
import com.mishou.common.widgets.webview.callback.OnWebViewClientListener;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/20.
 * email:shishoufeng1227@126.com
 */

public class NtiveFragment extends BaseSupportFragment {


    @BindView(R.id.wb_native)
    BaseWebView baseWebView;

    @BindView(R.id.btn_call_js)
    Button btnCallJs;


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_native_webview;
    }

    @Override
    protected void setOnListener(View view) {

        btnCallJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    baseWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            baseWebView.loadUrl("javascript:testAlert()");

                        }
                    });
                } else {
                    baseWebView.evaluateJavascript("javascript:testAlert()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                            Logger.d("onReceiveValue > " + s);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onLogic(Bundle savedInstanceState) {


        Test mTest = new Test();
        baseWebView.addMethod(mTest, "mTest");

        baseWebView.setUrl("file:///android_asset/test_html.html");


        baseWebView.setOnWebViewClientListener(new OnWebViewClientListener() {
            @Override
            public void OnLoadResource(WebView view, String url) {
                Logger.d("OnLoadResource >"+url);
            }

            @Override
            public void OnPageCommitVisible(WebView view, String url) {
                Logger.d("OnPageCommitVisible >"+url);
            }

            @Override
            public void OnPageFinished(WebView view, String url) {
                Logger.d("OnPageFinished >"+url);
            }

            @Override
            public void OnPageStarted(WebView view, String url, Bitmap favicon) {
                Logger.d("OnPageStarted >"+url);
            }

            @Override
            public boolean ShouldOverrideUrlLoading(WebView view, String url) {
                Logger.d("ShouldOverrideUrlLoading >"+url);
                return false;
            }

            @Override
            public void OnReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                Logger.d("OnReceivedError >"+error);
            }
        });

        baseWebView.setOnWebViewChromeListener(new OnWebViewChromeListener() {
            @Override
            public boolean OnJsAlert(WebView view, String url, String message, JsResult result) {
                Logger.d("OnJsAlert >"+message);
                return false;
            }

            @Override
            public boolean OnJsConfirm(WebView view, String url, String message, JsResult result) {
                Logger.d("OnJsConfirm >"+message);
                return false;
            }

            @Override
            public boolean OnJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                Logger.d("OnJsBeforeUnload >"+message);
                return false;
            }

            @Override
            public boolean OnJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Logger.d("OnJsPrompt >"+message);
                return false;
            }
        });

    }

    public class Test{

        @JavascriptInterface
        public void TestLog(String msg) {

            Logger.d(msg);
        }
    }


}
