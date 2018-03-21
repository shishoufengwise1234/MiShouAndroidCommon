package com.mishou.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xingzhezuomeng on 16/8/8.
 */
public class TimeUtils {

    //默认时间格式
    private static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat DEFAULT_SDF_Minute = new SimpleDateFormat("HH:mm");


    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /** 将时间字符串转为时间戳
    * <p>格式为yyyy-MM-dd HH:mm:ss
    */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }


    /** 将时间字符串转为时间戳
     * <p>格式为HH:mm
     */
    public static String string2Minute(String time) {
        return DEFAULT_SDF_Minute.format(new Date(string2Milliseconds(time)));
    }
    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static Date string2Date(String formatDate) {
        return string2Date(formatDate, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义
     */
    public static Date string2Date(String formatDate, SimpleDateFormat format) {
        return new Date(string2Milliseconds(formatDate, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义
     */
    public static String date2String(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    /**
     * 将Date类型转为时间戳
     */
    public static long date2Milliseconds(Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }



    /**
     * 获取当前时间
     * <p>单位（毫秒）
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getCurTimeString() {
        return milliseconds2String(getCurTimeMills());
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return milliseconds2String(getCurTimeMills(), format);
    }

    /**
     * 获取当前时间
     * <p>Date类型
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

//    /** 获取与当前时间的差（单位：unit）
//            * <pre>
//    * UNIT_MSEC:毫秒
//    * UNIT_SEC :秒
//    * UNIT_MIN :分
//    * UNIT_HOUR:小时
//    * UNIT_DAY :天
//    * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss
//    */
//    public static long getIntervalByNow(String time, int unit) {
//        return getIntervalByNow(time, unit, DEFAULT_SDF);
//    }

//    /**
//     * 获取与当前时间的差（单位：unit）
//     * <pre>
//     * UNIT_MSEC:毫秒
//     * UNIT_SEC :秒
//     * UNIT_MIN :分
//     * UNIT_HOUR:小时
//     * UNIT_DAY :天
//     * <p>time1和time2格式都为format
//     */
//    public static long getIntervalByNow(String time, int unit, SimpleDateFormat format) {
//        return getIntervalTime(getCurTimeString(), time, unit, format);
//    }
//
//    /**
//     * 获取与当前时间的差（单位：unit）
//     * <pre>
//     * UNIT_MSEC:毫秒
//     * UNIT_SEC :秒
//     * UNIT_MIN :分
//     * UNIT_HOUR:小时
//     * UNIT_DAY :天
//     * <p>time1和time2格式都为format
//     */
//    public static long getIntervalByNow(Date time, int unit) {
//        return getIntervalTime(getCurTimeDate(), time, unit);
//    }

    /**
     * 判断闰年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 计算两个时间 差
     * @param createTime
     * @param endTime
     * @return
     */
    public static String getStatusDate(String createTime, String endTime){

        if (StringUtils.isEmpty(createTime) ||StringUtils.isEmpty(endTime)){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        Date now = null;
        Date date = null;
        try {
            now = DEFAULT_SDF.parse(endTime);
            date = DEFAULT_SDF.parse(createTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time = now.getTime() - date.getTime();

        long day = time / (24 * 60 * 60 * 1000);

        long hour = (time / (60 * 60 * 1000) - day * 24);

        long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);

        long s = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (day > 0) {
            stringBuilder.append(day + "天");
        }
        if (hour > 0) {
            stringBuilder.append(hour + "小时");
        }
        if (min > 0) {
            stringBuilder.append(min + "分钟");
        }

        return stringBuilder.toString();

    }

    /**
     *
     * 比较两个时间前后
     *
     * 判断 endTime 是否大于 startTime
     *
     * @param endTime 后者时间
     * @param startTime 前者时间
     * @return true 前者大于后者  false 前者小于后者
     */
    public static boolean isBeforeTime(String endTime,String startTime){

        if (StringUtils.isEmpty(endTime) || StringUtils.isEmpty(startTime))
            return false;

        try {
            Date dateEnd = DEFAULT_SDF.parse(endTime);
            Date dateStart = DEFAULT_SDF.parse(startTime);

            return dateEnd.before(dateStart);

        }catch (ParseException e){
            return false;
        }catch (Exception e){
            return false;
        }
    }
    /**
     *
     * 比较两个时间前后
     *
     * 判断 endTime 是否大于 startTime
     *
     * @param endTime 后者时间
     * @param startTime 前者时间
     * @return true 前者大于后者  false 前者小于后者
     */
    public static boolean isBeforeTime(Date endTime,Date startTime){

        if (endTime == null || startTime == null)
            return false;

        try {
            return endTime.before(startTime);

        }catch (Exception e){
            return false;
        }
    }


    /**
     * 根据标准时间格式 解析出年和月
     * @param date Date 时间
     * @return yyyy-MM
     */
    public static String parseYearAndMonth(Date date){
        if (date == null)
            return "";

        return parseYearAndMonth(date2String(date));
    }

    /**
     *
     * 根据标准时间格式 解析出年和月
     * @param time 时间 SimpleDateFormat("yyyy-MM-dd HH:mm:ss") 格式
     * @return yyyy-MM
     */
    public static String parseYearAndMonth(String time){
        try {
            if (StringUtils.isEmpty(time)) {
                return "";
            }
            if (time.length() > 7) {
                return time.substring(0, 7);
            }
            return "";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }




}
