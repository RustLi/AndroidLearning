package com.rustli.androidlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.rustli.androidlearning.multiProcess.DaemonService;
import com.rustli.androidlearning.lockscreen.LockService;
import com.rustli.libtest.DaemonService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button button2;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                stopService(new Intent(MainActivity.this, KeepWeChatAliveService.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, DaemonService.class));
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
//        startService(new Intent(this, KeepWeChatAliveService.class));
        startService(new Intent(this, LockService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(this, KeepWeChatAliveService.class));
    }

}
