package com.rustli.jetpack.mvvm;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;


public class DataViewModel extends ViewModel {

    //可观察的数据
    public final ObservableField<String> id = new ObservableField<>();

    public void loadData(View view){
        DataModel dataModel = new DataModel(model -> id.set(model.getId()));
        String testStr = "test";
        dataModel.loadModel(testStr);
    }
}
