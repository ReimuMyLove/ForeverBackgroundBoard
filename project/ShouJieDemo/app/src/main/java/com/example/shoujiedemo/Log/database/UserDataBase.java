package com.example.shoujiedemo.Log.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoujiedemo.Log.dao.UserDao;
import com.example.shoujiedemo.entity.User;

@Database(entities = User.class,version = 1,exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    private static UserDataBase userDataBase;

    public static UserDataBase getInstance(Context context){
        if(userDataBase == null){
            synchronized (UserDataBase.class){
                userDataBase = Room.databaseBuilder(context,UserDataBase.class,"user_database").build();
            }
        }
        return userDataBase;
    }
    public abstract UserDao getUserDao();
}
