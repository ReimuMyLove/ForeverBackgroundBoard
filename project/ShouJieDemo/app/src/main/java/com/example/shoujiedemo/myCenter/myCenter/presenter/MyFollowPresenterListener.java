package com.example.shoujiedemo.myCenter.myCenter.presenter;

public interface MyFollowPresenterListener {

    void cancelFailed();

    void cancelSuccessful(int followerID);

    void findSuccessful(String jsons);

    void findFailed();

    void addSuccessful(int followerID);

    void addFailed();
}
