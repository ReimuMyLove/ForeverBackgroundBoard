package com.example.shoujiedemo.myCenter.mySpace.view.inter;

import com.example.shoujiedemo.entity.Content;

import java.util.List;

public interface MyArticleView {
    
    void getGroupDetailSuccessful(List<Content> contents);

    void getGroupDetailFailed();
}
