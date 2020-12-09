package com.example.shoujiedemo.entity;

public class Set {
    private int tuwen_num;
    private String name;
    private int id;
    private String pic;
    private int userid;

    public Set() {
    }

    public int getTuwen_num() {
        return tuwen_num;
    }

    public void setTuwen_num(int tuwen_num) {
        this.tuwen_num = tuwen_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Set{" +
                "tuwen_num=" + tuwen_num +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", pic='" + pic + '\'' +
                ", userid=" + userid +
                '}';
    }
}
