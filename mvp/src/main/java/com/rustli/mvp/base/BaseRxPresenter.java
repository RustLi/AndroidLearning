package com.rustli.mvp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseRxPresenter<T> implements BasePresenter<T>{
    protected T view;
    private CompositeDisposable compositeDisposable = null;

    protected void subscribe(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void unSubscribe(){
        if (compositeDisposable != null){
            compositeDisposable.dispose();
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        unSubscribe();
        this.view = null;
    }
}
