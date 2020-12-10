package com.example.shoujiedemo.myCenter.setting.presenter.impl;

import android.content.Context;
import android.content.Intent;

import com.example.shoujiedemo.myCenter.setting.view.activity.SettingActivity;

public class AboutPresenter {
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
    /* 返回设置页面 */
    public void Return(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }
}
