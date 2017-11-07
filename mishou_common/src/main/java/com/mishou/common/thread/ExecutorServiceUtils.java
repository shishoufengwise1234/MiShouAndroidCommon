package com.mishou.common.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ${shishoufeng} on 2017/3/16 0016.
 * email:shishoufeng1227@126.com
 * <p>
 * 线程池工具类
 */

public class ExecutorServiceUtils {

    private static ExecutorService executorService;

    private static final int DEFAULT_MAX_THREAD = 4;


    private static void initExecutorService() {
        /**
         *创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务
         * 后来线程进行排队执行
         */
//        executorService = Executors.newSingleThreadExecutor();
        /**
         * 创建一个最大线程数4的线程池
         */
        executorService = Executors.newFixedThreadPool(DEFAULT_MAX_THREAD);
    }

    /**
     * 将线程提交到线程池中执行
     *
     * @param runnable 单一线程
     */
    public static void submit(@NonNull Runnable runnable) {

        if (executorService == null)
            initExecutorService();

        executorService.execute(runnable);
    }

}
