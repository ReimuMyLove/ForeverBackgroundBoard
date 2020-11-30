package com.example.shoujiedemo.home.follow.presenter;

import com.example.shoujiedemo.home.follow.model.MyFollowOperateModel;
import com.example.shoujiedemo.home.follow.model.MyFollowOperateModelImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;

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

   /**
    * 验证点赞
    */
   @Override
   public void confirmFavourite() {

      model.favourite(this);
   }

   /**
    * 验证点赞
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


}
