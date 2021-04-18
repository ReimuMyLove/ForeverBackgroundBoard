package com.example.shoujiedemo.home.follow.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.model.MyFollowOperateModel;
import com.example.shoujiedemo.home.follow.model.MyFollowOperateModelImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyFollowOperatePresenterImpl implements MyFollowOperatePresenter,MyFollowOperationPresenterListener{

   private MyFollowOperateModel model;
   private ContentView contentView;

   public MyFollowOperatePresenterImpl(ContentView contentView){
      this.model = new MyFollowOperateModelImpl();
      this.contentView = contentView;
   }


   /**
    * 收藏成功回调
    */
   @Override
   public void onCollectSuccess() {
      contentView.collect();
   }

   /**
    * 收藏失败回调
    */
   @Override
   public void onCollectError() {
      contentView.changeCollectionError();
   }

   /**
    * 评论成功回调
    */
   @Override
   public void onCommentSuccess(String jsons) {
      Gson gson = new Gson();
      Comment comment = gson.fromJson(jsons, Comment.class);
      String time = comment.getTime().substring(5,16);
      comment.setTime(time);
      contentView.comment(comment);
   }

   @Override
   public void onCommentError() {

   }



   /**
    * 分享成功回调
    */
   @Override
   public void onShareSuccess() {

   }


   /**
    * 点赞成功回调
    */
   @Override
   public void onFavouriteSuccess() {

   }

   /**
    * 点赞失败回调
    */
   @Override
   public void onFavouriteError() {
      contentView.changeLikeError();
   }

   /**
    * 取消关注成功回调
    */
   @Override
   public void onUnFollySuccess() {
      contentView.setUnFolly();
   }

   /**
    * 取关失败回调
    */
   @Override
   public void onUnFollyError() {
      contentView.setUnFollyError();
   }

   /**
    * 关注成功回调
    */
   @Override
   public void onFollowSuccess() {
      contentView.setFollow();
   }

   /**
    * 关注失败回调
    */
   @Override
   public void onFollowError() {
      contentView.setFollowError();
   }

   /**
    * 举报成功回调
    */
   @Override
   public void onReportSuccess() {

   }

   /**
    * 取消点赞失败
    */
   @Override
   public void onUnFavouriteError() {
      contentView.changeLikeError();
   }

   /**
    * 取消点赞成功
    */
   @Override
   public void onUnFavouriteSuccess() {

   }

   /**
    * 取消收藏成功
    */
   @Override
   public void onUnCollectSuccess() {

   }

   /**
    * 取消收藏失败
    */
   @Override
   public void onUnCollectError() {
      contentView.changeCollectionError();
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
      for(Comment comment1:comments){
          String time = comment1.getTime().substring(5,16);
         comment1.setTime(time);
      }

      contentView.loadComment(comments);
   }

   @Override
   public void onLoadCommentError() {

   }

   @Override
   public void onLoadSetSuccess(String jsons) {
      Gson gson = new Gson();
      Object json = null;
      try {
         json = new JSONTokener(jsons).nextValue();
      } catch (JSONException e) {
         e.printStackTrace();
      }
      if(json instanceof JSONObject) {
         Set set = gson.fromJson(jsons, Set.class);
         List<Set> setList = new ArrayList<>();
         setList.add(set);
         contentView.showSet(setList);
      }else if(json instanceof JSONArray){
         List<Set> setList = gson.fromJson(jsons, new TypeToken<List<Set>>() {}.getType());
         contentView.showSet(setList);
      }
   }

   @Override
   public void onLoadSetError() {

   }

   @Override
   public void onDeleteCommentSuccess() {
      Log.e("listener","success");
      contentView.deleteComment();
   }

   @Override
   public void onDeleteCommentError() {

   }

   /**
    * 验证点赞
    */
   @Override
   public void confirmFavourite(int userId,int contentId) {

      model.favourite(this,userId,contentId);
   }

   /**
    * 验证取消点赞
    */
   @Override
   public void confirmUnFavourite(int userId,int contentId) {
      model.unFavourite(this,userId,contentId);
   }

   /**
    * 验证分享
    */
   @Override
   public void confirmShare(int userId,int contentId) {
      model.share(this,userId,contentId);
   }

   /**
    * 验证收藏
    */
   @Override
   public void confirmCollect(int userId,int contentId) {

      model.collect(this,userId,contentId);

   }

   /**
    * 验证取消收藏
    */
   @Override
   public void confirmUnCollect(int userId,int contentId) {
      model.unCollect(this,userId,contentId);
   }

   /**
    * 验证取关
    */
   @Override
   public void confirmUnFolly(int userId,int contentId) {

      model.unFolly(this,userId,contentId);
   }

   @Override
   public void confirmFollow(int userId,int contentId) {
      model.follow(this,userId,contentId);
   }

   /**
    * 验证举报
    */
   @Override
   public void confirmReport(int userId,int contentId) {
      model.report(this,userId,contentId);
   }

   /**
    * 验证评论
    */
   @Override
   public void confirmComment(int userId,int contentId,String text) {
      model.comment(this,userId,contentId,text);
   }

   @Override
   public void loadComment(int contentId,int pageNum) {
      model.loadComment(this,contentId,pageNum);
   }

   @Override
   public void loadSet(int userId) {
      model.loadSet(this,userId);
   }

   @Override
   public void deleteComment(int commentId) {
      model.deleteComment(this,commentId);
   }


}
