package com.rustli.jetpack.room.statdb;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lock_account_data")
public class LockAccountData {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @Nullable
    @ColumnInfo(name = "account") //账户信息
    public String account;

    @ColumnInfo(name = "errType")
    public int errType;

    @ColumnInfo(name = "errCode")
    public int errCode;

    @ColumnInfo(name = "errMsg")
    public String errMsg;

    @ColumnInfo(name = "createTime")
    public long createTime;
}
