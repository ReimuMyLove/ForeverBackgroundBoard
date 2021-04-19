package com.example.shoujiedemo.entity;

import java.io.Serializable;

public class Music implements Serializable {

    private int id;//音乐id
    private String pic;//封面图片
    private int userid;//用户id
    private String singer;//歌手名字
    private String name;//标题
    private int likes;//点赞数
    private int cheatnum;//评论数
    private int collectnum;//收藏数
    private String time;//发布时间
    private int currenttime;//当前播放时间
    private int totaltime;//歌曲时长
    private String text;//音乐文案
    private String path;//网络播放路径
    private int start = 0;

    private User user;//用户
    private boolean isLike = false;//点赞
    private boolean isCollect = false;//收藏
    private boolean isPause = true;//暂停
    private boolean isStop = true;//停止
    private int tag;// 0:viewHodler通知Service
                    //1:Service通知其他组件

    public Music(){
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCheatnum() {
        return cheatnum;
    }

    public void setCheatnum(int cheatnum) {
        this.cheatnum = cheatnum;
    }

    public int getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(int collectnum) {
        this.collectnum = collectnum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(int currenttime) {
        this.currenttime = currenttime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public int getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(int totaltime) {
        this.totaltime = totaltime;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                ", userid=" + userid +
                ", singer='" + singer + '\'' +
                ", name='" + name + '\'' +
                ", likes=" + likes +
                ", cheatnum=" + cheatnum +
                ", collectnum=" + collectnum +
                ", time='" + time + '\'' +
                ", currenttime=" + currenttime +
                ", totaltime=" + totaltime +
                ", text='" + text + '\'' +
                ", path='" + path + '\'' +
                ", start=" + start +
                ", user=" + user +
                ", isLike=" + isLike +
                ", isCollect=" + isCollect +
                ", isPause=" + isPause +
                ", isStop=" + isStop +
                ", tag=" + tag +
                '}';
    }
}
