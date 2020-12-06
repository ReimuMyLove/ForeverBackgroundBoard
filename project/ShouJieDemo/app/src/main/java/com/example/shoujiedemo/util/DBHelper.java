package com.example.shoujiedemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // 数据库名
    private static final String DATABASE_NAME = "userInfo.db";
    private static final int DATABASE_VERSION = 1;
    public static String tableName = "userInfo";
    //数据库版本号

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         *  初始生成一个当前用户登录表格保存用户登录信息，便于程序使用过程中随时调用
         *  注销时删除用户信息
         *  肖帅 2020.11.23
         */
        //生成用户信息表格
        String createTable1 =
                "create table if not exists userInfo(userID varchar(50), userPW varchar(50))";
        db.execSQL(createTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)   {

    }
}
