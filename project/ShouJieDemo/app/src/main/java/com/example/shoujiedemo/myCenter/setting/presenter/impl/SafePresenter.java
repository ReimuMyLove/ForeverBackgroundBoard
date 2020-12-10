package com.example.shoujiedemo.myCenter.setting.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.shoujiedemo.myCenter.setting.view.activity.SettingActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.ChangePasswordActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.DestroyActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.SetEmailActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.SetSafeQuestionActivity;

public class SafePresenter {
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

    /* 修改密码 */
    public void ChangePassword(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }

    /* 设置安全问题 */
    public void SetSafeQuestion(Context context) {
        Intent intent = new Intent(context, SetSafeQuestionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }

    /* 设置邮箱 */
    public void SetEmail(Context context) {
        Intent intent = new Intent(context, SetEmailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }

    /* 销毁账户 */
    public void DestroyAccount(Context context) {
        Intent intent = new Intent(context, DestroyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }
}
