package com.example.shoujiedemo.myCenter.setting.model;

import com.example.shoujiedemo.myCenter.setting.presenter.impl.SettingPresenter;
import com.example.shoujiedemo.myCenter.setting.presenter.impl.SettingPresenterListener;

public interface ChangePasswordModel {
    void changeKey(int userID,String oldKey, String newKey, SettingPresenterListener listener);

    void destroyAccountEnter(int userID, SettingPresenterListener listener);
}
