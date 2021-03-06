package com.example.shoujiedemo.entity;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String password;
    private String picname;
    private int age;
    private String sex;
    private String sign;
    private int fennum;
    private int follownum;
    private boolean isFollow;
    private String backgroundpic1;      //个人中心的背景图
    private String backgroundpic2;      //我的空间背景图

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getFennum() {
        return fennum;
    }

    public void setFennum(int fennum) {
        this.fennum = fennum;
    }

    public int getFollownum() {
        return follownum;
    }

    public void setFollownum(int follownum) {
        this.follownum = follownum;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public String getBackgroundpic1() {
        return backgroundpic1;
    }

    public void setBackgroundpic1(String backgroundpic1) {
        this.backgroundpic1 = backgroundpic1;
    }

    public String getBackgroundpic2() {
        return backgroundpic2;
    }

    public void setBackgroundpic2(String backgroundpic2) {
        this.backgroundpic2 = backgroundpic2;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", picname='" + picname + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", sign='" + sign + '\'' +
                ", fennum=" + fennum +
                ", follownum=" + follownum +
                ", isFollow=" + isFollow +
                ", backgroundpic1='" + backgroundpic1 + '\'' +
                ", backgroundpic2='" + backgroundpic2 + '\'' +
                '}';
    }
}
