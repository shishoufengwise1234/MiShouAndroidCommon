package com.mishou.demo.webview;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;

import com.mishou.common.base.BaseSupportFragment;
import com.mishou.common.demo.R;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.ValueCallback;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/20.
 * email:shishoufeng1227@126.com
 */

public class TbsFragment extends BaseSupportFragment {



    @BindView(R.id.wb_tbs)
    TbsWebView wbTbs;

    @BindView(R.id.btn_call_js)
    Button btnCallJs;


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_tbs_webview;
    }

    @Override
    protected void setOnListener(View view) {


        wbTbs.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient());
        wbTbs.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient());


        btnCallJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    wbTbs.post(new Runnable() {
                        @Override
                        public void run() {
                            wbTbs.loadUrl("javascript:testAlert()");

                        }
                    });
                } else {
                    wbTbs.evaluateJavascript("javascript:testAlert()", new ValueCallback<String>() {
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
        wbTbs.addMethod(mTest, "mTest");

        wbTbs.loadUrl("file:///android_asset/test_tbs.html");


    }

    public class Test{

        @JavascriptInterface
        public void TestLog(String msg) {

            Logger.d(msg);
        }
    }
}
