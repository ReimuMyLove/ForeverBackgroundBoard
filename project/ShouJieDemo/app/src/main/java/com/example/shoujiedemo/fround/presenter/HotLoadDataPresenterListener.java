package com.example.shoujiedemo.fround.presenter;

public interface HotLoadDataPresenterListener {

    void onLoadContentDataSuccess(String jsons);

    void onLoadContentDataError();

    void onSearchDataSuccess(String jsons);

    void onSearchDataError();
}
