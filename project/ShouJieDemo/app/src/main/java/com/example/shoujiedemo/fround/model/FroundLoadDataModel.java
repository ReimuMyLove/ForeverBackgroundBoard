package com.example.shoujiedemo.fround.model;


import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenterLisenter;
import com.example.shoujiedemo.fround.presenter.FroundOperationPresenterListener;

public interface FroundLoadDataModel {

    void loadContents(FroundLoadDataPresenterLisenter listener,int typeId,int page,int userId);//加载列表内容

    void search(FroundLoadDataPresenterLisenter listener,String flag,int typeid, int page,int userId);//搜索
}
