package com.rustli.androidlearning.parcelable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rustli.androidlearning.R;

public class ParcelActivity extends Activity {
    private static final String TAG = "ParcelActivty";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel);
        Intent intent = getIntent();
        Bundle bun = intent.getExtras();
        if (bun != null){
            Books books = bun.getParcelable("books");
            if (books != null){
                Log.d(TAG, "onCreate: name is " + books.getBookName());
            }
        }
    }
}
