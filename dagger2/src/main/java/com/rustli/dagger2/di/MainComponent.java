package com.rustli.dagger2.di;

import com.rustli.dagger2.MainActivity;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
