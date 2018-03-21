package com.library.widgets.calendar.component;

/**
 * Created by ldf on 17/6/27.
 *
 * 日期状态
 */

public enum State {
    CURRENT_MONTH,//当前月
    PAST_MONTH,//上个月
    NEXT_MONTH,//点击的日期是下个月
    SELECT, //选择
    NO_SELECT  //不选择
}
