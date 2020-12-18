package com.example.shoujiedemo.myCenter.myCenter.presenter;

public interface MyCommentPresenterListener {
    void getCommentListFailed();

    void getCommentListSuccessful(String jsons);
}
