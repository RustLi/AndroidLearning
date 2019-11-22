package com.rustli.androidlearning.multiProcess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class KeepReceiver extends BroadcastReceiver {
    private static final String TAG = "KeepReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: action is " + action);
    }
}
