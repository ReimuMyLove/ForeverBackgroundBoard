package com.example.shoujiedemo.fround.model;

import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenterLisenter;
import com.example.shoujiedemo.fround.presenter.FroundOperationPresenterListener;
import com.example.shoujiedemo.fround.presenter.HotLoadDataPresenterListener;

public interface FroundHotDataModel {

    void loadContents(HotLoadDataPresenterListener listener, int page,int userId);//加载列表内容

    void search(HotLoadDataPresenterListener listener,String flag, int page,int userId);//搜索
}
