package com.example.shoujiedemo.message.model;

import com.example.shoujiedemo.message.presenter.GetmessagePresenterListener;

public interface GetMessageModel {
    void getMessage(GetmessagePresenterListener listener, int userid);
}
