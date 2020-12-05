package com.example.shoujiedemo.fround.view;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Poem;

import java.util.List;

public interface PoemView {

    void showContentListData(List<Content> poems);//显示内容列表数据

    void noData();//无数据
}
