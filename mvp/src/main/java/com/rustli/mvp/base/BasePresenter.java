package com.rustli.mvp.base;

public interface BasePresenter<T> {
    void attachView(T view);
    void detachView();
}
