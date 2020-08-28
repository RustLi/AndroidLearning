package com.rustli.libcommon;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUtil {
    private static final String TAG = "ThreadUtil";

    private static final ExecutorService cacheThreadPool;
    private int maxPoolCount = 0;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(4, Math.min(CPU_COUNT - 1, 8));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 0;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "lwl customThreadPool #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>();

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
//        threadPoolExecutor.allowCoreThreadTimeOut(true);
//        cacheThreadPool = threadPoolExecutor;

//        cacheThreadPool = Executors.newCachedThreadPool();
        cacheThreadPool = Executors.newFixedThreadPool(CPU_COUNT + 1);
    }

    private void cacheThreadPoolTest(int num) {
        Log.d(TAG, "cacheThreadPoolTest: num = " + num);
        final long cacheTime = System.currentTimeMillis();
        final int length = num;
        for (int i = 0; i < length; i++) {
            final int index = i;
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行第" + index + "个任务");
                    ThreadUtil.sleepRandom(40, 80);
                    Log.d(TAG, "run: cacheThreadPool = " + cacheThreadPool);
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)cacheThreadPool;
                    int activeCount = threadPoolExecutor.getActiveCount();
                    long completedCount = threadPoolExecutor.getCompletedTaskCount();
                    if (activeCount > maxPoolCount){
                        maxPoolCount = activeCount;
                    }
                    if (completedCount == length - 1 ){
                        Log.d(TAG, "run: cacheTime = " + (System.currentTimeMillis() - cacheTime) + " maxActiveCount = " + maxPoolCount);
                    }
                }
            });
        }
        Log.d(TAG, "总数  ：：：： run: cacheThreadPool = " + cacheThreadPool);
    }


    /**
     * 随机睡觉，避免操作太有规律
     *
     * @param minMillis
     * @param maxMillis
     */
    public static void sleepRandom(int minMillis, int maxMillis) {
        int range = maxMillis - minMillis;

        try {
            long time = minMillis;
            if (range > 0) {
                time += System.currentTimeMillis() % range;
            }
            Log.d(TAG, "sleepRandom millis :" + time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
