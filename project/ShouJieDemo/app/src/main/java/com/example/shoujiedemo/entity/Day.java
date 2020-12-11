package com.example.shoujiedemo.entity;

public class Day {

    private int day;
    private String year;
    private String month;
    private String monthInEnglish;

    public Day(){

    }

    public Day( int day, String year) {
        this.day = day;
        this.year = year;
    }

    public Day(int day, String year, String month) {
        this.day = day;
        this.year = year;
        this.month = month;
    }

    public Day(int day, String year, String month, String monthInEnglish) {
        this.day = day;
        this.year = year;
        this.month = month;
        this.monthInEnglish = monthInEnglish;
    }

    public String getMonthInEnglish() {
        return monthInEnglish;
    }

    public void setMonthInEnglish(String monthInEnglish) {
        this.monthInEnglish = monthInEnglish;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
