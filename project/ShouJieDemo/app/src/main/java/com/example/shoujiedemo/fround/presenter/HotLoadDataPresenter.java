package com.example.shoujiedemo.fround.presenter;

public interface HotLoadDataPresenter {

    void confirmInitContent(int pageNum,int userId);//验证初始化数据

    void searchData(String flag, int page,int userId);//搜索
}
