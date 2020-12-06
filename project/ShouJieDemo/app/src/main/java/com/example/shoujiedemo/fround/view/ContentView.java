package com.example.shoujiedemo.fround.view;

import com.example.shoujiedemo.entity.Comment;

import java.util.List;

public interface ContentView {

    void setUnFolly();//取消关注

    void setUnFollyError();//取消关注失败

    void setFollow();//关注

    void setFollowError();//关注失败

    void changeLikeError();//改变点赞失败

    void changeCollectionError();//改变收藏失败

    void report();//举报

    void share();//分享

    void comment();//评论

    void loadComment(List<Comment> commentList);//加载评论
}
