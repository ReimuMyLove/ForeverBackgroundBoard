package com.example.shoujiedemo.home.follow.presenter;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.follow.model.MyFollowAtricleModel;
import com.example.shoujiedemo.home.follow.model.MyFollowAtricleModelImpl;
import com.example.shoujiedemo.home.follow.view.ArticleView;
import com.example.shoujiedemo.home.follow.view.FollowView;
import com.google.gson.Gson;

public class MyFollowAtriclePresenterImpl implements MyFollowAtriclePresenter,MyFollowAtriclePresenterListener{

    private MyFollowAtricleModel model;
    private ArticleView articleView;

    public MyFollowAtriclePresenterImpl(ArticleView articleView){
        this.model = new MyFollowAtricleModelImpl();
        this.articleView = articleView;
    }


    @Override
    public void confirmLoadAtricleContent(int id) {
        model.loadAtricleContent(this,id);
    }

    @Override
    public void onLoadAtricleContentSuccess(String text) {
        Gson gson = new Gson();
        Content article = gson.fromJson(text,Content.class);
        String content = article.getText();
        articleView.loadContent(content);
    }

    @Override
    public void onLoadArticleContentError() {

    }
}
