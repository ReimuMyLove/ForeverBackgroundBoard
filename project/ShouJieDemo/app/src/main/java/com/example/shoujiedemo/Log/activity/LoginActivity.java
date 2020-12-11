package com.example.shoujiedemo.Log.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.Log.presenter.LogPresenter;
import com.example.shoujiedemo.home.recommen.activity.MainActivity;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.DBHelper;

public class LoginActivity extends BaseActivity implements LoginView {
    EditText
            login_userName,             //登录用户名
            login_userPassword;         //登录密码
    Button
            login_login,                //登录按钮
            login_register;             //注册按钮
    Context context;                    //获取当前上下文
    LogPresenter
            logPresenter;               //获取logPresenter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取view控件
        FindView();
        //设置监听器
        SetListener();
        context = getBaseContext();         //获取当前上下文
        logPresenter = new LogPresenter(this);  //实例化LogPresenter
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
    }

    @Override
    public void MainIntent() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from UserInfo";
        db.execSQL(sql);
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError() {
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
                    String name = login_userName.getText().toString();
                    String password = login_userPassword.getText().toString();
                    logPresenter.confirmLogin(name,password,getBaseContext());
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
}
