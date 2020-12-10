package com.example.shoujiedemo.fround.presenter;

public interface FroundLoadDataPresenter {

    void confirmInitContent(int typeId,int pageNum,int userId);//验证初始化数据

    void searchData(String flag,int typeId,int page,int userId);//搜索
}
