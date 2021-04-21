package com.example.shoujiedemo.Log.presenter;

import android.content.Context;

import com.example.shoujiedemo.Log.activity.LoginView;
import com.example.shoujiedemo.Log.activity.RegisterView;
import com.example.shoujiedemo.Log.model.LoginModel;
import com.example.shoujiedemo.Log.model.LoginModelImpl;
import com.example.shoujiedemo.Log.model.RegisterModel;
import com.example.shoujiedemo.Log.model.RegisterModelImpl;

public class LogPresenter implements LoginPresenterListener,LoginInterface, RegisterInterface,RegisterPresenterListener {
    private LoginModel loginModel;
    private LoginView loginView;
    private RegisterModel registerModel;
    private RegisterView registerView;
    /**
     * 构造方法
     */
    public LogPresenter(LoginView loginView) {
        this.loginModel = new LoginModelImpl();
        this.loginView = loginView;
    }

    public LogPresenter(RegisterView registerView){
        this.registerModel = new RegisterModelImpl();
        this.registerView = registerView;
    }

    /**
     * 逻辑实现方法
     */

    //登录校验方法
    @Override
    public void confirmLogin(String name, String password, Context context) {

        loginModel.login(name,password,context,this);
    }

    //登录成功返回方法
    @Override
    public void OnLoginSuccessful() {
        loginView.MainIntent();
    }
    //登录失败返回方法
    @Override
    public void OnLoginFailed() {
        loginView.loginError();
    }

    //注册校验方法
    @Override
    public void confirmRegister(String name, String password, String repeatPassword) {
        if (password.equals(repeatPassword)){
            registerModel.register(name,password,this);
        }else{
            registerView.passwordError();
        }
    }

    //注册成功返回方法
    @Override
    public void OnRegisterSuccessful() {
        registerView.LoginIntent();
    }
    //注册失败
    @Override
    public void OnRegisterFailed() {
        registerView.systemERROR();
    }
    //重名
    @Override
    public void OnRegisterERROR() {
        registerView.nameRepeat();
    }
}
