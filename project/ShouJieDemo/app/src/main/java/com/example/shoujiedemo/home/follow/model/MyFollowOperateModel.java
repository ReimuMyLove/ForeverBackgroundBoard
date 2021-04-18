package com.example.shoujiedemo.home.follow.model;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperationPresenterListener;

import java.util.List;

/**
 *
 */
public interface MyFollowOperateModel {

    void collect(MyFollowOperationPresenterListener listener,int userId,int contentId);//收藏

    void unCollect(MyFollowOperationPresenterListener listener,int userId,int contentId);//取消收藏

    void comment(MyFollowOperationPresenterListener listener,int userId,int contentId,String text) ;//评论

    void share(MyFollowOperationPresenterListener listener,int userId,int contentId);//分享

    void favourite(MyFollowOperationPresenterListener listener,int userId,int contentId);//点赞

    void unFavourite(MyFollowOperationPresenterListener listener,int userId,int contentId);//取消点赞

    void unFolly(MyFollowOperationPresenterListener listener,int userId,int contentId);//取消关注

    void report(MyFollowOperationPresenterListener listener,int userId,int contentId);//举报

    void follow(MyFollowOperationPresenterListener listener,int userId,int followerId);//关注

    void loadComment(MyFollowOperationPresenterListener listener,int contentId,int pageNum);//加载评论

    void loadSet(MyFollowOperationPresenterListener listener,int userId);//加载文集

    void deleteComment(MyFollowOperationPresenterListener listener,int commentId);//删除评论
}
