package com.rustli.mvp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rustli.mvp.task.TaskActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.btn_hello)
    TextView btnHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //监听开启KeepAliveService
        IntentFilter filter = new IntentFilter();
        filter.addAction("broadcast_start_keep_alive");
        getApplicationContext().registerReceiver(messageReceive, filter);
    }

    @OnClick(R.id.btn_hello)
    public void onViewClicked() {
        startActivity(new Intent(this, TaskActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getApplicationContext().unregisterReceiver(messageReceive);
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
}
