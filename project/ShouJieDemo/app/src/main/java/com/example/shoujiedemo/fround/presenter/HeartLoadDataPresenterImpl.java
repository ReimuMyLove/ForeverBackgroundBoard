package com.example.shoujiedemo.fround.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.model.FroundLoadDataModel;
import com.example.shoujiedemo.fround.model.HeartLoadDataModelImpl;
import com.example.shoujiedemo.fround.view.HeartView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HeartLoadDataPresenterImpl implements FroundLoadDataPresenter,FroundLoadDataPresenterLisenter{
    private FroundLoadDataModel model;
    private HeartView view;

    public HeartLoadDataPresenterImpl(HeartView view) {
        this.view = view;
        model = new HeartLoadDataModelImpl();
    }



    @Override
    public void onLoadContentDataSuccess(String jsons) {
        List<Content> contents = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<LikeBean> likeBeans = new ArrayList<>();
        List<User> follows = new ArrayList<>();
        List<CollectBean> collectBeans = new ArrayList<>();
        Gson gson = new Gson();
        String user = null;
        String content = null;
        String like = null;
        String follow = null;
        String collect = null;
        try {
            JSONObject jsonArray = new JSONObject(jsons);
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            user = userArray.toString();
            JSONArray contentArray = jsonArray.getJSONArray("tuwendate");
            content = contentArray.toString();
            JSONArray likeArray = jsonArray.getJSONArray("likedate");
            like = likeArray.toString();
            JSONArray followArray = jsonArray.getJSONArray("followdate");
            follow = followArray.toString();
            JSONArray collectArray = jsonArray.getJSONArray("collectdate");
            collect = collectArray.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        contents = gson.fromJson(content, new TypeToken<List<Content>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        likeBeans = gson.fromJson(like,new TypeToken<List<LikeBean>>() {}.getType() );
        follows = gson.fromJson(follow,new TypeToken<List<User>>() {}.getType() );
        collectBeans = gson.fromJson(collect,new TypeToken<List<CollectBean>>() {}.getType() );

        if(follows != null) {
            for (User user1 : users) {
                user1.setFollow(true);
                for (User user2 : follows) {
                    if (user1.getId() == user2.getId()) {
                        user1.setFollow(false);
                    }
                }
            }
        }


        for(Content content1:contents){
            for(User user1 :users){
                if(content1.getUserid() == user1.getId()){
                    content1.setUser(user1);
                }
            }
        }

        if(likeBeans != null) {
            for (Content content1 : contents) {
                for (LikeBean likeBean : likeBeans) {
                    if (content1.getId() == likeBean.getTuwenid()) {
                        content1.setLike(true);
                    }
                }
            }

        }

        if(collectBeans != null){
            for (Content content1 : contents) {
                for (CollectBean collectBean : collectBeans) {
                    if (content1.getId() == collectBean.getTuwen_id()) {
                        content1.setCollect(true);
                    }
                }
            }
        }

        for(Content content1:contents){
            String time = content1.getTime().substring(5,16);
            content1.setTime(time);
        }
        if(contents != null){
            view.showContentListData(contents);
        }
    }

    @Override
    public void onLoadContentDataError() {
        view.onData();
    }

    @Override
    public void onSearchDataSuccess(String jsons) {
        List<Content> contents = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<LikeBean> likeBeans = new ArrayList<>();
        List<User> follows = new ArrayList<>();
        List<CollectBean> collectBeans = new ArrayList<>();
        Gson gson = new Gson();
        String user = null;
        String content = null;
        String like = null;
        String follow = null;
        String collect = null;
        try {
            JSONObject jsonArray = new JSONObject(jsons);
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            user = userArray.toString();
            JSONArray contentArray = jsonArray.getJSONArray("tagdate");
            content = contentArray.toString();
            JSONArray likeArray = jsonArray.getJSONArray("likedate");
            like = likeArray.toString();
            JSONArray followArry = jsonArray.getJSONArray("followdate");
            follow = followArry.toString();
            JSONArray collectArray = jsonArray.getJSONArray("collectdate");
            collect = collectArray.toString();

            Log.i("content",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        contents = gson.fromJson(content, new TypeToken<List<Content>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        likeBeans = gson.fromJson(like,new TypeToken<List<LikeBean>>() {}.getType() );
        follows = gson.fromJson(follow,new TypeToken<List<User>>() {}.getType() );
        collectBeans = gson.fromJson(collect,new TypeToken<List<CollectBean>>() {}.getType() );

        if(follows != null) {
            for (User user1 : users) {
                user1.setFollow(true);
                for (User user2 : follows) {
                    if (user1.getId() == user2.getId()) {
                        user1.setFollow(false);
                    }
                }
            }
        }


        for(Content content1:contents){
            for(User user1 :users){
                if(content1.getUserid() == user1.getId()){
                    content1.setUser(user1);
                }
            }
        }

        if(likeBeans != null) {
            for (Content content1 : contents) {
                for (LikeBean likeBean : likeBeans) {
                    if (content1.getId() == likeBean.getTuwenid()) {
                        content1.setLike(true);
                    }
                }
            }

        }

        if(collectBeans != null){
            for (Content content1 : contents) {
                for (CollectBean collectBean : collectBeans) {
                    if (content1.getId() == collectBean.getTuwen_id()) {
                        content1.setCollect(true);
                    }
                }
            }
        }

        for(Content content1:contents){
            String time = content1.getTime().substring(5,16);
            content1.setTime(time);
        }
        if(contents != null){
            view.showContentListData(contents);
        }
    }

    @Override
    public void onSearchDataError() {
        view.onData();
    }

    @Override
    public void confirmInitContent(int typeId,int pageNum,int userId) {
        model.loadContents(this,typeId,pageNum,userId);
    }

    @Override
    public void searchData(String flag, int typeId, int page,int userId) {
        model.search(this,flag,typeId,page,userId);
    }
}
