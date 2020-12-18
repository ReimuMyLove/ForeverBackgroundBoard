package com.example.shoujiedemo.myCenter.myCenter.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.model.MyCommentModel;
import com.example.shoujiedemo.myCenter.myCenter.model.MyCommentModelImpl;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyCommentView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyCommentPresenter implements MyCommentPresenterListener {
    private MyCommentView myCommentView;
    private MyCommentModel myCommentModel;

    public MyCommentPresenter(MyCommentView myCommentView){
        this.myCommentView = myCommentView;
        this.myCommentModel = new MyCommentModelImpl();
    }

    /**
     * 获取评论列表方法
     */
    public void getCommentList(int userId) {
        myCommentModel.getCommentList(userId,this);
    }

    /**
     * 获取评论列表回调方法
     */

    @Override
    public void getCommentListFailed() {
        myCommentView.getCommentListFailed();
    }

    @Override
    public void getCommentListSuccessful(String jsons) {
        Gson gson = new Gson();
        List<Comment> commentList;          //评论列表
        List<Content>  writerArticleList;   //图文列表
        List<User> userList;                //发布者信息
        try {
            //解析数据头
            JSONObject jsonArray = new JSONObject(jsons);
            //获取评论列表数据头
            JSONArray agreementArray = jsonArray.getJSONArray("cheatdate");
            //转化为字符串
            String comments = agreementArray.toString();
            //转化为评论列表
            commentList = gson.fromJson(comments, new TypeToken<List<Comment>>() {}.getType());
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
            myCommentView.getCommentListSuccessful(
                    commentList,
                    writerArticleList,
                    userList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
