package com.example.shoujiedemo.fround.view;

import com.example.shoujiedemo.entity.Article;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Heart;

import java.util.List;

public interface HeartView {

    void showContentListData(List<Content> hearts);//显示内容列表数据

    void onData();//无数据
}
