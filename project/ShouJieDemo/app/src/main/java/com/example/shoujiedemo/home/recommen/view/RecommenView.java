package com.example.shoujiedemo.home.recommen.view;

import com.example.shoujiedemo.entity.Content;

import java.util.List;

/**
 * 推荐页面的view接口
 */
public interface RecommenView {

    void loadContent(List<Content> contents);

    void loadByTime(List<Content> contents);
}
