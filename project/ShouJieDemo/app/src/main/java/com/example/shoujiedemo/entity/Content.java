package com.example.shoujiedemo.entity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * 内容抽象父类
 */
public class Content implements Serializable {

    private int id;//内容id
    private String pic;//封面图片
    private int userid;//用户id
    private String writer;//作者
    private String text;//内容
    private String title;//标题
    private String tag;//标签
    private int likes;//点赞数
    private int isoriginal;//是否原创
    private int cheatnum;//评论数
    private int collectnum;//收藏数
    private int forwardnum;//分享数
    private int typeid;//类型
    private String time;//发布时间
    private User user;//用户
    private boolean isLike = false;//点赞
    private boolean isCollect = false;//收藏
    private String dateEnglish;//英文日期
    private String day;

    public Content(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getIsoriginal() {
        return isoriginal;
    }

    public void setIsoriginal(int isoriginal) {
        this.isoriginal = isoriginal;
    }

    public int getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(int collectnum) {
        this.collectnum = collectnum;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCheatnum() {
        return cheatnum;
    }

    public void setCheatnum(int cheatnum) {
        this.cheatnum = cheatnum;
    }

    public int getForwardnum() {
        return forwardnum;
    }

    public void setForwardnum(int forwardnum) {
        this.forwardnum = forwardnum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getDateEnglish() {
        return dateEnglish;
    }

    public void setDateEnglish(String dateEnglish) {
        this.dateEnglish = dateEnglish;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                ", userid=" + userid +
                ", writer='" + writer + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", likes=" + likes +
                ", isoriginal=" + isoriginal +
                ", cheatnum=" + cheatnum +
                ", collectnum=" + collectnum +
                ", forwardnum=" + forwardnum +
                ", typeid=" + typeid +
                ", time='" + time + '\'' +
                ", user=" + user +
                ", isLike=" + isLike +
                ", isCollect=" + isCollect +
                '}';
    }
}
