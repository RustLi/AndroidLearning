package com.rustli.libcommon;

import android.app.AlertDialog;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;

public class DeviceUtil {
    private static final String TAG = "DeviceUtil";

    /**
     * des: 执行adb reboot（需要有root权限）
     * @param
     */
    private void reboot(Context context) {
        try {
            Runtime.getRuntime().exec("su -c reboot");
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    /**
     * des: 设备重启
     * @param
     */
    private void rebootPowerManager(Context context){
        //重启到fastboot模式
        PowerManager pManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pManager != null) {
            pManager.reboot("重启");
        }
    }

    /**
     * des: 采用反射的方法执行关机
     * @param
     */
    private void shutDowm() {
        try {
            //获得ServiceManager类
            Class ServiceManager = Class.forName("android.os.ServiceManager");
            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            //获得IPowerManager.Stub类
            Class cStub = Class.forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class, boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager, false, true);
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }

    }
}
