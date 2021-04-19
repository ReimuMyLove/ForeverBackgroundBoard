package com.example.shoujiedemo.myCenter.myCenter.model;

import com.example.shoujiedemo.myCenter.myCenter.presenter.MyAgreementPresenter;
import com.example.shoujiedemo.myCenter.myCenter.presenter.MyAgreementPresenterListener;

public interface MyAgreementModel {
    void getAgreementList(int userID, MyAgreementPresenterListener listener);
}
