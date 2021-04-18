package com.example.shoujiedemo.myCenter.myCenter.service;

public interface MyFollowPresenterListener {

    void cancelFailed();

    void cancelSuccessful(int followerID);

    void findSuccessful(String jsons);

    void findFailed();
}
