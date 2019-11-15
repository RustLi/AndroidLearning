package com.rustli.mvp.task;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;

public class TaskActivity  extends AppCompatActivity implements TaskContract.View{

    private TaskPresenter taskPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.i("onCreate");
        super.onCreate(savedInstanceState);
        taskPresenter = new TaskPresenter(this);
    }

    @Override
    protected void onResume() {
        Timber.i("onResume");
        super.onResume();
        taskPresenter.addTask();
    }

    @Override
    public void showAllTask() {
        Timber.i("showAllTask");
    }

    @Override
    public void showAddTask() {
        Timber.i("showAddTask");
    }
}
