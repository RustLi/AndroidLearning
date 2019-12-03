package com.rustli.libcommon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @date: 2019-11-26
 * @author: lwl
 * @description: 本地广播，只在app内发送和接收，安全性和效率高。可替代handler，常用于组件内通信。
 */

public class LocalBroadcast {

    private static final String BROADCAST_CHECK = "broadcast_check";

    /**
     * des: 发送广播，进程A
     * @param
     */
    private void sendMessage(Context context, String message) {
        Intent intent = new Intent();
        intent.putExtra(BROADCAST_CHECK, message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * des: 注册广播，进程B
     * @param
     */
    private void rigisterBroadcast(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_CHECK);
        LocalBroadcastManager.getInstance(context).registerReceiver(messageReceiver, filter);
    }

    /**
     * des: 解注册广播，进程B
     * @param
     */
    private void unregisterReceiver(Context context) {
        if (context != null) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(messageReceiver);
        }
    }


    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //TODO 逻辑处理
        }
    };
}


