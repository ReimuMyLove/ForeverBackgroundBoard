package com.example.shoujiedemo.fround.model;


import com.example.shoujiedemo.fround.presenter.FroundOperationPresenterListener;

/**
 *
 */
public interface FroundOperateModel {

    void collect(FroundOperationPresenterListener listener);//收藏

    void unCollect(FroundOperationPresenterListener listener);//取消收藏

    void comment(FroundOperationPresenterListener listener);//评论

    void share(FroundOperationPresenterListener listener);//分享

    void favourite(FroundOperationPresenterListener listener);//点赞

    void unFavourite(FroundOperationPresenterListener listener);//取消点赞

    void unFolly(FroundOperationPresenterListener listener);//取消关注

    void report(FroundOperationPresenterListener listener);//举报

    void follow(FroundOperationPresenterListener listener);//关注

    void loadComment(FroundOperationPresenterListener listener);//加载评论


}
