package com.rustli.androidlearning;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.rustli.androidlearning.fragment_recycler.ItemFragment;
import com.rustli.androidlearning.fragment_recycler.dummy.DummyContent;
import com.rustli.androidlearning.multiProcess.KeepWeChatAliveService;
import com.rustli.libcommon.PrefUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    private static final String TAG = "lwl";
    EditText etNum;
    Button btOk;
    public static final String KEY_TEST = "key_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNum = findViewById(R.id.et_num);
        btOk = findViewById(R.id.bt_ok);

//        if (savedInstanceState == null) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            ItemFragment fragment = new ItemFragment();
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.commit();
//        }
//        Log.d(TAG, "onCreate: lwl");
//        String aaa = null;
//        for (int i = 0; i < 10; i++) {
//            if (i == 5){
//                test(aaa);
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        String ret = PrefUtils.get(getApplicationContext(),KEY_TEST,"");
//        Log.d(TAG, "lwl onResume: ret = " + ret);
//        KeepWeChatAliveService.startup(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }

    public String jUnitTest() {
        return "jUnitTest";
    }

    private void test(@NonNull String test){
        Log.d(TAG, "lwl: test 111 = " + test);

//        Log.d(TAG, "test: 2222 length = " + test.length());
    }
}
