package com.example.shoujiedemo.myCenter.myCenter.view.inter;

public interface MyFollowAdapterView {

    void cancelFailed();

    void cancelSuccessful(int followerID);

    void addSuccessful(int followerID);

    void addFailed();
}
