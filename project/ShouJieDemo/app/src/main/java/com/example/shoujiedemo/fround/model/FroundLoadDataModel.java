package com.example.shoujiedemo.fround.model;


import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenterLisenter;

public interface FroundLoadDataModel {

    void loadContents(FroundLoadDataPresenterLisenter listener,int typeId,int page);//加载列表内容
}
