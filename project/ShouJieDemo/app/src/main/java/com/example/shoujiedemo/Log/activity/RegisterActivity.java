package com.example.shoujiedemo.Log.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.Log.presenter.LogPresenter;
import com.example.shoujiedemo.util.BaseActivity;

public class RegisterActivity extends BaseActivity implements RegisterView {
    EditText
            register_name,              //注册用户名
            register_password,          //注册密码
            register_repeatPassword;    //重复密码
    Button
            register_register;          //注册按钮
    Context context;                    //上下文
    LogPresenter
            logPresenter;               //获取logPresenter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //获取view控件
        FindView();
        //设置监听器
        SetListener();
        context = getBaseContext();         //获取当前上下文
        logPresenter = new LogPresenter(this);
    }

    /**
     * 设置监听器方法
     */
    public void SetListener(){
        MyListener listener = new MyListener();
        register_register.setOnClickListener(listener);
    }

    /**
     * 获取view方法
     */
    public void FindView() {
        register_register = findViewById(R.id.register_register);
        register_name = findViewById(R.id.register_name);
        register_password = findViewById(R.id.register_password);
        register_repeatPassword = findViewById(R.id.register_repeatPassword);
    }

    @Override
    public void LoginIntent() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void passwordError() {
        Toast.makeText(context, "两次密码有误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nameRepeat() {
        Toast.makeText(context, "已有相同用户名", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void systemERROR() {
        Toast.makeText(context, "系统错误，请重试", Toast.LENGTH_SHORT).show();
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.register_register) {
                String name = register_name.getText().toString();
                String password = register_password.getText().toString();
                String repeatPassword = register_repeatPassword.getText().toString();
                if(name.equals("") || password.equals("") || repeatPassword.equals("")){
                    Toast.makeText(context, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    logPresenter.confirmRegister(name,password,repeatPassword);
                }
            }
        }
    }
}
