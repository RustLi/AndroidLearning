package com.rustli.androidlearning.logback;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class LogbackActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    private static final String TAG = "LogbackActivity";
    private static final int PERMISSIONS = 100;
    private String[] mPerms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    private void init(){
        String path = "/sdcard/logback2/test";
        File file = new File(path);
        FileLoggingTree fileLoggingTree = new FileLoggingTree();
        Timber.plant(fileLoggingTree);
        Log.d(TAG, "onCreate: 333" + file.exists());
        Timber.d("onCreate 444 file = %b ", file.exists());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    Timber.d("onCreate %d ", i);
                }
            }
        }).start();
    }

    @AfterPermissionGranted(PERMISSIONS)
    private void requestPermission() {
        Log.d(TAG, "main: thread = " + Thread.currentThread().getName() + " id = " + Thread.currentThread().getId());
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            //Log.d(TAG, "onClick: 获取读写内存权限,Camera权限和wifi权限");
//            new Thread(this::handlerDataBase).start();
//            test();
//            handlerTest();
            init();
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
}
