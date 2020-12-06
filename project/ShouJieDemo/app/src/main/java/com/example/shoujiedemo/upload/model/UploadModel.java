package com.example.shoujiedemo.upload.model;


import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.upload.presenter.UploadPresenterListener;

public interface UploadModel {
    void Gointent(UploadPresenterListener listener, Content content,int isoriginal);//上传数据到服务端
}
