package com.example.shoujiedemo.myCenter.mySpace.model;

import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenterListener;

public interface ArticleModel {
    void delete(int groupID, MySpacePresenterListener mySpacePresenterListener);
    void getGroup(int userID, String groupName, MySpacePresenterListener mySpacePresenterListener);
    void getArticles(int userID, MySpacePresenterListener mySpacePresenterListener);
}
