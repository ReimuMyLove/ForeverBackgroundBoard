package com.example.shoujiedemo.entity;

public class LikeBean {

    private int id;
    private int userid;
    private int tuwenid;
    private int topicdetialid;
    private int musicid;
    private int cheatid;

    public LikeBean(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getTuwenid() {
        return tuwenid;
    }

    public void setTuwenid(int tuwenid) {
        this.tuwenid = tuwenid;
    }

    public int getTopicdetialid() {
        return topicdetialid;
    }

    public void setTopicdetialid(int topicdetialid) {
        this.topicdetialid = topicdetialid;
    }

    public int getMusicid() {
        return musicid;
    }

    public void setMusicid(int musicid) {
        this.musicid = musicid;
    }

    public int getCheatid() {
        return cheatid;
    }

    public void setCheatid(int cheatid) {
        this.cheatid = cheatid;
    }

    @Override
    public String toString() {
        return "LikeBean{" +
                "id=" + id +
                ", userid=" + userid +
                ", tuwenid=" + tuwenid +
                ", topicdetialid=" + topicdetialid +
                ", musicid=" + musicid +
                ", cheatid=" + cheatid +
                '}';
    }
}
