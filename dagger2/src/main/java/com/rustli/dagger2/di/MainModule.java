package com.rustli.dagger2.di;

import com.rustli.dagger2.MainContract;

import dagger.Module;
import dagger.Provides;
@Module
public class MainModule {
    private final MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    MainContract.View providerView(){
        return view;
    }

}
