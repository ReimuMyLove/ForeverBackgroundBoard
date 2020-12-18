package com.example.shoujiedemo.message.presenter;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.FollowBean;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.message.model.GetFollowModel;
import com.example.shoujiedemo.message.model.GetFollowModelImpl;
import com.example.shoujiedemo.message.view.MessageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFollowPresenerImpl implements GetFollowPresenter ,GetFollowPresenterListener {
    private GetFollowModel model;
    private MessageView view;
    public GetFollowPresenerImpl(MessageView view){
        this.view=view;
        model=new GetFollowModelImpl() ;
    }



    @Override
    public void LoadSuccess(String json) {
        List<User> users = new ArrayList<>();
        List<FollowBean>followBeans=new ArrayList<>();
        String user = null;
        String follow=null;
        Gson gson = new Gson();
        try {
            JSONObject jsonArray = new JSONObject(json);
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            user = userArray.toString();
            JSONArray followArray = jsonArray.getJSONArray("fendate");
            follow = followArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        followBeans = gson.fromJson(follow,new TypeToken<List<FollowBean>>() {}.getType() );
        view.Showfollow(users,followBeans);
    }

    @Override
    public void LoadFail(String Error) {

    }

    @Override
    public void getFollow(int userid) {
        model.getFollow(this,userid);
    }
}
