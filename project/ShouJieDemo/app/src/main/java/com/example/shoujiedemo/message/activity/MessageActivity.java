package com.example.shoujiedemo.message.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.message.presenter.GetMessagePresenter;
import com.example.shoujiedemo.message.presenter.GetMessagePresenterImpl;
import com.example.shoujiedemo.message.view.MessageView;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements MessageView {
    private GetMessagePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initdata();
    }

    private void initdata() {
        presenter=new GetMessagePresenterImpl(this);
        presenter.GetMessage(2);
    }

    @Override
    public void Showmessage(List<Comment> comments, List<LikeBean> likes, List<User> users, List<CollectBean> collects, List<Content> contents) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}