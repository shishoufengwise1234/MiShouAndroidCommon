package com.mishou.common.utils;

import android.content.Context;
import android.os.PowerManager;
import android.support.annotation.NonNull;

/**
 * Created by ${shishoufeng} on 2017/7/3 0003.
 * email:shishoufeng1227@126.com
 */

public class PowerUtils {


    /**
     * 开启电池管理器 防止屏幕休眠
     * @param context 上下文对象
     * @param TAG 标识tag
     * @return 屏幕锁对象
     */
    @SuppressWarnings("unchecked")
    public static PowerManager.WakeLock acquireLock(@NonNull Context context, @NonNull String TAG){
        PowerManager.WakeLock wakeLock;
        if (context == null) return null;
        if (TAG == null) return null;

        //启动电池管理器 防止屏幕休眠
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK| PowerManager.ON_AFTER_RELEASE,TAG);
        wakeLock.acquire();
        return wakeLock;
    }

    /**
     * 回收资源
     * @param lock 屏幕锁对象
     */
    @SuppressWarnings("unchecked")
    public static void releaseLock(PowerManager.WakeLock lock){
        if (lock == null) return;
        lock.release();
    }
}
