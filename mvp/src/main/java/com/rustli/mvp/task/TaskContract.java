package com.rustli.mvp.task;

import com.rustli.mvp.base.BasePresenter;
import com.rustli.mvp.base.BaseView;

public interface TaskContract {

    interface View extends BaseView{
        void showAllTask();
        void showAddTask();
    }

    interface Presenter extends BasePresenter<View>{
        void addTask();
        void deleteTask();
    }
}
