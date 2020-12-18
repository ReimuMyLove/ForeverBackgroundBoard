package com.example.shoujiedemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.shoujiedemo.Log.activity.LoginActivity;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.home.recommen.activity.MainActivity;

public class StartActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MILLIS = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
