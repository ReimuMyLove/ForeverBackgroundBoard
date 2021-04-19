package com.example.shoujiedemo.myCenter.myCenter.view.inter;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;

import java.util.List;

public interface MyCommentView {
    void getCommentListFailed();

    void getCommentListSuccessful(
            List<Comment> commentList,
            List<Content> writerArticleList,
            List<User> userList
    );
}
