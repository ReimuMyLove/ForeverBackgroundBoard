package com.example.shoujiedemo.home.follow.model;

import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenterListener;

/**
 * 关注列表内容文章类型的model接口
 */
public interface MyFollowAtricleModel {

    void loadAtricleContent(MyFollowAtriclePresenterListener listener,int id);//加载文章

}
