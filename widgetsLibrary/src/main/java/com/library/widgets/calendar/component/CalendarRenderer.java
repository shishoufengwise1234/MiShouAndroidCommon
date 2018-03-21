package com.library.widgets.calendar.component;

import android.content.Context;
import android.graphics.Canvas;

import com.library.widgets.calendar.Const;
import com.library.widgets.calendar.Utils;
import com.library.widgets.calendar.interf.IDayRenderer;
import com.library.widgets.calendar.interf.OnSelectDateListener;
import com.library.widgets.calendar.model.CalendarDate;
import com.library.widgets.calendar.view.Calendar;
import com.library.widgets.calendar.view.Day;
import com.library.widgets.calendar.view.Week;
import com.library.widgets.utils.Wlog;


/**
 * Created by ldf on 17/6/26.
 *
 * 日期处理类
 */

public class CalendarRenderer {

    private Week weeks[] = new Week[Const.TOTAL_ROW];    // 行数组，每个元素代表一行
    private Calendar calendar;
    private CalendarAttr attr;
    private IDayRenderer dayRenderer;
    private Context context;

    private OnSelectDateListener onSelectDateListener;    // 单元格点击回调事件
    private CalendarDate seedDate; //种子日期
    private CalendarDate selectedDate; //被选中的日期
    private int selectedRowIndex = 0;

    //点击下个月是否跳转到下个月
    private boolean isSelectMonth = false;

    //不是当前月点击是否回调
    private boolean isNoTargetMonthCallBack = false;

    //初始化日期为今天
    private static final CalendarDate toDayData = new CalendarDate();

    public CalendarRenderer(Calendar calendar, CalendarAttr attr, Context context) {
        this.calendar = calendar;
        this.attr = attr;
        this.context = context;
    }

    /**
     * 使用dayRenderer绘制每一天
     *
     */
    public void draw(Canvas canvas) {
        for (int row = 0; row < Const.TOTAL_ROW; row++) {
            if (weeks[row] != null) {
                for (int col = 0; col < Const.TOTAL_COL; col++) {
                    if (weeks[row].days[col] != null) {
                        dayRenderer.drawDay(canvas, weeks[row].days[col]);
                    }
                }
            }
        }
    }

    /**
     * 点击某一天时刷新这一天的状态
     *
     * @return void
     *
     * update ssf
     */
    public void onClickDate(int col, int row) {
        if (col >= Const.TOTAL_COL || row >= Const.TOTAL_ROW)
            return;
        if (weeks[row] != null) {
            Day targetDay = weeks[row].days[col]; //点击的日期

            if (attr.getCalendarType() == CalendarAttr.CalendayType.MONTH) {  // 点击的是月份

                if (targetDay.getState() == State.CURRENT_MONTH) {  //在当前月内点击

                    CalendarDate targetDate = targetDay.getDate();
                    if (targetDate != null && targetDate.equals(toDayData)){ //如果点击得是今天就选择日期
                        weeks[row].days[col].setState(State.SELECT);

                        selectedDate = weeks[row].days[col].getDate();
                        CalendarViewAdapter.saveDate(selectedDate);
                        //点击回调
                        onSelectDateListener.onSelectDate(selectedDate);
                        seedDate = selectedDate;
                    }else{
                        Wlog.w("weeks[row].days[col].getDate() != toDay");
                        onSelectDateListener.onClickOtherDay();
                    }
                } else if (targetDay.getState() == State.PAST_MONTH) {  //点击上个月日期
                    CalendarDate targetDate = targetDay.getDate();
                    if (targetDate != null && targetDate.equals(toDayData)){  //如果点击得是今天就选择日期
                        selectedDate = weeks[row].days[col].getDate();
                        CalendarViewAdapter.saveDate(selectedDate);
                        //点击回调
//                        onSelectDateListener.onSelectDate(selectedDate);
                        //暂时不切换到下月份
                        if (isSelectMonth()) {
                            onSelectDateListener.onSelectOtherMonth(-1);
                        }
                    }else{
                        Wlog.w("weeks[row].days[col].getDate() != toDay");
                        //是否弹出提示
                        if (isNoTargetMonthCallBack()) {
                            onSelectDateListener.onClickOtherDay();
                        }
                    }
                } else if (targetDay.getState() == State.NEXT_MONTH) {      //点击下个月日期
                    CalendarDate targetDate = targetDay.getDate();
                    if (targetDate != null && targetDate.equals(toDayData)){ //如果点击得是今天就选择日期
                        selectedDate = weeks[row].days[col].getDate();
                        CalendarViewAdapter.saveDate(selectedDate);

                        //点击回调
//                        onSelectDateListener.onSelectDate(selectedDate);
                        //暂时不切换到下月份
                        if (isSelectMonth()) {
                            onSelectDateListener.onSelectOtherMonth(1);
                        }
                    }else{
                        Wlog.w("weeks[row].days[col].getDate() != toDay");
                        //是否弹出提示
                        if (isNoTargetMonthCallBack()) {
                            onSelectDateListener.onClickOtherDay();
                        }
                    }
                }
            } else {
                CalendarDate targetDate = targetDay.getDate();
                if (targetDate != null && targetDate.equals(toDayData)){  //只可以选择今天日期
                    weeks[row].days[col].setState(State.SELECT);
                    selectedDate = weeks[row].days[col].getDate();
                    CalendarViewAdapter.saveDate(selectedDate);
                    onSelectDateListener.onSelectDate(selectedDate);
                    seedDate = selectedDate;
                }else{
                    Wlog.w("weeks[row].days[col].getDate() != toDay");
                    onSelectDateListener.onClickOtherDay();
                }
            }
        }
    }

    /**
     * 刷新指定行的周数据
     *
     * @param rowIndex  参数月所在年
     * @return void
     */
    public void updateWeek(int rowIndex) {
        CalendarDate currentWeekLastDay;
        if (CalendarViewAdapter.weekArrayType == 1) {
            currentWeekLastDay = Utils.getSaturday(seedDate);
        } else {
            currentWeekLastDay = Utils.getSunday(seedDate);
        }
        int day = currentWeekLastDay.day;
        for (int i = Const.TOTAL_COL - 1; i >= 0; i--) {
            CalendarDate date = currentWeekLastDay.modifyDay(day);
            if (weeks[rowIndex] == null) {
                weeks[rowIndex] = new Week(rowIndex);
            }
            if (weeks[rowIndex].days[i] != null) {
                if (date.equals(CalendarViewAdapter.loadDate())) {
                    weeks[rowIndex].days[i].setState(State.SELECT);
//                    weeks[rowIndex].days[i].setState(S、tate.NO_SELECT); //暂时不选中今天
                    weeks[rowIndex].days[i].setDate(date);
                } else {
                    weeks[rowIndex].days[i].setState(State.CURRENT_MONTH);
                    weeks[rowIndex].days[i].setDate(date);
                }
            } else {
                if (date.equals(CalendarViewAdapter.loadDate())) {
                    weeks[rowIndex].days[i] = new Day(State.SELECT, date, rowIndex, i);
//                    weeks[rowIndex].days[i] = new Day(State.NO_SELECT, date, rowIndex, i); //暂时不选中今天
                } else {
                    weeks[rowIndex].days[i] = new Day(State.CURRENT_MONTH, date, rowIndex, i);
                }
            }
            day--;
        }
    }

    /**
     * 填充月数据
     *
     * @return void
     */
    private void instantiateMonth() {
        int lastMonthDays = Utils.getMonthDays(seedDate.year, seedDate.month - 1);    // 上个月的天数
        int currentMonthDays = Utils.getMonthDays(seedDate.year, seedDate.month);    // 当前月的天数
        int firstDayPosition = Utils.getFirstDayWeekPosition(
                seedDate.year,
                seedDate.month,
                CalendarViewAdapter.weekArrayType);

        int day = 0;
        for (int row = 0; row < Const.TOTAL_ROW; row++) {
            day = fillWeek(lastMonthDays, currentMonthDays, firstDayPosition, day, row);
        }
    }

    /**
     * 填充月中周数据
     *
     * @return void
     */
    private int fillWeek(int lastMonthDays,
                         int currentMonthDays,
                         int firstDayWeek,
                         int day,
                         int row) {
        for (int col = 0; col < Const.TOTAL_COL; col++) {
            int position = col + row * Const.TOTAL_COL;// 单元格位置
            if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
                day++;
                fillCurrentMonthDate(day, row, col);
            } else if (position < firstDayWeek) {
                instantiateLastMonth(lastMonthDays, firstDayWeek, row, col, position);
            } else if (position >= firstDayWeek + currentMonthDays) {
                instantiateNextMonth(currentMonthDays, firstDayWeek, row, col, position);
            }
        }
        return day;
    }

    private void fillCurrentMonthDate(int day, int row, int col) {
        CalendarDate date = seedDate.modifyDay(day);
        if (weeks[row] == null) {
            weeks[row] = new Week(row);
        }
        if (weeks[row].days[col] != null) {
            if (date.equals(CalendarViewAdapter.loadDate())) {
                weeks[row].days[col].setDate(date);
                weeks[row].days[col].setState(State.SELECT);
//                weeks[row].days[col].setState(State.NO_SELECT);  //暂时不选中今天
            } else {
                weeks[row].days[col].setDate(date);
                weeks[row].days[col].setState(State.CURRENT_MONTH);
            }
        } else {
            if (date.equals(CalendarViewAdapter.loadDate())) {
                weeks[row].days[col] = new Day(State.SELECT, date, row, col);
//                weeks[row].days[col] = new Day(State.NO_SELECT, date, row, col); //暂时不选中今天
            } else {
                weeks[row].days[col] = new Day(State.CURRENT_MONTH, date, row, col);
            }
        }
        if (date.equals(seedDate)) {
            selectedRowIndex = row;
        }
    }

    private void instantiateNextMonth(int currentMonthDays,
                                      int firstDayWeek,
                                      int row,
                                      int col,
                                      int position) {
        CalendarDate date = new CalendarDate(
                seedDate.year,
                seedDate.month + 1,
                position - firstDayWeek - currentMonthDays + 1);
        if (weeks[row] == null) {
            weeks[row] = new Week(row);
        }
        if (weeks[row].days[col] != null) {
            weeks[row].days[col].setDate(date);
            weeks[row].days[col].setState(State.NEXT_MONTH);
        } else {
            weeks[row].days[col] = new Day(State.NEXT_MONTH, date, row, col);
        }
        //17/6/27  当下一个月的天数大于七时，说明该月有六周
//        if(position - firstDayWeek - currentMonthDays + 1 >= 7) { //当下一个月的天数大于七时，说明该月有六周
//        }
    }

    private void instantiateLastMonth(int lastMonthDays, int firstDayWeek, int row, int col, int position) {
        CalendarDate date = new CalendarDate(
                seedDate.year,
                seedDate.month - 1,
                lastMonthDays - (firstDayWeek - position - 1));
        if (weeks[row] == null) {
            weeks[row] = new Week(row);
        }
        if (weeks[row].days[col] != null) {
            weeks[row].days[col].setDate(date);
            weeks[row].days[col].setState(State.PAST_MONTH);
        } else {
            weeks[row].days[col] = new Day(State.PAST_MONTH, date, row, col);
        }
    }

    /**
     * 根据种子日期孵化出本日历牌的数据
     *
     * @return void
     */
    public void showDate(CalendarDate seedDate) {
        if (seedDate != null) {
            this.seedDate = seedDate;
        } else {
            this.seedDate = new CalendarDate();
        }
        update();
    }

    public void update() {
        instantiateMonth();
        calendar.invalidate();
    }

    public CalendarDate getSeedDate() {
        return this.seedDate;
    }

    public void cancelSelectState() {
        for (int i = 0; i < Const.TOTAL_ROW; i++) {
            if (weeks[i] != null) {
                for (int j = 0; j < Const.TOTAL_COL; j++) {
                    if (weeks[i].days[j].getState() == State.SELECT) {
                        weeks[i].days[j].setState(State.CURRENT_MONTH);
                        resetSelectedRowIndex();
                        break;
                    }
                }
            }
        }
    }

    public void resetSelectedRowIndex() {
        selectedRowIndex = 0;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public CalendarAttr getAttr() {
        return attr;
    }

    public void setAttr(CalendarAttr attr) {
        this.attr = attr;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setOnSelectDateListener(OnSelectDateListener onSelectDateListener) {
        this.onSelectDateListener = onSelectDateListener;
    }

    public void setDayRenderer(IDayRenderer dayRenderer) {
        this.dayRenderer = dayRenderer;
    }

    public boolean isSelectMonth() {
        return isSelectMonth;
    }

    public void setSelectMonth(boolean selectMonth) {
        isSelectMonth = selectMonth;
    }

    public void setNoTargetMonthCallBack(boolean noTargetMonthCallBack) {
        isNoTargetMonthCallBack = noTargetMonthCallBack;
    }

    //点击不是指定月份时 是否回调子类
    public boolean isNoTargetMonthCallBack() {
        return isNoTargetMonthCallBack;
    }


}
