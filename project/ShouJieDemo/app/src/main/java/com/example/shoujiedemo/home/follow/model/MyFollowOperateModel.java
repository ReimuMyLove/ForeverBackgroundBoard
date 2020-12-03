package com.example.shoujiedemo.home.follow.model;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperationPresenterListener;

import java.util.List;

/**
 *
 */
public interface MyFollowOperateModel {

    void collect(MyFollowOperationPresenterListener listener);//收藏

    void unCollect(MyFollowOperationPresenterListener listener);//取消收藏

    void comment(MyFollowOperationPresenterListener listener);//评论

    void share(MyFollowOperationPresenterListener listener);//分享

    void favourite(MyFollowOperationPresenterListener listener);//点赞

    void unFavourite(MyFollowOperationPresenterListener listener);//取消点赞

    void unFolly(MyFollowOperationPresenterListener listener);//取消关注

    void report(MyFollowOperationPresenterListener listener);//举报

    void follow(MyFollowOperationPresenterListener listener);//关注

    void loadComment(MyFollowOperationPresenterListener listener);//加载评论
}
