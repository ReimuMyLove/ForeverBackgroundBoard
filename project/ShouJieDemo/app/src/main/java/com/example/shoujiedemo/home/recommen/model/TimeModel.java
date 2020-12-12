package com.example.shoujiedemo.home.recommen.model;

import com.example.shoujiedemo.entity.Day;
import com.example.shoujiedemo.home.recommen.presenter.TimePresenterListener;

public interface TimeModel {

    void loadByTime(TimePresenterListener listener,Day day);
}
