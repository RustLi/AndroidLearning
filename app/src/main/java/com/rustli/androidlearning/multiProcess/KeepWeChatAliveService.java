package com.rustli.androidlearning.multiProcess;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.rustli.androidlearning.R;
import com.rustli.libcommon.ServiceUtil;


public class KeepWeChatAliveService extends Service {
    private static final String TAG = "lwllwl";
    private boolean isThreadReleased;
    private static final int checkAliveInterval =  30 * 1000;
    private Thread weChatMonitorThread;
//    private static final String DAEMON_SERVICE_CLASS_NAME = "com.rustli.androidlearning.multiProcess.DaemonService";
    private static final String DAEMON_SERVICE_CLASS_NAME = "com.rustli.libtest.DaemonService";
    public static final int NOTIFY_ID = 1020;

    private static final String ANDROID_CHANNEL_ID = "com.rustli.androidlearning.Channel";
    private static final int NOTIFICATION_ID = 555;

    @Override
    public void onCreate() {
        Log.d(TAG,"KeepWeChatAliveService is oncreate");
//        isThreadReleased = false;
        //监听开启KeepAliveService
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(DaemonService.BROADCAST_START_KEEP_ALIVE);
//        getApplicationContext().registerReceiver(messageReceive, filter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeGround();
//            startForeground(NOTIFY_ID, new Notification());
//            startService(new Intent(this, InnerService.class));
        }
    }

    private void startKeepAliveService(){
        Log.d(TAG, "startKeepAliveService: ");
//        Intent intent = new Intent();
//        intent.setAction("com.example.xposedtest.TestActivity");
//        final Intent explicitIntent = new Intent(createExplicitFromImplicitIntent(this,intent));
//        startActivity(explicitIntent);

        Intent intent = new Intent();
//        ComponentName cn=new ComponentName("com.example.xposedtest",
//                "com.example.xposedtest.MainActivity");
        ComponentName cn=new ComponentName("com.rustli.androidlearning",
                "com.rustli.androidlearning.MainActivity");
        intent.setComponent(cn);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        isThreadReleased = true;
//        if (weChatMonitorThread != null){
//            weChatMonitorThread.interrupt();
//            weChatMonitorThread = null;
//        }
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
//        Log.d(TAG, "onStartCommand: weChatMonitorThread = " + weChatMonitorThread);
//        isThreadReleased = false;
//        if (weChatMonitorThread == null){
//            weChatMonitorThread = new Thread(new weChatMonitorRunnable(), "weChatMonitorRunnable");
//            weChatMonitorThread.start();
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeGround();
//            startForeground(NOTIFY_ID, new Notification());
//            startService(new Intent(this, InnerService.class));
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

    private void startForeGround(){
        Log.d(TAG, "startForeGround: ");
        //API 18以下，直接发送Notification并将其置为前台
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startForeground(NOTIFY_ID, new Notification());
        } else {
            //API 18以上，发送Notification并将其置为前台
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("\"" + getBaseContext().getString(R.string.app_name) + "\"正在运行");
            builder.setContentText("lwl ::: 点击进入工具箱");
            builder.setShowWhen(false);
//            builder.setSmallIcon(com.htd.box.R.drawable.wx);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d(TAG, "startForeGround: 111");
                String channelId = "tools";
                String channelName = "工具箱";
//                int importance = NotificationManager.IMPORTANCE_LOW;
                int importance = NotificationManager.IMPORTANCE_NONE;
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                NotificationManager notificationManager = (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(channelId);

            }
            startForeground(NOTIFY_ID, builder.build());
        }
    }

    public static void startup(Context context) {
        Log.d(TAG, "startup: 222");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "startup: startForegroundService");
            context.startForegroundService(new Intent(context, DaemonService.class));
        } else {
            Log.d(TAG, "startup: startService");
            context.startService(new Intent(context, DaemonService.class));
        }
    }


    /**
     * des: 内部Service可用来移除通知栏
     * 两个服务同时startForeground，且绑定同样的 ID，Stop 掉InnerService ，这样通知栏图标即被移除.
     */
    public static final class InnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d(TAG, "onStartCommand: start --- ");
//            Notification.Builder builder = new Notification.Builder(this);
//
//            builder.setContentTitle("\"" + getBaseContext().getString(R.string.app_name) + "\"正在运行");
//            builder.setShowWhen(false);
//            builder.setSmallIcon(R.drawable.wx);
//            Notification notification = builder.getNotification();
//            startForeground(NOTIFY_ID, notification);

//            //API 18以下，直接发送Notification并将其置为前台
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                startForeground(NOTIFY_ID, new Notification());
            } else {
                //API 18以上，发送Notification并将其置为前台
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("\"" + getBaseContext().getString(R.string.app_name) + "\"正在运行");
                builder.setContentText("DaemonService ::: 点击进入工具箱");
                builder.setShowWhen(false);
//                builder.setSmallIcon(com.htd.box.R.drawable.wx);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "tools";
                    String channelName = "工具箱";
                    int importance = NotificationManager.IMPORTANCE_NONE;
                    NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(
                            NOTIFICATION_SERVICE);
                    if (notificationManager != null){
                        notificationManager.createNotificationChannel(channel);
                        builder.setChannelId(channelId);
                    }
                }
                startForeground(NOTIFY_ID, builder.build());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true);
            } else {
                stopSelf();
            }
//            stopForeground(true);
////            stopSelf();
            Log.d(TAG, "onStartCommand: stop ---");
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }



}
