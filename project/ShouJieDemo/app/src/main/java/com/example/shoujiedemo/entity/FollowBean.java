package com.example.shoujiedemo.entity;

public class FollowBean {

    private int userid;
    private int followerid;
    private String time;
    private User user;

    public FollowBean() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getFollowerid() {
        return followerid;
    }

    public void setFollowerid(int followerid) {
        this.followerid = followerid;
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

    @Override
    public String toString() {
        return "FollowBean{" +
                "userid=" + userid +
                ", followerid=" + followerid +
                ", time='" + time + '\'' +
                '}';
    }
}
