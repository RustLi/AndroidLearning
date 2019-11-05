package com.rustli.dagger2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.rustli.dagger2.di.DaggerMainComponent;
import com.rustli.dagger2.di.MainModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "MainActivity";

    @Inject
    MainPresenter mainPresenter;


    private MainContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //不采用Dagger2
//        presenter = new MainPresenter(this);
//        presenter.loadData();
        //使用Dagger2
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
        mainPresenter.loadData();
    }

    @Override
    public void updateUI() {
        Log.d(TAG, "updateUI: 111 ");
    }
}
