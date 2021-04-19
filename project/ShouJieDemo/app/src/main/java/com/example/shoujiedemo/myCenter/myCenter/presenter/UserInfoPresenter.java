package com.example.shoujiedemo.myCenter.myCenter.presenter;

import com.example.shoujiedemo.myCenter.myCenter.model.ChangeUserInfoModel;
import com.example.shoujiedemo.myCenter.myCenter.model.ChangeUserInfoModelImpl;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.ChangeUserInfoView;

public class UserInfoPresenter implements UserInfoPresenterListener{
    private ChangeUserInfoView changeUserInfo;
    private ChangeUserInfoModel changeUserInfoModelImpl;

    public UserInfoPresenter(ChangeUserInfoView changeUserInfo){
        this.changeUserInfo = changeUserInfo;
        changeUserInfoModelImpl = new ChangeUserInfoModelImpl();
    }


    /**
     * 修改个人信息方法
     */
    public void changeInfo(int userID, int userAge, String userSex, String userSign) {
        changeUserInfoModelImpl.changeInfo(userID,userAge,userSex,userSign,this);
    }

    @Override
    public void changeInfoFailed() {
        changeUserInfo.changeInfoFailed();
    }

    @Override
    public void changeInfoSuccessful() {
        changeUserInfo.changeInfoSuccessful();
    }
}
