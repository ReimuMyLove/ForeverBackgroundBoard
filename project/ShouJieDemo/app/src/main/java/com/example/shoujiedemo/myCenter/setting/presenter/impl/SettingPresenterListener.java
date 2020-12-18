package com.example.shoujiedemo.myCenter.setting.presenter.impl;

public interface SettingPresenterListener {
    void changeKeySuccessful();

    void changeKeyFailed();

    void changeKeyError();

    void destroyAccountSuccessful();

    void destroyAccountFailed();
}
