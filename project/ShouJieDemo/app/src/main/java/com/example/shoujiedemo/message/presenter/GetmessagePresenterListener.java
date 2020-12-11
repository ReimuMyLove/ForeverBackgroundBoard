package com.example.shoujiedemo.message.presenter;

public interface GetmessagePresenterListener {
    void LoadMessageSuccess(String json);
    void LoadMessageError(String error);
}
