package com.example.shoujiedemo.myCenter.myCenter.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.model.MyFollowModel;
import com.example.shoujiedemo.myCenter.myCenter.model.MyFollowModelImpl;
import com.example.shoujiedemo.myCenter.myCenter.service.MyFollowPresenterListener;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyFollowAdapterView;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyFollowView;
import com.example.shoujiedemo.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyFollowPresenter implements MyFollowPresenterListener {
    private MyFollowAdapterView myFollowAdapter;
    private MyFollowModel myFollowModel;

    private MyFollowView myFollowView;

    public MyFollowPresenter(MyFollowAdapterView myFollowAdapter) {
        this.myFollowAdapter = myFollowAdapter;
        myFollowModel = new MyFollowModelImpl();
    }

    public MyFollowPresenter(MyFollowView myFollowView){
        this.myFollowView = myFollowView;
        myFollowModel = new MyFollowModelImpl();
    }
    /**
     * 逻辑实现方法
     */
    public void cancelFollower(int followerID){
        int userID = UserUtil.USER_ID;
        myFollowModel.cancelFollower(userID,followerID,this);
    }

    public void findFollower(int userID) {
        myFollowModel.findFollower(userID,this);
    }

    /**
     * 结果回调方法
     */

    @Override
    public void cancelFailed() {
        myFollowAdapter.cancelFailed();
    }

    @Override
    public void cancelSuccessful(int followerID) {
        myFollowAdapter.cancelSuccessful(followerID);
    }

    /**
     *  获取关注列表回调方法
     */

    @Override
    public void findSuccessful(String jsons) {
        Gson gson = new Gson();
        String users;
        try {
            JSONObject jsonObject =new JSONObject(jsons);
            JSONArray jsonArray = jsonObject.getJSONArray("userdate");
            users = jsonArray.toString();
            List<User> userList = gson.fromJson(users, new TypeToken<List<User>>() {}.getType());
            myFollowView.getFollows(userList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findFailed() {

    }
}
