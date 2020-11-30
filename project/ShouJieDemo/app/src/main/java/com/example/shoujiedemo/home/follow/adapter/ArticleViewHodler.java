package com.example.shoujiedemo.home.follow.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.activity.ArticleActivity;
import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenterImpl;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.presenter.MyFollowUserPresenter;
import com.example.shoujiedemo.home.follow.view.ArticleView;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;
import com.example.shoujiedemo.home.recommen.presenter.OperatePresenter;
import com.example.shoujiedemo.home.recommen.presenter.OperatePresenterImpl;
import com.example.shoujiedemo.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class ArticleViewHodler extends RecyclerView.ViewHolder implements ContentView {

    ImageView articleCover;//封面
    TextView articleTitle;//标题
    TextView articlesynopsis;//简介
    TextView articleWriterName;//作者
    View artivleContent;//内容
    ImageView head;
    TextView userName;
    TextView set;
    TextView fanNum;
    Button btnFollow;
    Button btnPull;
    View lnReport;
    TextView unLike;
    TextView tag ;
    Button share;
    TextView shareNum;
    Button collected;
    TextView collectionNum;
    Button comment;
    TextView commentNum;
    Button like;
    TextView likeNum;
    View popuMenu;
    ImageView followAnim;

    private User user;

    private Context context;
    private MyOnClikListener myOnClikListener;
    private int position;

    private List<Content> contents = new ArrayList<>();
    private MyFollowOperatePresenter presenter;

    private boolean isFollow = false;
    private boolean isPull = false;

    private AnimationDrawable loadingDrawable;



    public ArticleViewHodler(@NonNull View itemView, Context context,List<Content> contents) {
        super(itemView);
        this.context = context;
        this.contents = contents;
        presenter = new MyFollowOperatePresenterImpl(this);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        articleCover = itemView.findViewById(R.id.follow_article_iv_cover);
        articleTitle = itemView.findViewById(R.id.follow_actricle_tv_title);
        articlesynopsis = itemView.findViewById(R.id.follow_actricle_tv_synopsis);
        articleWriterName = itemView.findViewById(R.id.follow_article_tv_writer_name);
        artivleContent = itemView.findViewById(R.id.follow_article_rn_content);
        head = itemView.findViewById(R.id.follow_article_iv_head);
        userName = itemView.findViewById(R.id.follow_article_tv_user_name);
        set = itemView.findViewById(R.id.follow_article_tv_set);
        fanNum = itemView.findViewById(R.id.follow_article_tv_fan);
        btnFollow = itemView.findViewById(R.id.follow_article_btn_follow);
        btnPull = itemView.findViewById(R.id.follow_article_btn_pull);
        lnReport = itemView.findViewById(R.id.follow_article_ln_report);
        unLike = itemView.findViewById(R.id.follow_article_tv_unLike);
        tag = itemView.findViewById(R.id.follow_article_tv_tag);
        share = itemView.findViewById(R.id.follow_article_btn_share);
        shareNum = itemView.findViewById(R.id.follow_article_tv_share_num);
        collected = itemView.findViewById(R.id.follow_article_btn_collection);

        if (contents.get(position).isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);

        collectionNum = itemView.findViewById(R.id.follow_article_tv_collection_num);
        comment = itemView.findViewById(R.id.follow_article_btn_comment);
        commentNum = itemView.findViewById(R.id.follow_article_tv_comment_num);
        like = itemView.findViewById(R.id.follow_article_btn_like);

        if (contents.get(position).isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        likeNum = itemView.findViewById(R.id.follow_article_tv_like_num);
        popuMenu = itemView.findViewById(R.id.follow_article_pull_menu);
        followAnim = itemView.findViewById(R.id.follow_article_iv_follow_anim);

        setMyOnClikListenser();
    }

    public void initData(int position){
        this.position = position;
        articlesynopsis.setText(contents.get(position).getText());
        articleTitle.setText(contents.get(position).getTitle());
        articleWriterName.setText("文by" + contents.get(position).getWriter());
        likeNum.setText(contents.get(position).getLikes() + "");
        commentNum.setText(contents.get(position).getCheatnum() + "");
        shareNum.setText(contents.get(position).getForwardnum() + "");
        collectionNum.setText(contents.get(position).getCollectnum() + "");
        user = contents.get(position).getUser();
        userName.setText(user.getName());
        fanNum.setText(user.getFennum() + "");
        if (contents.get(position).isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        if (contents.get(position).isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);

        Glide.with(context)
                .load(contents.get(position).getPic())
                .into(articleCover);
        articleCover.setScaleType(ImageView.ScaleType.FIT_XY);

    }



    /**
     * onclik内部类
     */
    public void setMyOnClikListenser(){
        myOnClikListener = new MyOnClikListener();
        head.setOnClickListener(myOnClikListener);
        set.setOnClickListener(myOnClikListener);
        like.setOnClickListener(myOnClikListener);
        collected.setOnClickListener(myOnClikListener);
        btnPull.setOnClickListener(myOnClikListener);
        btnFollow.setOnClickListener(myOnClikListener);
        artivleContent.setOnClickListener(myOnClikListener);
    }

    class MyOnClikListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.follow_article_iv_head://点击头像进入用户空间

                    break;
                case R.id.follow_article_tv_set://点击文集进入用户空间

                    break;
                case R.id.follow_article_btn_pull://点击弹出下拉菜单
                    if(isPull){
                        popuMenu.setVisibility(View.GONE);
                        isPull = false;
                    }else{
                        popuMenu.setVisibility(View.VISIBLE);
                        isPull = true;
                    }
                    break;
                case R.id.follow_article_ln_report:

                    break;
                case R.id.follow_article_btn_share:

                    break;
                case R.id.follow_article_btn_collection://收藏
                    if(contents.get(position).isCollect()) {
                        collected.setBackgroundResource(R.drawable.collectionunselect);//取消收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) -1;
                        contents.get(position).setCollectnum(collectionNums);
                        collectionNum.setText(collectionNums + "");
                        contents.get(position).setCollect(false);
                        presenter.confirmUnCollect();
                    }else{
                        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
                        contents.get(position).setCollectnum(collectionNums);
                        collectionNum.setText(collectionNums + "");
                        contents.get(position).setCollect(true);
                        presenter.confirmCollect();
                    }
                    break;
                case R.id.follow_article_btn_comment://评论按钮跳转到详情界面
                        comment();
                    break;
                case R.id.follow_article_btn_like://点赞
                    if(!contents.get(position).isLike()) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) + 1;
                        contents.get(position).setLikes(likeNums);
                        likeNum.setText(likeNums + "");
                        contents.get(position).setLike(true);
                        presenter.confirmFavourite();
                    }else{
                        like.setBackgroundResource(R.drawable.likeunselect);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) - 1;
                        contents.get(position).setLikes(likeNums);
                        likeNum.setText(likeNums + "");
                        contents.get(position).setLike(false);
                        presenter.confirmUnFavourite();
                    }
                    break;
                case R.id.follow_article_btn_follow:
                    followAnim.setVisibility(View.VISIBLE);
                    btnFollow.setVisibility(View.INVISIBLE);
                    loadingDrawable =(AnimationDrawable)followAnim.getDrawable();
                    loadingDrawable.start();
                    if(isFollow) {  //关注
                        isFollow = false;
                        presenter.confirmFollow();
                    }else {         //取关
                        isFollow= true;
                        presenter.confirmUnFolly();
                    }
                    break;
                case R.id.follow_article_rn_content:
                    Intent intent2 = new Intent(context, ArticleActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("article",contents.get(position));
                    bundle2.putBoolean("isFollow",isFollow);
                    bundle2.putSerializable("user",contents.get(position).getUser());
                    bundle2.putInt("position",position);
                    intent2.putExtra("bundle",bundle2);
                    context.startActivity(intent2);
                    break;
            }
        }
    }


    @Override
    public void setUnFolly() {
        if(loadingDrawable.isRunning()) {
            loadingDrawable.stop();
            btnFollow.setVisibility(View.VISIBLE);
            followAnim.setVisibility(View.INVISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(btnFollow, "scaleX",
                    1f, 0f, 1f);
            animator.setDuration(500);
            animator.start();
            Toast.makeText(context,"取消关注",Toast.LENGTH_SHORT).show();
            btnFollow.setText("关注+");
            int fenNum = Integer.parseInt(fanNum.getText().toString()) - 1;
            fanNum.setText(fenNum + "");
            isFollow= true;
        }
    }

    @Override
    public void setUnFollyError() {
        if(loadingDrawable.isRunning()) {
            loadingDrawable.stop();
            btnFollow.setVisibility(View.VISIBLE);
            followAnim.setVisibility(View.INVISIBLE);
            Toast.makeText(context,"取消关注失败",Toast.LENGTH_SHORT).show();
            isFollow= false;
        }
    }

    @Override
    public void setFollow() {
        if(loadingDrawable.isRunning()){
            loadingDrawable.stop();
            btnFollow.setVisibility(View.VISIBLE);
            followAnim.setVisibility(View.INVISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(btnFollow, "scaleX",
                    1f, 0f, 1f);
            animator.setDuration(500);
            animator.start();
            btnFollow.setText("已关注");
            int fenNum = Integer.parseInt(fanNum.getText().toString()) + 1;
            fanNum.setText(fenNum + "");
            Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
            isFollow= false;
        }
    }

    @Override
    public void setFollowError() {
        if(loadingDrawable.isRunning()){
            loadingDrawable.stop();
            btnFollow.setVisibility(View.VISIBLE);
            followAnim.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
            isFollow = true;
        }
    }

    @Override
    public void changeLikeError() {
        if(contents.get(position).isLike()){
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(context, "点赞失败", Toast.LENGTH_SHORT).show();
            contents.get(position).setLike(false);
        }else{
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(context, "取消点赞失败", Toast.LENGTH_SHORT).show();
            contents.get(position).setLike(true);
        }

    }

    @Override
    public void changeCollectionError() {
        if(contents.get(position).isCollect()) {
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
            contents.get(position).setCollect(false);
        }else{
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
            contents.get(position).setCollect(true);
        }
    }

    @Override
    public void report() {

    }

    @Override
    public void share() {

    }

    @Override
    public void comment() {
        Intent intent = new Intent(context, ArticleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("articleTitle",contents.get(position).getTitle());
        bundle.putString("articleText",contents.get(position).getText());
        bundle.putString("articleDate",contents.get(position).getTime());
        bundle.putString("articleWriterName",contents.get(position).getWriter());
        bundle.putBoolean("isFollow",isFollow);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }


}
