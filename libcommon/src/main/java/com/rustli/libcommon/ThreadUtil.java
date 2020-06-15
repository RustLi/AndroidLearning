package com.rustli.libcommon;

import android.util.Log;

public class ThreadUtil {
    private static final String TAG = "ThreadUtil";

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
