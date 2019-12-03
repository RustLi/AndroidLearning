package com.rustli.androidlearning.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rustli.androidlearning.R;

public class LockScreenActivity extends AppCompatActivity {
    private static final String TAG = "LockScreenActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        // 动态注册广播，这个广播是在屏幕亮的时候，发送广播，来关闭当前的Activity
        registerReceiver(receiver, new IntentFilter("FinishActivity"));

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        if (receiver != null){
            unregisterReceiver(receiver);
        }
    }


    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: action = " + intent.getAction());
            // 收到广播
            LockScreenActivity.this.finish();
        }
    };
}
