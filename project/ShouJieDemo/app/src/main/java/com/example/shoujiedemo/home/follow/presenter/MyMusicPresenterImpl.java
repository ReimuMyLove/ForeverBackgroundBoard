package com.example.shoujiedemo.home.follow.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.model.MyFollowOperateModel;
import com.example.shoujiedemo.home.follow.model.MyMusicOperateModelImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyMusicPresenterImpl implements MyFollowOperatePresenter,MyFollowOperationPresenterListener{

    private ContentView view;
    private MyFollowOperateModel model;

    public MyMusicPresenterImpl(ContentView view) {
        this.view = view;
        model = new MyMusicOperateModelImpl();
    }

    @Override
    public void confirmFavourite(int userId, int contentId) {
        Log.e("presenter","presenter");
        model.favourite(this,userId,contentId);
    }

    @Override
    public void confirmUnFavourite(int userId, int contentId) {
        model.unFavourite(this,userId,contentId);
    }

    @Override
    public void confirmShare(int userId, int contentId) {

    }

    @Override
    public void confirmCollect(int userId, int contentId) {
        view.collect();
    }

    @Override
    public void confirmUnCollect(int userId, int contentId) {
        view.changeCollectionError();
    }

    @Override
    public void confirmUnFolly(int userId, int followerId) {

    }

    @Override
    public void confirmFollow(int userId, int followerId) {

    }

    @Override
    public void confirmReport(int userId, int contentId) {

    }

    @Override
    public void confirmComment(int userId, int contentId, String text) {

    }

    @Override
    public void loadComment(int contentId, int pageNum) {

    }

    @Override
    public void loadSet(int userId) {

    }

    @Override
    public void deleteComment(int commentId) {

    }

    @Override
    public void deleteContent(int id) {
        model.deleteContent(this,id);
    }


    @Override
    public void onCollectSuccess() {

    }

    @Override
    public void onCollectError() {

    }

    @Override
    public void onCommentSuccess(String jsons) {
        Gson gson = new Gson();
        Comment comment = gson.fromJson(jsons, Comment.class);
        String time = comment.getTime().substring(5,16);
        comment.setTime(time);
        view.comment(comment);
    }

    @Override
    public void onCommentError() {

    }

    @Override
    public void onShareSuccess() {

    }

    @Override
    public void onFavouriteSuccess() {
        Log.e("presenter","presenter");
    }

    @Override
    public void onFavouriteError() {
        view.changeLikeError();
    }

    @Override
    public void onUnFollySuccess() {
        view.setUnFolly();
    }

    @Override
    public void onUnFollyError() {
        view.setUnFollyError();
    }

    @Override
    public void onFollowSuccess() {
        view.setFollow();
    }

    @Override
    public void onFollowError() {
        view.setFollowError();
    }

    @Override
    public void onReportSuccess() {

    }

    @Override
    public void onUnFavouriteError() {
        view.changeLikeError();
    }

    @Override
    public void onUnFavouriteSuccess() {

    }

    @Override
    public void onUnCollectSuccess() {

    }

    @Override
    public void onUnCollectError() {
        view.changeCollectionError();
    }

    @Override
    public void onLoadCommentSuccess(String jsons) {
        List<Comment> comments = new ArrayList<>();
        List<User> users = new ArrayList<>();
        Gson gson = new Gson();
        String user = null;
        String comment = null;
        try {
            JSONObject jsonArray = new JSONObject(jsons);
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            user = userArray.toString();
            JSONArray contentArray = jsonArray.getJSONArray("tuwendate");
            comment = contentArray.toString();
            Log.e("comment",comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        comments = gson.fromJson(comment, new TypeToken<List<Comment>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        for(Comment comment1:comments){
            for(User user1 :users){
                if(comment1.getUser1id() == user1.getId()){
                    comment1.setUser(user1);
                    continue;
                }
            }
        }
        for(Comment comment1:comments){ ;
            String time = comment1.getTime().substring(5,16);
            comment1.setTime(time);
        }

        view.loadComment(comments);
    }

    @Override
    public void onLoadCommentError() {

    }

    @Override
    public void onLoadSetSuccess(String jsons) {

    }

    @Override
    public void onLoadSetError() {

    }

    @Override
    public void onDeleteCommentSuccess() {
        view.deleteComment();
    }

    @Override
    public void onDeleteCommentError() {

    }

    @Override
    public void onDeleteContentSuccess() {
        view.deleteContent();
    }

    @Override
    public void onDeleteContentError() {
        view.deleteError();
    }

}
