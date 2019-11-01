package com.rustli.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.rustli.mvvm.databinding.ActivityMvvmBinding;


/**
 * @date: 2019-10-31
 * @author: lwl
 * @description:  Mvvm示例
 */

public class MvvmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        //Method 1
        /*final  DataViewModel viewModel = new DataViewModel();
        binding.setViewModel(viewModel);*/

        //Method 2
        DataViewModel viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        binding.setViewModel(viewModel);

    }
}
