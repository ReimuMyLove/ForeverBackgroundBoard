package com.example.shoujiedemo.home.follow.presenter;

import com.example.shoujiedemo.home.follow.model.MyFollowUserModel;
import com.example.shoujiedemo.home.follow.model.MyFollowUserModelImpl;
import com.example.shoujiedemo.home.follow.view.FollowView;

public class MyFollowUserPresenterImpl implements MyFollowUserPresenter,MyFollowUserPresenterListener{

    private MyFollowUserModel model;
    private FollowView view;

    public MyFollowUserPresenterImpl(FollowView view){
        this.view = view;
        this.model = new MyFollowUserModelImpl();
    }

    /**
     * 验证用户数据加载
     */
    @Override
    public void confirmLoadUserInfo() {

    }

    @Override
    public void onLoadUserInfoSuccess() {

    }
}
