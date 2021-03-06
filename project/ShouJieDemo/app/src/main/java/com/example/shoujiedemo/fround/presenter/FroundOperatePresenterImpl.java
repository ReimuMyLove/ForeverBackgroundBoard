package com.example.shoujiedemo.fround.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.model.FroundOperateModel;
import com.example.shoujiedemo.fround.model.FroundOperateModelImpl;
import com.example.shoujiedemo.fround.view.ContentView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FroundOperatePresenterImpl implements FroundOperatePresenter, FroundOperationPresenterListener {

   private FroundOperateModel model;
   private ContentView contentView;

   public FroundOperatePresenterImpl(ContentView contentView){
      this.model = new FroundOperateModelImpl();
      this.contentView = contentView;
   }


   /**
    * 收藏成功回调
    */
   @Override
   public void onCollectSuccess() {

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
   public void onCommentSuccess() {
      contentView.comment();
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
      for(Comment comment1:comments){ ;
         String time = comment1.getTime().substring(5,16);
         comment1.setTime(time);
      }

      contentView.loadComment(comments);
   }


   /**
    * 验证点赞
    */
   @Override
   public void confirmFavourite() {

      model.favourite(this);
   }

   /**
    * 验证取消点赞
    */
   @Override
   public void confirmUnFavourite() {
      model.unFavourite(this);
   }

   /**
    * 验证分享
    */
   @Override
   public void confirmShare() {
      model.share(this);
   }

   /**
    * 验证收藏
    */
   @Override
   public void confirmCollect() {

      model.collect(this);
   }

   /**
    * 验证取消收藏
    */
   @Override
   public void confirmUnCollect() {
      model.unCollect(this);
   }

   /**
    * 验证取关
    */
   @Override
   public void confirmUnFolly() {

      model.unFolly(this);
   }

   @Override
   public void confirmFollow() {
      model.follow(this);
   }

   /**
    * 验证举报
    */
   @Override
   public void confirmReport() {
      model.report(this);
   }

   /**
    * 验证评论
    */
   @Override
   public void confirmComment() {
      model.comment(this);
   }

   @Override
   public void loadComment() {
      model.loadComment(this);
   }




}
