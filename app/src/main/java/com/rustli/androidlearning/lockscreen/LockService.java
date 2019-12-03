package com.rustli.androidlearning.lockscreen;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class LockService extends Service {
    private static final String TAG = "LockService";

    private BroadcastReceiver receiver;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "onReceive: action = " + action);
                if (action != null && action.equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.d(TAG, "onReceive: 收到锁屏广播");
                    Intent lockscreen = new Intent(LockService.this, LockScreenActivity.class);
                    lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(lockscreen);
                }

                if (action != null && action.equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d(TAG, "onReceive: 收到亮屏广播");
                    Intent broadcast = new Intent("FinishActivity");
                    context.sendBroadcast(broadcast);//发送对应的广播
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);

    }
}
