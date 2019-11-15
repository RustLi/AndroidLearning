package com.rustli.mvp.task;

import com.rustli.mvp.base.BaseRxPresenter;
import com.rustli.mvp.net.task.TaskNetworkManager;
import com.rustli.mvp.net.task.TaskResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TaskPresenter extends BaseRxPresenter<TaskContract.View> implements TaskContract.Presenter {

    public TaskPresenter(TaskContract.View view) {
        attachView(view);
    }

    @Override
    public void addTask() {
        Timber.i("addTask");
        Disposable disposable= TaskNetworkManager.getTaskRequest().addTask("1", "rust",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TaskResponse>() {
                    @Override
                    public void accept(TaskResponse taskResponse) throws Exception {
                        Timber.i("addTask taskResponse is %s " , taskResponse.code);
                        view.showAddTask();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.i("addTask is error : %s ", throwable.getMessage());
                    }
                });
        subscribe(disposable);
    }

    @Override
    public void deleteTask() {

    }
}
