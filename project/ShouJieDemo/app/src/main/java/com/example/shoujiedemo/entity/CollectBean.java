package com.example.shoujiedemo.entity;

public class CollectBean {

    private int id;
    private int tuwen_id;
    private int music_id;
    private int topic_detail_id;
    private int wenjiid;

    public CollectBean(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTuwen_id() {
        return tuwen_id;
    }

    public void setTuwen_id(int tuwen_id) {
        this.tuwen_id = tuwen_id;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public int getTopic_detail_id() {
        return topic_detail_id;
    }

    public void setTopic_detail_id(int topic_detail_id) {
        this.topic_detail_id = topic_detail_id;
    }

    public int getWenjiid() {
        return wenjiid;
    }

    public void setWenjiid(int wenjiid) {
        this.wenjiid = wenjiid;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "id=" + id +
                ", tuwen_id=" + tuwen_id +
                ", music_id=" + music_id +
                ", topic_detail_id=" + topic_detail_id +
                ", wenjiid=" + wenjiid +
                '}';
    }
}
