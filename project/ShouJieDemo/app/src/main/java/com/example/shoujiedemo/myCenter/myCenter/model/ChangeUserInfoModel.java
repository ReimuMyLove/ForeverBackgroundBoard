package com.example.shoujiedemo.myCenter.myCenter.model;

import com.example.shoujiedemo.myCenter.myCenter.presenter.UserInfoPresenterListener;

public interface ChangeUserInfoModel {
    void changeInfo(int userID, int userAge, String userSex, String userSign, UserInfoPresenterListener listener);
}
