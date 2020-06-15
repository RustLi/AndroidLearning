package com.rustli.jetpack.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {UserEntity.class}, version = 5)
public abstract class UserDataBase extends RoomDatabase {
    private static UserDataBase mInstance = null;
    public abstract UserDao getUserDao();
    private static final String TAG = "UserDataBase";
    private static Executor mDiskIO;


    public static UserDataBase getUserDatabase(Context context){
        if (mInstance == null){
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDataBase.class, "/sdcard/roomtest/user_test.db")
//                    .addMigrations(migration2_3, migration3_4,migration4_5)
//                    .fallbackToDestructiveMigration() //会清空，慎重！
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.d(TAG, "handlerDataBase onCreate");
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            Log.d(TAG, "handlerDataBase onOpen");
                            Log.d(TAG, "onOpen: mInstance = " + mInstance);
                            mDiskIO.execute(new Runnable() {
                                @Override
                                public void run() {
                                    if (mInstance != null){
                                        mInstance.getUserDao().deleteUser(System.currentTimeMillis());
                                    }
                                }
                            });
                        }
                    })
                    .build();
        }

        return mInstance;
    }



    public static void destroyInstance() {
        mInstance = null;
    }

    public static Executor getDiskIO(){
        if (mDiskIO == null){
            mDiskIO = Executors.newSingleThreadExecutor();
        }
        return mDiskIO;
    }

    private static final Migration migration2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE visitorrecord ADD COLUMN timeStamp TEXT");
        }
    };

    private static final Migration migration3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Face ADD COLUMN vipGroupName TEXT");
        }
    };

    private static final Migration migration4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Face ADD COLUMN vipGroupName TEXT");
        }
    };
}
