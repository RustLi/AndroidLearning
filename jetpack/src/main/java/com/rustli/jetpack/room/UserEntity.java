package com.rustli.jetpack.room;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "room_user_test")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "createTime")
    public long createTime;

    public UserEntity(String userName, String gender, int age,long createTime) {
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.createTime = createTime;
    }


}
