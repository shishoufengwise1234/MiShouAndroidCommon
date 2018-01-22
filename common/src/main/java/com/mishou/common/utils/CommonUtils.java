package com.mishou.common.utils;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 *
 * 类库帮助类
 */

public class CommonUtils {


    /**
     * null 值检查
     * @param t 检查数据
     * @param message 异常信息
     */
    public static <T> T checkNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
