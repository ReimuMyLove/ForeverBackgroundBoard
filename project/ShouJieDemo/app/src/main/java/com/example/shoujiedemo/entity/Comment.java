package com.example.shoujiedemo.entity;

public class Comment {

    private int id;
    private int topic_detial_id;
    private int tu_id;
    private int user1id;
    private int user2id;
    private String text;
    private String time;
    private User user;
    private Content content;

    public Comment(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic_detial_id() {
        return topic_detial_id;
    }

    public void setTopic_detial_id(int topic_detial_id) {
        this.topic_detial_id = topic_detial_id;
    }

    public int getTu_id() {
        return tu_id;
    }

    public void setTu_id(int tu_id) {
        this.tu_id = tu_id;
    }

    public int getUser1id() {
        return user1id;
    }

    public void setUser1id(int user1id) {
        this.user1id = user1id;
    }

    public int getUser2id() {
        return user2id;
    }

    public void setUser2id(int user2id) {
        this.user2id = user2id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", topic_detial_id=" + topic_detial_id +
                ", tu_id=" + tu_id +
                ", user1id=" + user1id +
                ", user2id=" + user2id +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", user=" + user +
                '}';
    }
}
