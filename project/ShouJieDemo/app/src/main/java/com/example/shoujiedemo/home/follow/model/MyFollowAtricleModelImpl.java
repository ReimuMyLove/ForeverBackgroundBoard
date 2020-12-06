package com.example.shoujiedemo.home.follow.model;

import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenterImpl;
import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenterListener;

public class MyFollowAtricleModelImpl implements MyFollowAtricleModel{


    @Override
    public void loadAtricleCover(MyFollowAtriclePresenterListener listener) {
        listener.onLoadAtricleCoverSuccess();
    }

}
