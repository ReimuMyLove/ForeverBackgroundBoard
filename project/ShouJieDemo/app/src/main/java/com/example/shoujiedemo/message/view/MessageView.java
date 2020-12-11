package com.example.shoujiedemo.message.view;

import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;

import java.util.List;

public interface MessageView {
   void Showmessage(List<Comment>comments, List<LikeBean>likes, List<User>users, List<CollectBean>collects, List<Content>contents);
}
