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
        /*
           初始生成一个当前用户登录表格保存用户登录信息，便于程序使用过程中随时调用
           注销时删除用户信息
           肖帅 2020.11.23
         */
        //生成用户登录信息表格
        String createTable1 =
                "create table if not exists " +
                        "userInfo( " +
                        "userID int, " +
                        "userName varchar(50), " +      //用户名
                        "userPassword varchar(50), " +  //用户密码
                        "userSex varchar(10), " +       //用户性别
                        "picName varchar(100)," +       //用户头像
                        "userAge int, " +               //用户年龄
                        "userFans int, " +              //粉丝数
                        "userFollow int, " +            //关注数
                        "userSign varchar(420))";       //个性签名
        db.execSQL(createTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)   {

    }
}
