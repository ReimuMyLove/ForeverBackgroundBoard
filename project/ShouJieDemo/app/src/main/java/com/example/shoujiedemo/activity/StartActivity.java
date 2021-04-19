package com.example.shoujiedemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.shoujiedemo.Log.activity.LoginActivity;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.home.recommen.activity.MainActivity;
import com.example.shoujiedemo.util.SharedPreUtil;
import com.example.shoujiedemo.util.SystemBarTintManager;
import com.example.shoujiedemo.util.ThemeResUtil;

public class StartActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MILLIS = 1500;
    boolean currentDarkModel = false; //当前是否为夜间模式
    SystemBarTintManager mTintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreUtil.getInstance().load();
        if(SharedPreUtil.getInstance().darkmodel) {
            setTheme(R.style.AppThemeDark);
            ThemeResUtil.setModel(true); // APP首页才需要这句，其它跳转activity不需要再次设置
        }
        else {
            setTheme(R.style.TranslucentTheme);
            ThemeResUtil.setModel(false); // APP首页才需要这句，其它跳转activity不需要再次设置
        }

        currentDarkModel = SharedPreUtil.getInstance().darkmodel;

        mTintManager = new SystemBarTintManager(this);


// 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    private void goHome() {
        Intent intent=null;
        intent = new Intent(StartActivity.this, LoginActivity.class);
        StartActivity.this.startActivity(intent);
        StartActivity.this.finish();
    }
}
