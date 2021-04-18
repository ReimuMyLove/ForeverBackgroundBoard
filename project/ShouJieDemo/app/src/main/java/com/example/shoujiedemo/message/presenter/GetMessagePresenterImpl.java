package com.example.shoujiedemo.message.presenter;


import android.util.Log;

import com.example.shoujiedemo.message.model.GetMessageModel;
import com.example.shoujiedemo.message.model.GetMessageModelImpl;
import com.example.shoujiedemo.message.view.MessageView;

public class GetMessagePresenterImpl implements GetMessagePresenter,GetmessagePresenterListener{
    private GetMessageModel model;
    private MessageView view;
    public GetMessagePresenterImpl(MessageView view){
        this.view=view;
        model=new GetMessageModelImpl();
    }
    @Override
    public void GetMessage(int userid) {
        model.getMessage(this,userid);
    }

    @Override
    public void LoadMessageSuccess(String json) {
        Log.e("wrk",json);
    }

    @Override
    public void LoadMessageError(String error) {

    }
}
