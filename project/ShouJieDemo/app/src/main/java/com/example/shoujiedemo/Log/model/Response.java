package com.example.shoujiedemo.Log.model;


import java.io.Serializable;

public class Response implements Serializable {
    private String loginState;//响应状态码
    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }


    @Override
    public String toString() {
        return "Response{LoginState='"+loginState+'}';
    }
}
