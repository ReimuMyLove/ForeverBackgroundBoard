package com.example.shoujiedemo.entity;

public class Month {
    private boolean isSelect;
    private String month;
    private String year;

    public Month(){

    }

    public Month(boolean isSelect,String month){
        this.isSelect = isSelect;
        this.month = month;
    }

    public Month(boolean isSelect, String month, String year) {
        this.isSelect = isSelect;
        this.month = month;
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Month{" +
                "isSelect=" + isSelect +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
