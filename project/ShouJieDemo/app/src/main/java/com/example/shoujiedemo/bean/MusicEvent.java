package com.example.shoujiedemo.bean;

public class MusicEvent {

    private int tag;//0:播放，1：暂停，2：停止，3：继续播放，4：调整进度，5：返回改变
    private int currentTime;//当前时间
    private int duration;//总时间
    private boolean isPause;//是否暂停

    public MusicEvent() {
    }

    public MusicEvent(int currentTime) {
        this.currentTime = currentTime;
    }

    public MusicEvent(int currentTime, int duration) {
        this.currentTime = currentTime;
        this.duration = duration;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
