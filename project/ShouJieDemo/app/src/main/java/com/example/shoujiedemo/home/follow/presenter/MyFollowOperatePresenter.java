package com.example.shoujiedemo.home.follow.presenter;

public interface MyFollowOperatePresenter {

    void confirmFavourite(int userId,int contentId);//点赞

    void confirmUnFavourite(int userId,int contentId);//取消点赞

    void confirmShare(int userId,int contentId);//分享

    void confirmCollect(int userId,int contentId);//收藏

    void confirmUnCollect(int userId,int contentId);//取消收藏

    void confirmUnFolly(int userId,int followerId);//取消关注

    void confirmFollow(int userId,int followerId);//关注

    void confirmReport(int userId,int contentId);//举报

    void confirmComment(int userId,int contentId);//评论

    void loadComment(int contentId);//加载评论

    void loadSet(int userId);//加载文集
}
