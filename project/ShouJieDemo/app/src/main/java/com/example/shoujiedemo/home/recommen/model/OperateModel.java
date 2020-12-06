package com.example.shoujiedemo.home.recommen.model;

/**
 * 推荐页面model层对推荐的内容各种操作功能接口
 */
public interface OperateModel {

    void collect(long id,String type);//收藏方法

    void favourite(long id,String type);//点赞方法
}
