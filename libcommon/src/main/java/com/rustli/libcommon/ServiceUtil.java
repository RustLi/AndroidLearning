package com.rustli.libcommon;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ServiceUtil {

    // 判断服务是否开启
    public static boolean isServiceAlive(Context context,
                                         String serviceClassName) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null){
            List<ActivityManager.RunningServiceInfo> running = manager
                    .getRunningServices(Integer.MAX_VALUE);
            for (int i = 0; i < running.size(); i++) {
                if (serviceClassName.equals(running.get(i).service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
