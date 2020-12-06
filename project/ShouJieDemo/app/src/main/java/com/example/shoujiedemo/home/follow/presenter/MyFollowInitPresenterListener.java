package com.example.shoujiedemo.home.follow.presenter;

import android.graphics.Bitmap;

import com.example.shoujiedemo.entity.Content;

import java.util.List;

public interface MyFollowInitPresenterListener {

    void onLoadContentDataSuccess(String jsons);

    void onLoadContentDataError();
}
