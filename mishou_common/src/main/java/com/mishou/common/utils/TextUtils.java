package com.mishou.common.utils;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * Created by ${shishoufeng} on 2016/12/1 0001.
 * email:shishoufeng1227@126.com
 *
 * 采用SpannableString 处理字体 大小，颜色，不同问题
 *
 */
public class TextUtils {


    /***
     * 由大至小
     *
     * 处理 不同文字大小 颜色不一致文字
     * @param text 待处理文字
     * @param proportion 变大 文字 大小
     * @param bigColor 颜色
     * @param start 开始位置
     * @param end 结束位置
     * @param smallSize 小字体颜色
     * @param smallColor 颜色
     * @return
     */
    public static SpannableString handleHintText(@NonNull CharSequence text, int proportion, String bigColor, int start, int end, int smallSize, String smallColor){
        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new AbsoluteSizeSpan(proportion,true),start,end, Spanned.SPAN_INTERMEDIATE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(bigColor)),start,end, Spanned.SPAN_INTERMEDIATE);

        if (smallSize == 0 || smallColor == null || StringUtils.isequals("",smallColor)){
            return spannableString;
        }else{
            spannableString.setSpan(new AbsoluteSizeSpan(smallSize,true),end,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(smallColor)),end,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
    /***
     * 由大至小
     *
     * 处理 不同文字大小 颜色不一致文字
     * @param text 待处理文字
     * @param proportion 变大 文字 大小
     * @param bigColor 颜色
     * @param start 开始位置
     * @param end 结束位置
     * @param smallSize 小字体颜色
     * @param smallColor 颜色
     * @return
     */
    public static SpannableString handleHintText(@NonNull CharSequence text, int proportion, @ColorInt int bigColor, int start, int end, int smallSize, @ColorInt int smallColor){
        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new AbsoluteSizeSpan(proportion,true),start,end, Spanned.SPAN_INTERMEDIATE);
        spannableString.setSpan(new ForegroundColorSpan(bigColor),start,end, Spanned.SPAN_INTERMEDIATE);

        if (smallSize == 0 || smallColor == -1){
            return spannableString;
        }else{
            spannableString.setSpan(new AbsoluteSizeSpan(smallSize,true),end,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(smallColor),end,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
    /***
     * 由小直大
     *
     * 处理 不同文字大小 颜色不一致文字
     * @param text 待处理文字
     * @param proportion 变大 文字 大小
     * @param bigColor 颜色
     * @param start 开始位置
     * @param end 结束位置
     * @param smallSize 小字体颜色
     * @param smallColor 颜色
     * @return
     */
    public static SpannableString handleHintTextSmall(@NonNull CharSequence text, int smallSize, String smallColor, int start, int end, int proportion, String bigColor){
        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new AbsoluteSizeSpan(smallSize,true),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(smallColor)),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (smallSize == 0 || smallColor == null || StringUtils.isequals("",smallColor)){
            return spannableString;
        }else{
            spannableString.setSpan(new AbsoluteSizeSpan(proportion,true),end,text.length(), Spanned.SPAN_INTERMEDIATE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(bigColor)),end,text.length(), Spanned.SPAN_INTERMEDIATE);
        }
        return spannableString;
    }

    /**
     * 获取注册时 提示信息
     * @param text 提示文本
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public static SpannableString getRegisterHint(@NonNull CharSequence text, int start, int end){

        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new AbsoluteSizeSpan(16,true),start,end, Spanned.SPAN_INTERMEDIATE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),start,end, Spanned.SPAN_INTERMEDIATE);

        spannableString.setSpan(new AbsoluteSizeSpan(12,true),end,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),end,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
