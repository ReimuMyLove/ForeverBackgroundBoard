package com.example.shoujiedemo.bean;

public class MusicEvent {

    private int currentTime;
    private int duration;

    public MusicEvent() {
    }

    public MusicEvent(int currentTime) {
        this.currentTime = currentTime;
    }

    public MusicEvent(int currentTime, int duration) {
        this.currentTime = currentTime;
        this.duration = duration;
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
