package com.example.shoujiedemo.home.recommen.presenter;

public interface ItemPresenterListener {

    void loadContentDetailsSuccess(String jsons);//加载详情成功

    void loadContentDetailsError();//加载详情失败
}
