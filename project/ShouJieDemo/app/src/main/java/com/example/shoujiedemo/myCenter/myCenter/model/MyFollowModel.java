package com.example.shoujiedemo.myCenter.myCenter.model;

import com.example.shoujiedemo.myCenter.myCenter.service.MyFollowPresenterListener;

public interface MyFollowModel {
    void cancelFollower(int userID,int followID, MyFollowPresenterListener listener);

    void findFollower(int userID, MyFollowPresenterListener listener);
}
