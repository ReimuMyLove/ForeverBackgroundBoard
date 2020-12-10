package com.example.shoujiedemo.upload.model;


import android.net.Uri;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.upload.presenter.UploadPresenterListener;

public interface UploadModel {
    void Gointent(UploadPresenterListener listener, Content content, int isoriginal);//上传数据到服务端
    void Gointent(UploadPresenterListener listener, Content content, int isoriginal, Uri uri);//上传数据到服务端

}
