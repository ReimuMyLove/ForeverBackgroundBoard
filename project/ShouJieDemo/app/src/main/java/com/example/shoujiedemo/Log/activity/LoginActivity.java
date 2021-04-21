package com.example.shoujiedemo.Log.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoujiedemo.Log.dao.UserDao;
import com.example.shoujiedemo.Log.database.UserDataBase;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.Log.presenter.LogPresenter;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.recommen.activity.MainActivity;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.DBHelper;
import com.example.shoujiedemo.util.UserUtil;

import java.util.List;

public class LoginActivity extends BaseActivity implements LoginView {
    private EditText
            login_userName,             //登录用户名
            login_userPassword;         //登录密码
    private Button
            login_login,                //登录按钮
            login_register;             //注册按钮
    private Context context;            //获取当前上下文
    private ImageView
            login_animation;            //转圈图
    Animation animation;

    LogPresenter
            logPresenter;               //获取logPresenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //isLogin();
        //获取view控件
        FindView();
        //设置监听器
        SetListener();
        context = LoginActivity.this;         //获取当前上下文
        logPresenter = new LogPresenter(LoginActivity.this);  //实例化LogPresenter
    }

    /**
     * 设置监听器方法
     */
    public void SetListener(){
        MyListener listener = new MyListener();
        login_login.setOnClickListener(listener);
        login_register.setOnClickListener(listener);
    }

    /**
     * 获取view方法
     */
    public void FindView() {
        login_userName = findViewById(R.id.login_userName);
        login_userPassword = findViewById(R.id.login_userPassword);
        login_login = findViewById(R.id.login_login);
        login_register = findViewById(R.id.login_register);
        login_animation = findViewById(R.id.login_animation);
    }

    @Override
    public void MainIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError() {
        login_animation.clearAnimation();
        login_animation.setVisibility(View.INVISIBLE);
        login_login.setText("登录");
        Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_login:
                    confirmLogin();
                    break;
                case R.id.login_register:
                    Register();
                    break;
            }
        }
    }

    /**
     * Register注册方法
     */
    private void Register(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void confirmLogin(){
        login_animation.setVisibility(View.VISIBLE);
        login_login.setText("");
        //动画
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading_music_anim_rotate);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        login_animation.startAnimation(animation);
        String name = login_userName.getText().toString();
        String password = login_userPassword.getText().toString();
        logPresenter.confirmLogin(name,password,getBaseContext());
    }

}
