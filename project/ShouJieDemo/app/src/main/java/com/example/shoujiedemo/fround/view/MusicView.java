package com.example.shoujiedemo.fround.view;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Music;

import java.util.List;

public interface MusicView {

    void showContentListData(List<Music> musicList);//显示内容列表数据

    void showSearchList(List<Music> musicList);//显示搜索数据

    void noData();//无数据
}
