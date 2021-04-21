package com.example.shoujiedemo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey
    private int id;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="password")
    private String password;
    @ColumnInfo(name="picname")
    private String picname;
    @ColumnInfo(name="age")
    private int age;
    @ColumnInfo(name="sex")
    private String sex;
    @ColumnInfo(name="sign")
    private String sign;
    @ColumnInfo(name="fennum")
    private int fennum;
    @ColumnInfo(name="follownum")
    private int follownum;
    @ColumnInfo(name="is_follw")
    private boolean isFollow;
    @ColumnInfo(name="backgroundpic1")
    private String backgroundpic1;      //个人中心的背景图
    @ColumnInfo(name="backgroundpic2")
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
