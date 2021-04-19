package com.example.shoujiedemo.message.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.activity.ArticleActivity;
import com.example.shoujiedemo.activity.HeartActivity;
import com.example.shoujiedemo.activity.PoemActivity;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class CommentMessageAdapter extends BaseAdapter {

    private List<Comment> commentList = new ArrayList<>();
    private List<Content>contents=new ArrayList<>();
    private Context context;
    private CircleImageView imageView;
    private TextView name;
    private TextView time;
    private TextView text;
    private TextView content;
    private User user;
    private List<LikeBean>likes;
    public CommentMessageAdapter(List<Comment> commentList, Context context, List<Content> contents, User user, List<LikeBean>likes) {
        this.commentList = commentList;
        this.context = context;
        this.contents=contents;
        this.user=user;
        this.likes=likes;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_message_comment,viewGroup,false);
        initdata(view,i);
        return view;
    }

    private void initdata(View view, final int i) {
        imageView= view.findViewById(R.id.item_comment_message_user_head);
        Glide.with(context).load(ConfigUtil.BASE_HEAD_URL+commentList.get(i).getUser().getPicname()).into(imageView);
        name=view.findViewById(R.id.item_comment_message_user_name);
        name.setText(commentList.get(i).getUser().getName());
        text=view.findViewById(R.id.item_comment_message_comment_text);
        text.setText(commentList.get(i).getText());
        time=view.findViewById(R.id.item_comment_message_time);
        time.setText(commentList.get(i).getTime());
        content=view.findViewById(R.id.item_comment_message_comment_content_text);
        for (Content content1:contents) {
            if(content1.getId()==commentList.get(i).getTu_id()){
                content.setText(content1.getText());
                commentList.get(i).setContent(content1);
            }
        }
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(commentList.get(i).getContent().getTypeid()){
                    case 0:
                        Intent intent=new Intent();
                        intent.setClass(context, ArticleActivity.class);
                        Bundle bundle = new Bundle();
                        Log.e("wrk",commentList.get(i).getContent().toString());
                        Content content=commentList.get(i).getContent();
                        content.setUser(user);
                        content.setCollect(true);
                        bundle.putSerializable("article",commentList.get(i).getContent());
                        bundle.putSerializable("user",user);
                        intent.putExtra("bundle",bundle);
                        context.startActivity(intent);
                        break;
                    case 2:
                        Intent intent1=new Intent();
                        intent1.setClass(context, HeartActivity.class);
                        Bundle bundle1 = new Bundle();
                        Log.e("wrk",commentList.get(i).getContent().toString());
                        Content content1=commentList.get(i).getContent();
                        content1.setUser(user);
                        content1.setCollect(true);
                        bundle1.putSerializable("heart",commentList.get(i).getContent());
                        bundle1.putSerializable("user",user);
                        intent1.putExtra("bundle",bundle1);
                        context.startActivity(intent1);
                        break;
                    case 3:
                        Intent intent2=new Intent();
                        intent2.setClass(context, PoemActivity.class);
                        Bundle bundle2 = new Bundle();
                        Log.e("wrk",commentList.get(i).getContent().toString());
                        Content content2=commentList.get(i).getContent();
                        content2.setUser(user);
                        content2.setCollect(true);
                        bundle2.putSerializable("poem",commentList.get(i).getContent());
                        bundle2.putSerializable("user",user);
                        intent2.putExtra("bundle",bundle2);
                        context.startActivity(intent2);
                        break;
                }
            }
        });
    }
}
