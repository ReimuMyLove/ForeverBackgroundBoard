package com.example.shoujiedemo.fround.presenter;

public interface FroundLoadDataPresenterLisenter {

    void onLoadContentDataSuccess(String jsons);

    void onLoadContentDataError();

    void onSearchDataSuccess(String jsons);

    void onSearchDataError();
}
