package com.mishou.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mishou.common.demo.R;
import com.mishou.common.image.ImageLoader;
import com.mishou.common.ui.base.BaseAppcompatActivity;
import com.mishou.common.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/8.
 * email:shishoufeng1227@126.com
 */

public class ImageLoaderActivity extends BaseAppcompatActivity {


    @BindView(R.id.img_pic)
    ImageView imgPic;
    @BindView(R.id.img_pic_2)
    ImageView imgPic2;

    @BindView(R.id.btn_load)
    Button btnLoad;

    @Override
    protected void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_imageloader);
    }

    @Override
    protected void initView() {

        ImageLoader.getInstance().loadImage(ImageLoaderActivity.this,"",imgPic);


    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initData() {

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ToastUtils.showMessage(ImageLoaderActivity.this,"load");
            }
        });

    }

}
