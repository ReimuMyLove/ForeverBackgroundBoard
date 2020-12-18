package com.example.shoujiedemo.home.follow.view;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Set;

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

    void comment(Comment comment);//评论

    void collect();//收藏

    void loadComment(List<Comment> commentList);//加载评论

    void deleteComment();//删除评论

    void noSet();//无文集

    void showSet(List<Set> sets);//显示文集

    void deleteContent();

    void deleteError();
}
