package com.rustli.jetpack.room;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.rustli.jetpack.R;


public class RoomTestActivity extends AppCompatActivity {
    private static final String TAG = "RoomTestActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_test);
        new Thread(this::handlerDataBase).start();
    }

    private void handlerDataBase(){
        Log.d(TAG, "handlerDataBase: ");
        UserDataBase userDataBase = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "/sdcard/room_user_test.db").build();
        UserDao userDao = userDataBase.getUserDao();
        for (int index = 1; index < 100;index++){
            userDao.insertAll(new UserEntity(index,"jack","male",30));
        }
        Log.d(TAG, "handlerDataBase: length = " + userDao.getAll().size());
    }

}
