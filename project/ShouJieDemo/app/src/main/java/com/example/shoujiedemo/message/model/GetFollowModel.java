package com.example.shoujiedemo.message.model;

import com.example.shoujiedemo.message.presenter.GetFollowPresenterListener;
import com.example.shoujiedemo.message.presenter.GetmessagePresenterListener;

public interface GetFollowModel  {
    void getFollow(GetFollowPresenterListener listener, int userid);
}
