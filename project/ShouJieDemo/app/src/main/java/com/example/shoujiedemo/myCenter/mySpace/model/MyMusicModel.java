package com.example.shoujiedemo.myCenter.mySpace.model;

import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenterListener;

public interface MyMusicModel {
    void getMusic(int userID, MySpacePresenterListener listener);
}
