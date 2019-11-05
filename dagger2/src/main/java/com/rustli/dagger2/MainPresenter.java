package com.rustli.dagger2;

import android.util.Log;

import javax.inject.Inject;


public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";

    private MainContract.View view;

    @Inject
    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        Log.d(TAG, "loadData: ");
        view.updateUI();
    }
}
