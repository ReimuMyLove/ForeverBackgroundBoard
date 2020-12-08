package com.example.shoujiedemo.fround.model;

import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenterLisenter;
import com.example.shoujiedemo.fround.presenter.HotLoadDataPresenterListener;

public interface FroundHotDataModel {

    void loadContents(HotLoadDataPresenterListener listener, int page);//加载列表内容

    void loadContentBySearch(FroundLoadDataPresenterLisenter listener, String flag, int page);//加载搜索内容
}
