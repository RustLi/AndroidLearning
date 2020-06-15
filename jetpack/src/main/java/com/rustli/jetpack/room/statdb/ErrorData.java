
package com.rustli.jetpack.room.statdb;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "error_data")
public final class ErrorData {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @Nullable
    @ColumnInfo(name = "method") //调用的接口方法，hashCode回调所在的方法
    public String method;

    @ColumnInfo(name = "errType")
    public int errType;

    @ColumnInfo(name = "errCode")
    public int errCode;

    @ColumnInfo(name = "errMsg")
    public String errMsg;

    @ColumnInfo(name = "createTime")
    public long createTime;
}
