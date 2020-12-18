package com.example.shoujiedemo.myCenter.myCenter.model;

import com.example.shoujiedemo.myCenter.myCenter.presenter.MyCommentPresenterListener;

public interface MyCommentModel {
    void getCommentList(int userId, MyCommentPresenterListener listener);
}
