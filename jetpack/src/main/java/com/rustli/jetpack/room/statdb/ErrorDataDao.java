
package com.rustli.jetpack.room.statdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ErrorDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertErrData(ErrorData errorData);

    @Query("DELETE FROM error_data WHERE (:currentTime - createTime > 15 * 24 * 60 * 60 * 1000) ")
    void deleteOverTimeData(long currentTime);
}
