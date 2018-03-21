package com.mishou.common.widgets.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mishou.common.widgets.webview.callback.OnWebViewChromeListener;
import com.mishou.common.widgets.webview.callback.OnWebViewClientListener;


/**
 * Created by Administrator on 2015/12/22.
 * <p/>
 * 项目自定义加载WebView
 */
public class BaseWebView extends WebView {

    private static final String TAG = "BaseWebView";

    private Context context;
    /**
     * WebView 进度条
     */
    private ProgressBar mProgress;

    /**
     * 是否启动接口回调 另外处理 WebViewChrome
     */
    private boolean isWebViewChromeListener = false;

    /***
     * 是否启动接口回调 另外处理 WebViewClient
     */
    private boolean isWebViewClientListener = false;

    private OnWebViewChromeListener onWebViewChromeListener;

    private OnWebViewClientListener onWebViewClientListener;

    public BaseWebView(Context context) {
        this(context, null);
        this.context = context;
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /***
     * 初始化
     */
    private void init() {
        WebSettings settings = getSettings();
        /***
         * 设置支持JavaScript 方法
         */
        settings.setJavaScriptEnabled(true);
        /***
         * 不允许进行缩放
         */
        settings.setBuiltInZoomControls(false);
        /**
         * 取消右下方缩放按钮
         */
        settings.setDisplayZoomControls(false);
        /**
         * 设置加载进来的页面自适应手机屏幕
         */
        settings.setUseWideViewPort(true);
        /**
         * WebView加载的页面的模式
         */
        settings.setLoadWithOverviewMode(true);
        /***
         * 取消滚动条
         */
        setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        /***
         * 开启APP缓存
         */
        settings.setAppCacheEnabled(true);
        /***
         * 设置WebView 缓存策略
         */
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        /***
         * 允许访问文件
         */
        settings.setAllowFileAccess(true);
        /***
         * 启用数据库
         */
        settings.setDatabaseEnabled(true);
        /**
         * 不允许保存密码
         */
        settings.setSavePassword(false);

        /**
         * 加快HTML网页装载完成的速度
         */
        if(Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        this.setWebChromeClient(new BaseWebViewChrome());
        this.setWebViewClient(new BaseWebViewClient());
    }

    /**
     * 设置 OnWebViewChromeListener 进行接口回调
     *
     * @param onWebViewChromeListener
     * @return
     */
    public BaseWebView setOnWebViewChromeListener(OnWebViewChromeListener onWebViewChromeListener) {
        this.onWebViewChromeListener = onWebViewChromeListener;
        return this;
    }

    /***
     * 设置 OnWebViewClientListener 进行接口回调处理
     *
     * @param onWebViewClientListener
     * @return
     */
    public BaseWebView setOnWebViewClientListener(OnWebViewClientListener onWebViewClientListener) {
        this.onWebViewClientListener = onWebViewClientListener;
        return this;
    }

    /**
     * 设置加载的URL
     *
     * @param url
     */
    public void setUrl(String url) {
        this.loadUrl(url);
    }

    /***
     * 初始化进度条
     *
     * @param progressBar
     */
    public void setProgressBar(ProgressBar progressBar) {
        this.mProgress = progressBar;
    }

    /***
     * 设置是否启动接口回调 WebViewClient
     *
     * @param
     */
    public BaseWebView setIsWebViewClientListener(boolean isWebViewClientListener) {
        this.isWebViewClientListener = isWebViewClientListener;
        return this;
    }

    /***
     * 设置是否启动接口回调 WebViewChrome
     *
     * @param
     */
    public BaseWebView setIsWebViewChromeListener(boolean isWebViewChromeListener) {
        this.isWebViewChromeListener = isWebViewChromeListener;
        return this;
    }

    /***
     * 单个添加
     * 向JavaScript注入Android方法
     *
     * @param object     具体方法
     * @param methodName 对象名
     */
    @SuppressLint("JavascriptInterface")
    public void addMethod(Object object, String methodName) {
        this.addJavascriptInterface(object, methodName);
    }

    /***
     * 集体添加
     * 向JavaScript注入Android方法
     *
     * @param objects []具体方法
     * @param methods []对象名
     */
    @SuppressLint("JavascriptInterface")
    public void addMethod(Object[] objects, String[] methods) {
        String error_message = "";
        try {
            if (objects != null && objects.length != 0 && methods != null && methods.length != 0) {
                if (objects.length == methods.length) {
                    /**
                     * 添加对象
                     */
                    for (int i = 0; i < objects.length; i++) {
                        this.addJavascriptInterface(objects[i], methods[i]);
                    }
                } else {
                    error_message = "不符合条件!";
                }
            } else {
                error_message = "添加失败!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (BuildConfig.DEBUG)
//            Log.d(TAG, error_message);

    }
    /**
     * 判断是返回到上一级页面还是直接直接关闭界面
     */
    public void goBackOrFinish(Activity activity){
        if(canGoBack()){
            goBack();
        }else{
            activity.finish();
        }
    }

    /****
     * 处理网页中的一些对话框信息（提示对话框，带选择的对话框，带输入的对话框）
     */
    private class BaseWebViewChrome extends WebChromeClient {

        /****
         * 此处覆盖的是javascript中的alert方法。当网页需要弹出alert窗口时，会执行onJsAlert中的方法
         * 网页自身的alert方法不会被调用。
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            boolean isJsAlert = false;
            if (isWebViewChromeListener) {
                /**
                 * 自定义处理
                 */
                if (onWebViewChromeListener != null)
                    isJsAlert = onWebViewChromeListener.OnJsAlert(view, url, message, result);
            } else {
                /***
                 * 默认处理
                 */
                Log.i(TAG, "url ==" + url + "message ==" + message);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                isJsAlert = super.onJsAlert(view,url,message,result);
            }
            /***
             * 此处代码非常重要，若没有，android就不能与js继续进行交互了， 且第一次交互后，webview不再展示出来。
             * result：A JsResult to confirm that the user hit enter.
             */
            result.confirm();
            return isJsAlert;
        }

        /***
         * 此处覆盖的是javascript中的confirm方法。当网页需要弹出confirm窗口时，会执行onJsConfirm中的方法
         * 网页自身的confirm方法不会被调用。
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            boolean isJsConfirm = false;
            if (isWebViewChromeListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewChromeListener != null)
                    isJsConfirm = onWebViewChromeListener.OnJsConfirm(view, url, message, result);
            } else {
                /**
                 * 默认处理方式
                 */
                isJsConfirm = super.onJsConfirm(view, url, message, result);
            }
            result.confirm();
            return isJsConfirm;
        }

        /**
         * 如果页面被强制关闭,弹窗提示：是否确定离开？ 点击确定 保存数据离开，点击取消，停留在当前页面
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            boolean isJsBeforeUnload = false;
            if (isWebViewChromeListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewChromeListener != null)
                    isJsBeforeUnload = onWebViewChromeListener.OnJsBeforeUnload(view, url, message, result);
            } else {
                /**
                 * 默认处理方式
                 */
                isJsBeforeUnload = super.onJsBeforeUnload(view, url, message, result);
            }
            return isJsBeforeUnload;
        }

        /***
         * 此处覆盖的是javascript中的confirm方法。当网页需要弹出confirm窗口时，会执行onJsConfirm中的方法
         * 网页自身的confirm方法不会被调用。
         *
         * @param view
         * @param url
         * @param message
         * @param defaultValue
         * @param result
         * @return
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            boolean isJsPrompt = false;
            if (isWebViewChromeListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewChromeListener != null)
                    isJsPrompt = onWebViewChromeListener.OnJsPrompt(view, url, message, defaultValue, result);
            } else {
                /**
                 * 默认处理方式
                 */
                isJsPrompt = super.onJsPrompt(view, url, message, defaultValue, result);
            }
            result.confirm();
            return isJsPrompt;
        }

        /***
         * 设置WebView title
         *
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            context.setTitle(title);
        }

        /***
         * 设置网页加载的进度条
         *
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                if (mProgress != null)
                    mProgress.setVisibility(View.GONE);
            } else {
                if (mProgress != null) {
                    mProgress.setVisibility(View.VISIBLE);
                    mProgress.setProgress(newProgress);
                }
            }
        }
    }

    /***
     * 通过这个设置来执行加载WebView网页时所要执行的一些方法
     */
    private class BaseWebViewClient extends WebViewClient {

        /***
         * 加载页面资源
         *
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (isWebViewClientListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewClientListener != null)
                    onWebViewClientListener.OnLoadResource(view, url);
            } else {
                /**
                 * 默认处理
                 */

            }
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            if (isWebViewClientListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewClientListener != null)
                    onWebViewClientListener.OnPageCommitVisible(view, url);
            } else {
                /**
                 * 默认处理
                 */

            }
        }

        /****
         * 页面加载完成
         *
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (isWebViewClientListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewClientListener != null)
                    onWebViewClientListener.OnPageFinished(view, url);
            } else {
                /**
                 * 默认处理
                 */
                if(!getSettings().getLoadsImagesAutomatically()) {
                    getSettings().setLoadsImagesAutomatically(true);
                }
            }
        }

        /***
         * 开始加载页面
         *
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (isWebViewClientListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewClientListener != null)
                    onWebViewClientListener.OnPageStarted(view, url, favicon);
            } else {
                /**
                 * 默认处理
                 */

            }
        }

        /***
         * 点击页面的某条链接进行跳转的话，会启动系统的默认浏览器进行加载，调出了我们本身的应用
         * 因此，要在shouldOverrideUrlLoading方法中
         *
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean isshouldOverrideUrlLoading = false;
            if (isWebViewClientListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewClientListener != null)
                    isshouldOverrideUrlLoading = onWebViewClientListener.ShouldOverrideUrlLoading(view, url);
            } else {
                /**
                 * 默认处理
                 */
//                isshouldOverrideUrlLoading = super.shouldOverrideUrlLoading(view,url);
                Log.i(TAG, "url ==" + url);
                view.loadUrl(url);
            }
            return isshouldOverrideUrlLoading;
        }

        /***
         * WebView 加载失败时调用 错误提示
         *
         * @param view
         * @param request
         * @param error
         */
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (isWebViewClientListener) {
                /**
                 * 接口回调处理
                 */
                if (onWebViewClientListener != null)
                    onWebViewClientListener.OnReceivedError(view, request, error);
            } else {
                /**
                 * 默认处理
                 */
//                loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        //回收资源
        this.destroy();
    }

    //    private int mCurrContentHeight = 0;
//    @Override
//    protected void onScrollChanged(int newX, int newY, int oldX, int oldY) {
//        super.onScrollChanged(newX, newY, oldX, oldY);
//        if (newY != oldY) {
//            float contentHeight = getContentHeight() * getScale();
//            // 当前内容高度下从未触发过, 浏览器存在滚动条且滑动到将抵底部位置
//            if (mCurrContentHeight != contentHeight && newY > 0 && contentHeight <= newY + getHeight() + mThreshold) {
//
//                mCurrContentHeight = contentHeight;
//            }
//    }
}
