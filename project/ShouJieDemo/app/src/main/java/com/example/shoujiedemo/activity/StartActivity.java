package com.example.shoujiedemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.shoujiedemo.Log.activity.LoginActivity;
import com.example.shoujiedemo.Log.dao.UserDao;
import com.example.shoujiedemo.Log.database.UserDataBase;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.recommen.activity.MainActivity;
import com.example.shoujiedemo.util.SharedPreUtil;
import com.example.shoujiedemo.util.SystemBarTintManager;
import com.example.shoujiedemo.util.ThemeResUtil;
import com.example.shoujiedemo.util.UserUtil;

import java.util.List;

public class StartActivity extends AppCompatActivity {

    SystemBarTintManager mTintManager;
    private int START_ACTIVITY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTintManager = new SystemBarTintManager(this);

        goHome();

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(START_ACTIVITY == 1) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }.start();

    }

    private void goHome(){
        new Thread(){
            @Override
            public void run() {
                UserDao userDao = UserDataBase.getInstance(getApplicationContext()).getUserDao();
                List<User> userList = userDao.queryUser();
                if(userList.size() != 0){
                    UserUtil.USER_ID = userList.get(0).getId();
                    UserUtil.USER_IMG = userList.get(0).getPicname();
                    UserUtil.USER_AGE = userList.get(0).getAge();
                    UserUtil.USER_FANS = userList.get(0).getFennum();
                    UserUtil.USER_CENTER_BACKGROUND = userList.get(0).getBackgroundpic1();
                    UserUtil.USER_SPACE_BACKGROUND = userList.get(0).getBackgroundpic2();
                    UserUtil.USER_NAME = userList.get(0).getName();
                    UserUtil.USER_SEX = userList.get(0).getSex();
                    UserUtil.USER_SIGN = userList.get(0).getSign();
                    UserUtil.USER_FOLLOW = userList.get(0).getFollownum();
                    START_ACTIVITY = 1;
                }else{
                    START_ACTIVITY = 0;
                }
            }
        }.start();
    }



}
