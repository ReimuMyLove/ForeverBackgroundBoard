package com.example.shoujiedemo.home.follow.model;

import com.example.shoujiedemo.home.follow.presenter.MyFollowInitPresenterListener;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperationPresenterListener;

public interface MyFollowInitModel {

    void loadContents(MyFollowInitPresenterListener listener,int userId,int pageNum);//加载关注列表内容

}
