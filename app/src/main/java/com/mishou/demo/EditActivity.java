package com.mishou.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mishou.common.demo.R;
import com.mishou.common.base.BaseAppcompatActivity;
import com.mishou.common.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by ${shishoufeng} on 17/11/12.
 * email:shishoufeng1227@126.com
 */

public class EditActivity extends BaseAppcompatActivity {


    @BindView(R.id.edit_value)
    EditText editValue;
    @BindView(R.id.btn_check)
    Button btnCheck;

    @Override
    protected void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setOnListener() {


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = editValue.getText().toString().trim();

                ToastUtils.showMessage(EditActivity.this,value);
            }
        });

    }

    @Override
    protected void initData() {

    }

}
