package com.example.shoujiedemo.message.presenter;

public interface GetFollowPresenterListener {
    void LoadSuccess(String json);
    void LoadFail(String Error);
}
