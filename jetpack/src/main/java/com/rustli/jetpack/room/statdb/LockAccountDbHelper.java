package com.rustli.jetpack.room.statdb;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

public class LockAccountDbHelper {
    private static final String TAG = "ErrorDataDbHelper";
    private static  Context mContext;

    private static volatile LockAccountDbHelper INSTANCE = new LockAccountDbHelper();
    private final Executor mDiskIO = SkynetStatDb.getDiskIO();
    private final LockAccountDao mLockAccountDataDao = SkynetStatDb.getInstance(mContext).lockAccountDao();

    public static LockAccountDbHelper get(Context context) {
        if (mContext == null) mContext = context;
        return INSTANCE;
    }

    /**
     * des: 插入封号时的信息
     */
    public void insertLockAccountData(@NonNull String account, int errType, int errCode, String errMsg){
        LockAccountData lockAccountData = new LockAccountData();
        lockAccountData.account = account;
        lockAccountData.createTime = System.currentTimeMillis();
        lockAccountData.errType = errType;
        lockAccountData.errCode = errCode;
        lockAccountData.errMsg = errMsg;
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mLockAccountDataDao.insertErrData(lockAccountData);
            }
        };
        try {
            mDiskIO.execute(saveRunnable);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"insertLockAccountData e = " + e.toString());
        }
    }
}
