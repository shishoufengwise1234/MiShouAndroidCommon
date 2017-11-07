package com.mishou.common.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by ${shishoufeng} on 2016/8/31 0031.
 * email:shishoufeng1227@126.com
 */
public class CustomGridView extends GridView {


    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public CustomGridView(Context context) {
        this(context, null);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

}
