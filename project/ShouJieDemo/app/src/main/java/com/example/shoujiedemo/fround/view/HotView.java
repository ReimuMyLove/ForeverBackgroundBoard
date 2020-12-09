package com.example.shoujiedemo.fround.view;

import com.example.shoujiedemo.entity.Content;

import java.util.List;

public interface HotView {

    void showContentListData(List<Content> contents);//显示内容列表数据

    void showSearchList(List<Content> contents,String flag);//显示搜索数据

    void noData();//无数据
}

