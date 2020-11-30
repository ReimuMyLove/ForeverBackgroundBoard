package com.example.shoujiedemo.bean;

public class MsgEvent {
    private String type;
    private boolean value;
    private int position;

    public MsgEvent(){

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
