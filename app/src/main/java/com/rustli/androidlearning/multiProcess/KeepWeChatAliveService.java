package com.rustli.androidlearning.multiProcess;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rustli.libtest.DaemonService;
import com.rustli.libtest.ServiceUtil;


public class KeepWeChatAliveService extends Service {
    private static final String TAG = "KeepWeChatAliveService";
    private boolean isThreadReleased;
    private static final int checkAliveInterval =  30 * 1000;
    private Thread weChatMonitorThread;
//    private static final String DAEMON_SERVICE_CLASS_NAME = "com.rustli.androidlearning.multiProcess.DaemonService";
    private static final String DAEMON_SERVICE_CLASS_NAME = "com.rustli.libtest.DaemonService";


    @Override
    public void onCreate() {
        Log.d(TAG,"KeepWeChatAliveService is oncreate");
        isThreadReleased = false;
        //监听开启KeepAliveService
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(DaemonService.BROADCAST_START_KEEP_ALIVE);
//        getApplicationContext().registerReceiver(messageReceive, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isThreadReleased = true;
        if (weChatMonitorThread != null){
            weChatMonitorThread.interrupt();
            weChatMonitorThread = null;
        }
//        getApplicationContext().unregisterReceiver(messageReceive);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: weChatMonitorThread = " + weChatMonitorThread);
        isThreadReleased = false;
        if (weChatMonitorThread == null){
            weChatMonitorThread = new Thread(new weChatMonitorRunnable(), "weChatMonitorRunnable");
            weChatMonitorThread.start();
        }
        return START_STICKY;
    }

    private BroadcastReceiver messageReceive = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            if (intent != null && intent.getAction() != null){
                String action = intent.getAction();
                Log.d(TAG, "onReceive: action is " + action);
            }
        }
    };

    public class  weChatMonitorRunnable implements Runnable {
        public void run() {
            while (!isThreadReleased){
                boolean isAlive = ServiceUtil.isServiceAlive(KeepWeChatAliveService.this,
                        DAEMON_SERVICE_CLASS_NAME);
                Log.d(TAG, "run: isAlive is " + isAlive);
                if (!isAlive){
                    Log.d(TAG, "run: DaemonService died, DaemonService restart");
                    startService(new Intent(KeepWeChatAliveService.this, DaemonService.class));
                }

                try {
                    Thread.sleep(checkAliveInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
