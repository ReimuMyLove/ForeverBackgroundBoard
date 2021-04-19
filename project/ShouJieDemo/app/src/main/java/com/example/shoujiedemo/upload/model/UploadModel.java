package com.example.shoujiedemo.upload.model;


import android.net.Uri;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.upload.presenter.UploadPresenterListener;

public interface UploadModel {
    void Gointent(UploadPresenterListener listener, Content content, int isoriginal,int setId);//上传数据到服务端
    void Gointent(UploadPresenterListener listener, Content content, int isoriginal, Uri uri,int setId);//上传数据到服务端

    void uploadMusic(UploadPresenterListener listener, int userId,int songId);//上传音乐

    void uploadMusic(UploadPresenterListener listener, int userId,int songId,String text);//上传音乐

    void loadSet(UploadPresenterListener listener,int userId);//获取文集
}
