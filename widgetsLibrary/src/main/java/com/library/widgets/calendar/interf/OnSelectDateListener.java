package com.library.widgets.calendar.interf;


import com.library.widgets.calendar.model.CalendarDate;

/**
 * Created by ldf on 17/6/2.
 *
 * 日期选择回调
 */

public interface OnSelectDateListener {

    //选择日期回调
    void onSelectDate(CalendarDate date);

    //点击其它月份日期
    void onSelectOtherMonth(int offset);

    //点击的不是今天
    void onClickOtherDay();
}
