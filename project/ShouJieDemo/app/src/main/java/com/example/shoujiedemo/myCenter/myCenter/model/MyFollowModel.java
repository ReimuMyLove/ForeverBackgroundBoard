package com.example.shoujiedemo.myCenter.myCenter.model;

import com.example.shoujiedemo.myCenter.myCenter.presenter.MyFollowPresenterListener;

public interface MyFollowModel {
    void cancelFollower(int userID,int followID, MyFollowPresenterListener listener);

    void findFollower(int userID, MyFollowPresenterListener listener);

    void addFollower(int userID,final int followerID, final MyFollowPresenterListener listener);
}
