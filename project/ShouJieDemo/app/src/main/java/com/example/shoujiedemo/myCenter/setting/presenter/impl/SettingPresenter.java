package com.example.shoujiedemo.myCenter.setting.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.example.shoujiedemo.home.recommen.activity.MainActivity;
import com.example.shoujiedemo.myCenter.setting.service.CookiesClear;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.AboutActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.FeedbackViewActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.SafeActivity;
import com.example.shoujiedemo.util.DBHelper;

public class SettingPresenter {
    CookiesClear cookiesClear = new CookiesClear();
    /**
     * 生命周期相关方法
     */
    public void onDestory(){
        //do something to release and avoid memory leak;
    }
    public void onStart(){
        //do something when onStart
    }
    public void onStop(){
        //do something when onStop
    }
    public void onResume(){
        //do something when onResume
    }
    public void onPause(){
        //do something when onPause
    }

    /**
     * 逻辑实现方法
     */
   /* public void Logout(Context context){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "DELETE FROM userInfo ";
        db.execSQL(sql);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }*/

    public void ClearCookie(Context context) {
        cookiesClear.clearAllCache(context);
    }

    public void Return(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }

}
