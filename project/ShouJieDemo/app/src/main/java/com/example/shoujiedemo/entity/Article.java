package com.example.shoujiedemo.entity;

import android.graphics.Bitmap;

/**
 * 文章实体类继承Content
 */
public class Article{

    private long id;//文章id
    private String cover;//封面图片
    private String writer;//作者
    private String contents;//内容
    private String title;//标题
    private String date;//英文日期


    public Article(){
        //修改父属性Type

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", cover='" + cover + '\'' +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
