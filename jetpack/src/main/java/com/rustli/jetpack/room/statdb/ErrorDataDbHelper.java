package com.rustli.jetpack.room.statdb;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

/**
 * @date: 2020/6/6
 * @author: lwl
 * @description: 回调错误信息写入数据库
 */
public class ErrorDataDbHelper {

    private static final String TAG = "ErrorDataDbHelper";
    private static Context mContext;

    private static volatile ErrorDataDbHelper INSTANCE = new ErrorDataDbHelper();
    private final Executor mDiskIO = SkynetStatDb.getDiskIO();
    private final ErrorDataDao mErrorDataDao = SkynetStatDb.getInstance(mContext).errorDataDao();

    public static ErrorDataDbHelper get(Context context) {
        if (mContext == null) mContext = context;
        return INSTANCE;
    }

    /**
     * des: 插入回调时的错误信息
     */
    public void insertErrorData(@NonNull String method,int errType, int errCode, String errMsg){
        ErrorData errorData = new ErrorData();
        errorData.method = method;
        errorData.createTime = System.currentTimeMillis();
        errorData.errType = errType;
        errorData.errCode = errCode;
        errorData.errMsg = errMsg;
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mErrorDataDao.insertErrData(errorData);
            }
        };
        try {
            mDiskIO.execute(saveRunnable);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"insertErrorData e = " + e.toString());
        }
    }
}
