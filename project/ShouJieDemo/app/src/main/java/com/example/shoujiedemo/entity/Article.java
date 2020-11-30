package com.example.shoujiedemo.entity;

import android.graphics.Bitmap;

/**
 * 文章实体类继承Content
 */
public class Article{

    private long id;//文章id
    private Bitmap bitmap;//封面图片
    private String writer;//作者
    private String contents;//内容
    private String title;//标题
    private String tag;//标签
    private int likeNum;//点赞数
    private int commentsNum;//评论数
    private int collectionNum;//收藏数


    public Article(){
        //修改父属性Type

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", bitmap=" + bitmap +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", likeNum=" + likeNum +
                ", commentsNum=" + commentsNum +
                ", collectionNum=" + collectionNum +
                '}';
    }
}
