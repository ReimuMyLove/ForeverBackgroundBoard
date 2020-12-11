package com.example.shoujiedemo.message.activity;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.message.view.MessageView;

import java.util.List;

/**
 * 消息页面
 */
public class MessageFragment extends Fragment implements MessageView {


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }


    @Override
    public void Showmessage(List<Comment> comments, List<LikeBean> likes, List<User> users, List<CollectBean> collects, List<Content> contents) {

    }
}
