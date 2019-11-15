package com.rustli.jetpack.lifecycle;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

public class BasePresenter implements IPresenter {
    private static final String TAG = "BasePresenter";
    @Override
    public void onCreate(LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStart(LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume(LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause(LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop(LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestory(LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "onDestory: ");
    }

    @Override
    public void onAny(LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "onAny: ");
    }
}
