/*
package com.rustli.androidlearning.multiProcess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rustli.libtest.ServiceUtil;

import java.util.List;

public class DaemonService extends Service {

    private static final String TAG = "DaemonService";
    public static final String BROADCAST_START_KEEP_ALIVE = "broadcast_start_keep_alive";
    private boolean isThreadReleased;
    public static final String KEEP_ALIVE_CLASS_NAME = "com.rustli.androidlearning.multiProcess.KeepWeChatAliveService";
    private static final int CHECK_ALIVE_INTERVAL = 60 * 1000;
    private Thread guardThread;

    @Override
    public void onCreate() {
        Log.d(TAG,"DaemonService is oncreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        if (guardThread == null){
            guardThread = new Thread(new GuardRunnable(), "GuardRunnable");
            guardThread.start();
        }
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isThreadReleased = true;
        if (guardThread != null){
            guardThread.interrupt();
            guardThread = null;
        }
    }


    public class GuardRunnable implements Runnable {
        public void run() {
            while (!isThreadReleased){
                boolean isAlive = ServiceUtil.isServiceAlive(DaemonService.this,
                        KEEP_ALIVE_CLASS_NAME);
                Log.d(TAG, "run: isAlive is " + isAlive);
                if (!isAlive){
                    Log.d(TAG, "run: KeepAliveService died, restart KeepALiveService");
                    startKeepAliveService(DaemonService.this);
                }
                try {
                    Thread.sleep(CHECK_ALIVE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void startKeepAliveService(Context context){
        Log.d(TAG, "startKeepAliveService: ");
//        Intent intent = new Intent();
//        intent.setAction(BROADCAST_START_KEEP_ALIVE);
//        getApplicationContext().sendBroadcast(intent);
//        ComponentName componentName = new ComponentName("com.rustli.androidlearning.multiProcess", "com.rustli.androidlearning.multiProcess.KeepWeChatAliveService");
//        intent.setComponent(componentName);

//        startService(intent);

//        startService(new Intent("com.rustli.androidlearning.multiProcess.KeepWeChatAliveService"));

//        Intent intent = new Intent();
//        intent.setAction("com.rustli.androidlearning.multiProcess.KeepWeChatAliveService");
//        final Intent explicitIntent = new Intent(createExplicitFromImplicitIntent(this,intent));
//        startService(explicitIntent);

        startService(new Intent(this, KeepWeChatAliveService.class));
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
*/
