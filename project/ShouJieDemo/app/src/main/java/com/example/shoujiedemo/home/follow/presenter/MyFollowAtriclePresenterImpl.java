package com.example.shoujiedemo.home.follow.presenter;

import com.example.shoujiedemo.home.follow.model.MyFollowAtricleModel;
import com.example.shoujiedemo.home.follow.model.MyFollowAtricleModelImpl;
import com.example.shoujiedemo.home.follow.view.ArticleView;
import com.example.shoujiedemo.home.follow.view.FollowView;

public class MyFollowAtriclePresenterImpl implements MyFollowAtriclePresenter,MyFollowAtriclePresenterListener{

    private MyFollowAtricleModel model;
    private ArticleView articleView;

    public MyFollowAtriclePresenterImpl(ArticleView articleView){
        this.model = new MyFollowAtricleModelImpl();
        this.articleView = articleView;
    }


    /**
     * 加载文章封面成功
     */
    @Override
    public void onLoadAtricleCoverSuccess() {
        articleView.showImgCover();//显示文章封面
    }



    /**
     * 加载文章封面
     */
    @Override
    public void confirmLoadAtricleCover() {
        model.loadAtricleCover(this);
    }
}
