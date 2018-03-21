package com.library.widgets.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.library.widgets.calendar.interf.IDayRenderer;
import com.library.widgets.utils.Wlog;


/**
 * Created by ldf on 16/10/19.
 *
 * 自定义日期类
 */

public abstract class DayView extends RelativeLayout implements IDayRenderer {

    protected Day day;
    protected Context context;
    protected int layoutResource;

    /**
     * 构造器 传入资源文件创建DayView
     *
     * @param layoutResource 资源文件
     * @param context 上下文
     */
    public DayView(Context context, int layoutResource) {
        super(context);
        setupLayoutResource(layoutResource);
        this.context = context;
        this.layoutResource = layoutResource;
    }

    /**
     * 为自定义的DayView设置资源文件
     *
     * @param layoutResource 资源文件
     * @return CalendarDate 修改后的日期
     */
    private void setupLayoutResource(int layoutResource) {
        View inflated = LayoutInflater.from(getContext()).inflate(layoutResource, this);

        //fix API18版本以下崩溃 NullPointerException
        //======================================
        inflated.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        //======================================

        inflated.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        inflated.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void refreshContent() {
        Wlog.d("DayView -- refreshContent()");
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    /**
     * 绘制 日期数据
     * @param day 数据
     */
    @Override
    public void drawDay(Canvas canvas, Day day) {
        Wlog.d("DayView -- drawDay()");

        this.day = day;
        refreshContent();
        int saveId = canvas.save();
        canvas.translate(getTranslateX(canvas, day),
                day.getPosRow() * getMeasuredHeight());

        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    // 2018/1/3 计算 平移距离 有待优化 1、要考虑 小屏手机 2、大屏手机  3、较高分辨率情况下 不同情况  已经适配完成
    private int getTranslateX(Canvas canvas, Day day) {
        int dx;
        int canvasWidth = canvas.getWidth() / 7;
        int viewWidth = getMeasuredWidth();
        int moveX = (canvasWidth - viewWidth);
        dx = day.getPosCol() * canvasWidth + moveX;

        Wlog.d("getTranslateX  ---  canvasWidth = "+canvasWidth+" viewWidth = "+viewWidth+" moveX = "+moveX+" dx = "+dx);
        return dx;
        //原始代码
//        int dx;
//        int canvasWidth = canvas.getWidth() / 7;
//        int viewWidth = getMeasuredWidth();
//        int moveX = (canvasWidth - viewWidth) / 2;
//        dx = day.getPosCol() * canvasWidth + moveX;
//
//        Wlog.d("getTranslateX  ---  canvasWidth = "+canvasWidth+" viewWidth = "+viewWidth+" moveX = "+moveX+" dx = "+dx);
//        return dx;
    }
}