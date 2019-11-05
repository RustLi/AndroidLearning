package com.rustli.dagger2;

public interface MainContract {

    interface Presenter{
        void loadData();
    }

    interface View{
        void updateUI();
    }
}
