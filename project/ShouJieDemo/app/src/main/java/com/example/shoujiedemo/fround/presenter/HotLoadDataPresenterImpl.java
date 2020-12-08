package com.example.shoujiedemo.fround.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.model.FroundHotDataModel;
import com.example.shoujiedemo.fround.model.FroundLoadDataModel;
import com.example.shoujiedemo.fround.model.HotLoadDataModelImpl;
import com.example.shoujiedemo.fround.view.HotView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HotLoadDataPresenterImpl implements HotLoadDataPresenter,HotLoadDataPresenterListener{
    private FroundHotDataModel model;
    private HotView view;

    public HotLoadDataPresenterImpl(HotView view) {

        this.view = view;
        this.model = new HotLoadDataModelImpl();

    }

    @Override
    public void confirmInitContent(int pageNum) {
        model.loadContents(this,pageNum);
    }

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
            JSONArray contentArray = jsonArray.getJSONArray("hotdate");
            content = contentArray.toString();
            Log.i("content",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        contents = gson.fromJson(content, new TypeToken<List<Content>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        Log.i("user",user);
        for(Content content1:contents){
            for(User user1 :users){
                if(content1.getUserid() == user1.getId()){
                    content1.setUser(user1);
                    continue;
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
        view.noData();
    }
}
