package com.example.shoujiedemo.home.follow.view;

import android.graphics.Bitmap;

import com.example.shoujiedemo.entity.Content;

import java.util.List;

/**
 * 关注文章内容的view接口
 */
public interface FollowView {

    void showContentListData(List<Content> contents);//显示内容列表数据

    void noData();//无数据

}
