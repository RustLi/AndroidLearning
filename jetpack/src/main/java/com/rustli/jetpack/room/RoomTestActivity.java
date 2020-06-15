package com.rustli.jetpack.room;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.rustli.jetpack.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class RoomTestActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "RoomTestActivity";
    private static final int PERMISSIONS = 100;
    private String[] mPerms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Handler mBackgroundHandler;
    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_test);
        userDao = UserDataBase.getUserDatabase(getApplicationContext()).getUserDao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }



    private void handlerDataBase() {
        Log.d(TAG, "handlerDataBase: ");
        UserDataBase.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertAll(new UserEntity("test","amle",30,System.currentTimeMillis()));
                Log.d(TAG, "run: insert end");
            }
        });
    }

    private void test(){
        Log.d(TAG, "test: thread = " + Thread.currentThread().getName() + " id = " + Thread.currentThread().getId());
        getBackgroundHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "backGround: thread = " + Thread.currentThread().getName() + " id = " + Thread.currentThread().getId());
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: new handler ");
                        Log.d(TAG, "new handler: thread = " + Thread.currentThread().getName() + " id = " + Thread.currentThread().getId());
                    }
                });
            }
        },1000);
    }

    private void handlerTest(){
        Log.d(TAG, "handlerTest: ");
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "很抱歉,APP出现异常,即将关闭", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

    @AfterPermissionGranted(PERMISSIONS)
    private void requestPermission() {
        Log.d(TAG, "main: thread = " + Thread.currentThread().getName() + " id = " + Thread.currentThread().getId());
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            //Log.d(TAG, "onClick: 获取读写内存权限,Camera权限和wifi权限");
            new Thread(this::handlerDataBase).start();
//            test();
//            handlerTest();
        } else {
            EasyPermissions.requestPermissions(this, "获取读写内存权限,Camera权限,wifi权限,系统设置权限", PERMISSIONS, mPerms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied: 拒绝权限");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }
    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }
}
