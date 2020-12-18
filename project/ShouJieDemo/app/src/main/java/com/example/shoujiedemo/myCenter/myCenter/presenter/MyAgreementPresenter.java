package com.example.shoujiedemo.myCenter.myCenter.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.model.MyAgreementModel;
import com.example.shoujiedemo.myCenter.myCenter.model.MyAgreementModelImpl;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyAgreementView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyAgreementPresenter implements MyAgreementPresenterListener {
    private MyAgreementView myAgreementView;
    private MyAgreementModel myAgreementModel;

    public MyAgreementPresenter(MyAgreementView myAgreementView){
        this.myAgreementView = myAgreementView;
        this.myAgreementModel = new MyAgreementModelImpl();
    }

    /**
     * 逻辑实现代码
     */

    //获取全部点赞数据
    public void getAgreement(int userID) {
        myAgreementModel.getAgreementList(userID,this);
    }

    /**
     * 获取全部点赞数据回调方法
     */

    @Override
    public void getAgreementListSuccessful(String jsons) {
        Gson gson = new Gson();
        List<LikeBean> agreementList;       //点赞列表
        List<Content>  writerArticleList;   //图文列表
        List<User> userList;                //发布者信息
        try {
            //解析数据头
            JSONObject jsonArray = new JSONObject(jsons);
            //获取点赞列表数据头
            JSONArray agreementArray = jsonArray.getJSONArray("likedate");
            //转化为字符串
            String agreements = agreementArray.toString();
            //转化为点赞列表
            agreementList = gson.fromJson(agreements, new TypeToken<List<LikeBean>>() {}.getType());
            //获取用户信息头
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            //转化为字符串
            String users = userArray.toString();
            //转化为用户列表
            userList = gson.fromJson(users, new TypeToken<List<User>>() {}.getType());
            //获取文章信息数据头
            JSONArray writerArticleArray = jsonArray.getJSONArray("tuwendate");
            //转化为字符串
            String writerArticles = writerArticleArray.toString();
            //转化为文章信息列表
            writerArticleList = gson.fromJson(writerArticles, new TypeToken<List<Content>>() {}.getType());
            //返回回调方法
            myAgreementView.getAgreementListSuccessful(agreementList,writerArticleList,userList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAgreementListFailed() {
        myAgreementView.getAgreementListFailed();
    }

}
