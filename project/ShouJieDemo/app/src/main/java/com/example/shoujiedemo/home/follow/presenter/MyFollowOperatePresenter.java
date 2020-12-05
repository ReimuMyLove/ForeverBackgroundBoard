package com.example.shoujiedemo.home.follow.presenter;

public interface MyFollowOperatePresenter {

    void confirmFavourite();//点赞

    void confirmUnFavourite();//取消点赞

    void confirmShare();//分享

    void confirmCollect();//收藏

    void confirmUnCollect();//取消收藏

    void confirmUnFolly();//取消关注

    void confirmFollow();//关注

    void confirmReport();//举报

    void confirmComment();//评论

    void loadComment();//加载评论

    void loadSet();//加载文集
}
