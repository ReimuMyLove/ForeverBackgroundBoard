package com.example.shoujiedemo.myCenter.mySpace.model;

import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenterListener;

public interface MyArticleModel {
    void getGroupDetail(int groupID,int userID, MySpacePresenterListener listener);
}
