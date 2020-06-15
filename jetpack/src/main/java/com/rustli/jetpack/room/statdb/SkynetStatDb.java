
package com.rustli.jetpack.room.statdb;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {ErrorData.class}, version = 1)
public abstract class SkynetStatDb extends RoomDatabase {
    private static final String TAG = "SkynetStatDb";

    private static SkynetStatDb INSTANCE;

    public abstract ErrorDataDao errorDataDao();

    public abstract LockAccountDao lockAccountDao();

    private static final Object sLock = new Object();

    private static Executor mDiskIO;

    private static final String DB_NAME = "skynet_stat.db";

    public static SkynetStatDb getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (sLock) {
                if (INSTANCE == null) {
                    //此处只创建配置表
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SkynetStatDb.class, getSkynetStatDbName())
                            .allowMainThreadQueries()
                            .addCallback(mRoomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Executor getDiskIO(){
        if (mDiskIO == null){
            mDiskIO = Executors.newSingleThreadExecutor();
        }
        return mDiskIO;
    }


    private static String getSkynetStatDbName(){
        try {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            String dirName = ".skynet";
            File appDir = new File(root, dirName);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            String dbPath = appDir.getAbsolutePath() + File.separator + DB_NAME;
            File fdb = new File(dbPath);
            if (!fdb.exists()) {
                fdb.createNewFile();
            }
            Log.d(TAG, "getSkynetStatDbName dbPath: " + dbPath);
            return dbPath;
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"getSkynetStatDbName e = " + e.toString());
        }
        Log.e(TAG,"getSkynetStatDbName skynet_stat sdcard path error");
        return DB_NAME;
    }

    private static Callback mRoomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //第一次执行具体方法时走onCreate，只走一次
            Log.d(TAG,"onCreate");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //第一次执行具体方法时会走onOpen
            Log.d(TAG,"onOpen");
            //删除15天以前的数据
            mDiskIO.execute(new Runnable() {
                @Override
                public void run() {
                    if (INSTANCE != null){
                        INSTANCE.errorDataDao().deleteOverTimeData(System.currentTimeMillis());
                    }
                }
            });
        }
    };



}
