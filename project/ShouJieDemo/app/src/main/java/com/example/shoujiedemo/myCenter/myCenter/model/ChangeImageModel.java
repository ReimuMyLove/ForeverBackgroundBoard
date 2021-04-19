package com.example.shoujiedemo.myCenter.myCenter.model;

import android.net.Uri;

import com.example.shoujiedemo.myCenter.myCenter.presenter.ChangeImagePresenterListener;

public interface ChangeImageModel {
    void changeImage(ChangeImagePresenterListener listener, int userid, Uri uri);
    void uploadWenji(ChangeImagePresenterListener listener,int userid ,Uri uri ,String name);
    void changeBackGround(ChangeImagePresenterListener listener,int userid,Uri uri);
}
