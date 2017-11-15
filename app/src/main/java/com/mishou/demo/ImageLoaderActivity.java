package com.mishou.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mishou.common.demo.R;
import com.mishou.common.image.ImageLoader;
import com.mishou.common.base.BaseAppcompatActivity;
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
    protected int getLayoutView() {
        return R.layout.activity_imageloader;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        ImageLoader.getInstance()
                .loadImage(ImageLoaderActivity.this,"http://img5.imgtn.bdimg.com/it/u=49366202,632101467&fm=27&gp=0.jpg",imgPic);

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
