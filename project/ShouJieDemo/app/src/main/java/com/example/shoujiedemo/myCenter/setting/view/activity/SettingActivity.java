package com.example.shoujiedemo.myCenter.setting.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.myCenter.setting.presenter.impl.SettingPresenter;
import com.example.shoujiedemo.myCenter.setting.service.CookiesClear;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.AboutActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.FeedbackViewActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.SafeActivity;
import com.example.shoujiedemo.myCenter.setting.view.inter.SettingView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.SwitchButton;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

public class SettingActivity extends BaseActivity implements SettingView{
    View
            myCenter_setting_safe,            //账户与安全
            myCenter_setting_clearCookie,     //清理缓存
            myCenter_setting_feedback,        //帮助与反馈
            myCenter_setting_about;           //关于
    Button
            myCenter_setting_logout,          //退出登录
            myCenter_setting_return;          //返回个人中心页面
    TextView
            myCenter_setting_cookieSize;      //缓存数据大小
    SwitchButton
            myCenter_setting_nightModeSwitch; //夜间模式开关
    Context context;                          //获取当前上下文
    SettingPresenter settingPresenter;        //获取SettingPresenter
    CookiesClear
            cookiesClear = new CookiesClear();//获取CookieClear数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this.getBaseContext();
        //获取控件
        FindView();
        //绑定监听器
        SetListener();
        //绑定Presenter
        settingPresenter = new SettingPresenter();
        //计算当前Cookie大小并显示在页面中
        GetCookieSize();
    }

    /**
     * 获取view方法
     */
    @Override
    public void FindView() {
        myCenter_setting_safe = findViewById(R.id.myCenter_setting_safe);
        myCenter_setting_clearCookie = findViewById(R.id.myCenter_setting_clearCookie);
        myCenter_setting_feedback = findViewById(R.id.myCenter_setting_feedback);
        myCenter_setting_about = findViewById(R.id.myCenter_setting_about);
        myCenter_setting_nightModeSwitch = findViewById(R.id.myCenter_setting_nightModeSwitch);
        myCenter_setting_logout = findViewById(R.id.myCenter_setting_logout);
        myCenter_setting_cookieSize = findViewById(R.id.myCenter_setting_cookieSize);
        myCenter_setting_return = findViewById(R.id.myCenter_setting_return);
    }

    /**
     * 设置监听器方法
     */
    @Override
    public void SetListener() {
        MyListener listener = new MyListener();
        myCenter_setting_safe.setOnClickListener(listener);
        myCenter_setting_clearCookie.setOnClickListener(listener);
        myCenter_setting_feedback.setOnClickListener(listener);
        myCenter_setting_about.setOnClickListener(listener);
        myCenter_setting_logout.setOnClickListener(listener);
        myCenter_setting_return.setOnClickListener(listener);
    }

    /**
     * 改变系统主题(夜间模式与日间模式)
     */
    @Override
    public void NightModeChange() {
        if(myCenter_setting_nightModeSwitch.isChecked()){
            setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        myCenter_setting_nightModeSwitch.startAnimate();
    }

    /**
     * 计算并展示当前缓存大小
     */
    @Override
    public void GetCookieSize() {
        try {
            String size = cookiesClear.getTotalCacheSize(context);
            myCenter_setting_cookieSize.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.myCenter_setting_nightModeSwitch:
                    NightModeChange();
                    break;
                case R.id.myCenter_setting_safe:
                    Safe();
                    break;
                case R.id.myCenter_setting_clearCookie:
                    settingPresenter.ClearCookie(context);
                    Toast.makeText(context,"清理缓存成功",Toast.LENGTH_SHORT);
                    GetCookieSize();
                    break;
                case R.id.myCenter_setting_feedback:
                    Feedback();
                    break;
                case R.id.myCenter_setting_about:
                    About();
                    break;
                case R.id.myCenter_setting_logout:
                   // settingPresenter.Logout(context);
                    break;
                case R.id.myCenter_setting_return:
                    onBackPressed();
                    break;
            }
        }
    }

    public void About() {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }

    public void Safe() {
        Intent intent = new Intent(this, SafeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }

    public void Feedback() {
        Intent intent = new Intent(this, FeedbackViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }
}