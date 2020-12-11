package com.example.shoujiedemo.myCenter.mySpace.model;

import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenterListener;

public interface ArticleModel {
    void delete(int groupID, MySpacePresenterListener listener);

    void getGroup(int userID,String groupName,MySpacePresenterListener listener);

    void getArticles(int userID, MySpacePresenterListener listener);

    void addFollow(int followID, int userID, MySpacePresenterListener listener);

    void getOwnerInfo(int ownerID, MySpacePresenterListener listener);
}
