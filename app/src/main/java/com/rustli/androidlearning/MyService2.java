package com.rustli.androidlearning;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService2 extends Service {

    private static final String TAG = "MyService2";

    @Override
    public void onCreate() {
        Log.d(TAG,"MyService2 is oncreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
