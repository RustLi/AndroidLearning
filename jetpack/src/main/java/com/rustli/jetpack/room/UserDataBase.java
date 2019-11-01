package com.rustli.jetpack.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {
    private static UserDataBase mInstance = null;
    public abstract UserDao getUserDao();

    public static UserDataBase getUserDatabase(Context context){
        if (mInstance == null){
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDataBase.class, "DeepCamFace")
                    .addMigrations(migration2_3, migration3_4)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
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
}
