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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", picname='" + picname + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", sign='" + sign + '\'' +
                ", fennum=" + fennum +
                ", follownum=" + follownum +
                '}';
    }
}
