package com.mishou.common.utils;

import java.math.BigDecimal;

/**
 * Created by ${shishoufeng} on 17/4/18.
 * email:shishoufeng1227@126.com
 * <p>
 * 精密计算帮助类
 */

public class DecimalUtils {

    //默认除法运算精度
    private static final int DEFAULT_DIV_SCALE = 10;

    //保留几位小数
    public static final int SCALE_1 = 1;
    public static final int SCALE_2 = 2;
    public static final int SCALE_3 = 3;

    /**
     * 两数值之和
     *
     * @param value1
     * @param value2
     * @return
     */
    public static double add(String value1, String value2) {

        if (StringUtils.isEmpty(value1) || StringUtils.isEmpty(value2))
            return 0.0;

        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);

        return b1.add(b2).doubleValue();
    }


    /**
     * 两数值相减 求差
     *
     * @param value1 减数
     * @param value2 被减数
     * @return
     */
    public static double sub(String value1, String value2) {

        if (StringUtils.isEmpty(value1) || StringUtils.isEmpty(value2))
            return 0.0;

        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);

        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两数值相乘 求积
     *
     * @param value1
     * @param value2
     * @return
     */
    public static double mul(String value1, String value2) {

        if (StringUtils.isEmpty(value1) || StringUtils.isEmpty(value2))
            return 0.0;

        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);

        return b1.multiply(b2).doubleValue();
    }

    /**
     * 两数值相乘 求积 保留两位小数 四舍五入 并去除多余0 转换成字符串
     *
     * @param value1
     * @param value2
     * @return
     */
    public static String mulString2(String value1, String value2) {

        if (StringUtils.isEmpty(value1) || StringUtils.isEmpty(value2))
            return "";

        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);

        double value = b1.multiply(b2).doubleValue();

        return  round2(String.valueOf(value));
    }


    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_UP
     *
     * @param v1
     * @param v2
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_UP
     *
     * @param v1
     * @param v2
     * @param scale 表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2, int scale) {
        return divide(v1, v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     *
     * @param v1
     * @param v2
     * @param scale      表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, round_mode).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_UP
     *
     * @param v1
     * @param v2
     * @return 两个参数的商，以字符串格式返回
     */
    public static String divide(String v1, String v2) {
        return divide(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_UP
     *
     * @param v1
     * @param v2
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商，以字符串格式返回
     */
    public static String divide(String v1, String v2, int scale) {
        return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     *
     * @param v1
     * @param v2
     * @param scale      表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商，以字符串格式返回
     */
    public static String divide(String v1, String v2, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, round_mode).toString();
    }

    /**
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_UP
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        return round(v, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v          需要四舍五入的数字
     * @param scale      小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.setScale(scale, round_mode).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_UP
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale) {
        return round(v, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留1位小数
     *
     * @param v
     * @return
     */
    public static String round1(String v) {
        return round(v, SCALE_1, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留2位小数
     *
     * @param v
     * @return
     */
    public static String round2(String v) {
        return round(v, SCALE_2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留3位小数
     *
     * @param v
     * @return
     */
    public static String round3(String v) {
        return round(v, SCALE_3, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 提供精确的小数位四舍五入处理
     * stripTrailingZeros().toPlainString() 去除多余0
     *
     * @param v          需要四舍五入的数字
     * @param scale      小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale, int round_mode) {
        if (StringUtils.isEmpty(v))
            return "";

        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);

        return b.setScale(scale, round_mode).stripTrailingZeros().toPlainString();
    }


    /**
     * 保留2位小数
     * @param bigDecimal
     * @return
     */
    public static String round2(BigDecimal bigDecimal) {
        return bigDecimal.setScale(SCALE_2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

}
