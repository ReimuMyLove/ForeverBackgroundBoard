package com.example.shoujiedemo.myCenter.myCenter.presenter;

import com.google.gson.JsonObject;

public interface MyAgreementPresenterListener {
    void getAgreementListSuccessful(String jsons);
    void getAgreementListFailed();
}
