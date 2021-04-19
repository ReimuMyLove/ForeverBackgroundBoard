package com.example.shoujiedemo.message.view;

import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.FollowBean;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;

import java.util.List;

public interface MessageView {
   void Showmessage(List<Comment>comments,  List<User>users,List<Content>contents,List<LikeBean> likes);
   void Showfollow(List<User>users, List<FollowBean>followBeans);
}
