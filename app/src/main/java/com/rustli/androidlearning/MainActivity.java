package com.rustli.androidlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rustli.androidlearning.fragment_recycler.ItemFragment;
import com.rustli.androidlearning.fragment_recycler.dummy.DummyContent;
import com.rustli.androidlearning.multiProcess.DaemonService;
import com.rustli.androidlearning.multiProcess.KeepWeChatAliveService;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    Button button2;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: lwl");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, KeepWeChatAliveService.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, DaemonService.class));
            }
        });

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ItemFragment fragment = new ItemFragment();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: lwl");
        super.onResume();
        startService(new Intent(this, KeepWeChatAliveService.class));
//        startService(new Intent(this, LockService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, KeepWeChatAliveService.class));
//        stopService(new Intent(this, LockService.class));
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {


    }
}
