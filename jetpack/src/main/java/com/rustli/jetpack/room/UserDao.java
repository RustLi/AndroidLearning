package com.rustli.jetpack.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from room_user_test")
    List<UserEntity> getAll();

    @Query("select * from room_user_test where :userName")
    List<UserEntity> getUserByName(String userName);

    @Insert()
    void insertAll(UserEntity userEntity);

    @Insert()
    void insertList(List<UserEntity> userEntities);

    @Query("DELETE FROM room_user_test where (:currentTime - createTime > 15 * 24 * 60 * 60 * 1000)")
    void deleteUser(long currentTime);

}
