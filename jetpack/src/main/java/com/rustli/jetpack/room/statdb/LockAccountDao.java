package com.rustli.jetpack.room.statdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface LockAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertErrData(LockAccountData lockAccountData);

}
