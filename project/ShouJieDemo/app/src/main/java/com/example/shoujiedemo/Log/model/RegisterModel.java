package com.example.shoujiedemo.Log.model;

import com.example.shoujiedemo.Log.presenter.LoginPresenterListener;
import com.example.shoujiedemo.Log.presenter.RegisterPresenterListener;

public interface RegisterModel {
    void register(String name, String password, RegisterPresenterListener listener);
}
