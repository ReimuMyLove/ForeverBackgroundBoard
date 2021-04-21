package com.example.shoujiedemo.Log.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoujiedemo.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public Long insertUser(User user);     //插入数据

    @Update
    public void updateUser(User user);     //更新用户数据

    @Query("select * from User")
    public List<User> queryUser();         //查询所有用户
}
