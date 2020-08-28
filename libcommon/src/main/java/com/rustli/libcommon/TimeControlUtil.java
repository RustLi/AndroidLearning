package com.rustli.libcommon;

import android.text.format.Time;


/**
 * Created by ljc on 2020-06-18
 * 时间控制器，实现定时间隔、随机间隔、每日可用时段配置
 * 
 */
public class TimeControlUtil {

    //下次可用时间
    private long nextTime = 0;

    //间隔
    private long intervalMin = 0;
    private long intervalMax = 0;

    //可用时间段
    private int startHour = -1;
    private int endHour = -1;

    /**
     * 固定间隔，单位 ms
     *
     * @param fixedIntervalMills
     */
    public TimeControlUtil(long fixedIntervalMills) {
        this.intervalMin = fixedIntervalMills;
    }

    /**
     * 随机间隔，单位 ms
     *
     * @param minIntervalMills 最小间隔 ms
     * @param maxIntervalMills 最大间隔 ms
     */
    public TimeControlUtil(long minIntervalMills, long maxIntervalMills) {
        this.intervalMin = minIntervalMills;
        this.intervalMax = maxIntervalMills;
    }

    /**
     * 设置可用时间段
     * 如只允许7~21点可用，setAvailable(7, 21)，实际生效时间为7:00:00 ~ 20:59:59
     * 也可支持 22~3 点可用这种设置
     * 不设置则全天可用
     *
     * @param startHour 0~23
     * @param endHour   0~23
     */
    public void setAvailable(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    /**
     * 是否到达间隔时间，可用了，若返回true, 则自动记录本次时间，等待下次间隔
     *
     * @return true or false
     */
    public boolean isAvailable() {
        if (System.currentTimeMillis() >= this.nextTime && isInTimeInterval(startHour, endHour)) {
            calcNextTime();
            return true;
        }

        return false;
    }

    /**
     * 距离下次可用时间还有多久(ms) ，可用于sleep
     *
     * @return
     */
    public long howLong() {
        long how = this.nextTime - System.currentTimeMillis();
        return how > 0 ? how : intervalMin;
    }


    /**
     * 重置计时
     *
     * @return
     */
    public void reset() {
        this.nextTime = 0;
    }

    /**
     * 计算下次时间
     */
    private void calcNextTime() {
        this.nextTime = System.currentTimeMillis() + intervalMin;
        if (intervalMax > 0 && intervalMax > intervalMin) {
            this.nextTime += System.currentTimeMillis() % (intervalMax - intervalMin);
        }
    }

    /**
     * 当前是否在指定时间段，小时粒度，不含后endHour，支持跨24点
     *
     * @return   startHour <= 当前时间 < endHour
     */
    public static boolean isInTimeInterval(int startHour, int endHour) {
        if (startHour != -1 && endHour != -1) {
            int hour;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                hour = calendar.get(android.icu.util.Calendar.HOUR_OF_DAY);
            } else {
                Time t = new Time();
                t.setToNow();
                hour = t.hour;
            }

            if (hour >= startHour && hour < endHour) {
                return true;
            } else {
                return (endHour < startHour) && (hour >= startHour || hour < endHour);
            }
        }
        return true;
    }
}
