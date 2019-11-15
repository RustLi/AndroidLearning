package com.rustli.mvp.base;

import com.rustli.mvp.utils.FileLoggingTree;

import org.litepal.LitePalApplication;

import timber.log.Timber;

public class App extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FileLoggingTree fileLoggingTree = new FileLoggingTree();
        Timber.plant(fileLoggingTree);
    }
}
