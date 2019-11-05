package com.rustli.rxmodule.RxPermission;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rustli.rxmodule.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class RxPermissionActivity extends AppCompatActivity {

    private static final String TAG = "RxPermissionActivity";
    private RxPermissions rxPermissions;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    /**
     * des: 请求单个权限
     *
     * @param
     */
    private void requestSinglePermission() {
        disposable = rxPermissions
                    .request(Manifest.permission.CAMERA)
                    .subscribe(granted -> {
                        if (granted) {
                            //同意权限
                            Log.d(TAG, "requestSinglePermission: agree camera permission");
                        } else {
                            //权限拒绝
                        }
                    });
    }

    /**
     * des: 同时请求多个权限，综合判断
     *
     * @param
     */
    private void requestMultiPermission() {
        disposable = rxPermissions
                    .request(Manifest.permission.CAMERA,
                            Manifest.permission.READ_PHONE_STATE)
                    .subscribe(granted -> {
                        if (granted) {
                            // All requested permissions are granted
                        } else {
                            // At least one permission is denied
                        }
                    });
    }


    /**
     * des: 同时请求多个权限，多次处理
     *
     * @param
     */
    private void requestMutiPerOneByOne() {
        disposable = rxPermissions
                    .requestEach(Manifest.permission.CAMERA,
                            Manifest.permission.READ_PHONE_STATE)
                    .subscribe(permission -> { // will emit 2 Permission objects
                        if (permission.granted) {
                            // `permission.name` is granted !
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                        }
                    });
    }

    /**
     * des: 请求多个权限
     * @param
     */
    private void requestMultiPerAll() {
         disposable = rxPermissions
                    .requestEachCombined(Manifest.permission.CAMERA,
                            Manifest.permission.READ_PHONE_STATE)
                    .subscribe(permission -> { // will emit 1 Permission object
                        if (permission.granted) {
                            // All permissions are granted !
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // At least one denied permission without ask never again
                        } else {
                            // At least one denied permission with ask never again
                            // Need to go to the settings
                        }
                    });
    }
}

