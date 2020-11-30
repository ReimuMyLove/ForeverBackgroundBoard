package com.example.shoujiedemo.home.follow.model;

import com.example.shoujiedemo.home.follow.presenter.MyFollowOperationPresenterListener;


import java.util.List;

public class MyFollowOperateModelImpl implements MyFollowOperateModel {


    public MyFollowOperateModelImpl() {

    }

    @Override
    public void collect(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n>0)
            listener.onCollectSuccess();
        else
            listener.onCollectError();
    }

    @Override
    public void unCollect(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n>0)
            listener.onUnCollectSuccess();
        else
            listener.onUnCollectError();
    }

    @Override
    public void comment(MyFollowOperationPresenterListener listener) {
        listener.onCommentSuccess();
    }

    @Override
    public void share(MyFollowOperationPresenterListener listener) {

    }

    @Override
    public void favourite(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onFavouriteSuccess();
        else
            listener.onFavouriteError();
    }

    @Override
    public void unFavourite(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onUnFavouriteSuccess();
        else
            listener.onUnFavouriteError();

    }

    @Override
    public void unFolly(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onUnFollySuccess();
        else
            listener.onUnFollyError();
    }

    @Override
    public void report(MyFollowOperationPresenterListener listener) {
        listener.onReportSuccess();
    }

    @Override
    public void follow(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onFollowSuccess();
        else
            listener.onFollowError();
    }
}
