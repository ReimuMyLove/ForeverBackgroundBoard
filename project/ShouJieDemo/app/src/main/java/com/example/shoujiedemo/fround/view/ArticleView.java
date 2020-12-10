package com.example.shoujiedemo.fround.view;

import com.example.shoujiedemo.entity.Content;


import java.util.List;

public interface ArticleView {

    void showContentListData(List<Content> articles);//显示内容列表数据

    void showSearchList(List<Content> contents);//显示搜索数据

    void noData();//无数据
}
