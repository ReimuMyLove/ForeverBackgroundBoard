package com.example.shoujiedemo.message.presenter;


import android.util.Log;

import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.FollowBean;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.message.model.GetMessageModel;
import com.example.shoujiedemo.message.model.GetMessageModelImpl;
import com.example.shoujiedemo.message.view.MessageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        List<Content> contents = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        List<LikeBean>likes=new ArrayList<>();
        Gson gson = new Gson();
        String user = null;
        String content = null;
        String cheat = null;
        String like=null;
        try {
            JSONObject jsonArray = new JSONObject(json);
            JSONArray userArray = jsonArray.getJSONArray("user");
            user = userArray.toString();
            JSONArray contentArray = jsonArray.getJSONArray("tuwen");
            content = contentArray.toString();
            JSONArray chaetArray = jsonArray.getJSONArray("cheat");
            cheat = chaetArray.toString();
            JSONArray likeArray = jsonArray.getJSONArray("like");
            like=likeArray.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        likes=gson.fromJson(like,new TypeToken<List<LikeBean>>(){}.getType());;
        contents = gson.fromJson(content, new TypeToken<List<Content>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        comments = gson.fromJson(cheat,new TypeToken<List<Comment>>() {}.getType() );
        if(likes != null) {
            for (Content content1 : contents) {
                for (LikeBean likeBean : likes) {
                    if (content1.getId() == likeBean.getTuwenid()) {
                        content1.setLike(true);
                    }
                }
            }

        }

        view.Showmessage(comments,users,contents,likes);


    }

    @Override
    public void LoadMessageError(String error) {

    }
}
