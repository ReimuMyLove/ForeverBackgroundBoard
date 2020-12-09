package com.example.shoujiedemo.Log.model;

import android.content.Context;

import com.example.shoujiedemo.Log.presenter.LoginPresenterListener;

public interface LoginModel {
    void login(String name, String password, Context context, LoginPresenterListener listener);
}
