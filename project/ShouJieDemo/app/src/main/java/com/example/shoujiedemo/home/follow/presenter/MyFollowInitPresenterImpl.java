package com.example.shoujiedemo.home.follow.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.model.MyFollowInitModel;
import com.example.shoujiedemo.home.follow.model.MyFollowInitModelImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyFollowInitPresenterImpl implements MyFollowInitPresenter, MyFollowInitPresenterListener {

    private FollowView followView;
    private MyFollowInitModel model;

    public MyFollowInitPresenterImpl(FollowView followView) {
        this.followView = followView;
        this.model = new MyFollowInitModelImpl();
    }

    /**
     * 验证初始化
     */
    @Override
    public void confirmInitContent(int userId,int pageNum) {
        model.loadContents(this,userId,pageNum);
    }


    /**
     * 初始化成功回调
     */
    @Override
    public void onLoadContentDataSuccess(String jsons) {
        List<Content> contents = new ArrayList<>();
        List<User> users = new ArrayList<>();
        Gson gson = new Gson();
        String user = null;
        String content = null;
        try {
            JSONObject jsonArray = new JSONObject(jsons);
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            user = userArray.toString();
            JSONArray contentArray = jsonArray.getJSONArray("tuwendate");
            content = contentArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        contents = gson.fromJson(content, new TypeToken<List<Content>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        for(Content content1:contents){
            for(User user1 :users){
                if(content1.getUserid() == user1.getId()){
                    content1.setUser(user1);
                    Log.e("content" + content1.getId(),content1.getUserid() + "");
                    Log.e("user" + user1.getId(),user1.toString());
                }
            }
        }

        for(Content content1:contents){
            String time = content1.getTime().substring(5,16);
            content1.setTime(time);
        }

        followView.showContentListData(contents);
    }

    @Override
    public void onLoadContentDataError() {
        followView.noData();
    }


}
