package com.example.shoujiedemo.message.model;

import com.example.shoujiedemo.upload.presenter.UploadPresenterListener;

public interface GetMessageModel {
    void getMessage(UploadPresenterListener listener, int userid);
}
