package com.example.shoujiedemo.home.follow.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.activity.ArticleActivity;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;

import java.util.ArrayList;
import java.util.List;

public class HeartViewHodler  extends RecyclerView.ViewHolder implements ContentView {

    ImageView head;
    TextView userName;
    TextView set;
    TextView fanNum;
    Button btnFollow;
    Button btnPull;
    View lnReport;
    TextView unLike;
    TextView shareNum;
    Button collected;
    TextView collectionNum;
    Button comment;
    TextView commentNum;
    Button like;
    TextView likeNum;
    View popuMenu;
    TextView heartContent;
    ImageView followAnim;
    private Context context;

    private MyOnClikeListener myOnClikeListener;
    private List<Content> contents = new ArrayList<>();
    private MyFollowOperatePresenter presenter;
    private User user;

    private AnimationDrawable loadingDrawable;

    private boolean isLike = false;
    private boolean isCollect = false;
    private boolean isFollow = false;
    private boolean isPull = false;

    public HeartViewHodler(@NonNull View itemView, Context context, List<Content> contents) {
        super(itemView);
        this.context = context;
        this.contents = contents;
        presenter = new MyFollowOperatePresenterImpl(this);
        initView();
        setMyOnClikeListenser();
    }

    private void initView() {
        head = itemView.findViewById(R.id.follow_heart_iv_head);
        userName = itemView.findViewById(R.id.follow_heart_tv_user_name);
        set = itemView.findViewById(R.id.follow_heart_tv_set);
        fanNum = itemView.findViewById(R.id.follow_heart_tv_fan);
        btnFollow = itemView.findViewById(R.id.follow_heart_btn_follow);
        btnPull = itemView.findViewById(R.id.follow_heart_btn_pull);
        lnReport = itemView.findViewById(R.id.follow_heart_ln_report);
        unLike = itemView.findViewById(R.id.follow_heart_tv_unLike);
        collected = itemView.findViewById(R.id.follow_heart_btn_collection);
        collectionNum = itemView.findViewById(R.id.follow_heart_tv_collection_num);
        comment = itemView.findViewById(R.id.follow_heart_btn_comment);
        commentNum = itemView.findViewById(R.id.follow_heart_tv_comment_num);
        like = itemView.findViewById(R.id.follow_heart_btn_like);
        likeNum = itemView.findViewById(R.id.follow_heart_tv_like_num);
        popuMenu = itemView.findViewById(R.id.follow_heart_pull_menu);
        heartContent = itemView.findViewById(R.id.follow_heart_tv_cotent);
        followAnim = itemView.findViewById(R.id.follow_heart_iv_follow_anim);
    }

    public void setMyOnClikeListenser(){
        myOnClikeListener = new MyOnClikeListener();
        head.setOnClickListener(myOnClikeListener);
        set.setOnClickListener(myOnClikeListener);
        like.setOnClickListener(myOnClikeListener);
        collected.setOnClickListener(myOnClikeListener);
        btnPull.setOnClickListener(myOnClikeListener);
        btnFollow.setOnClickListener(myOnClikeListener);
        btnPull.setOnClickListener(myOnClikeListener);
    }

    public void initData(int position){
        heartContent.setText(contents.get(position).getText());
        likeNum.setText(contents.get(position).getLikes() + "");
        commentNum.setText(contents.get(position).getCheatnum() + "");
        //shareNum.setText(contents.get(position).getForwardnum() + "");
        collectionNum.setText(contents.get(position).getCollectnum() + "");

        user = contents.get(position).getUser();
        userName.setText(user.getName());
        fanNum.setText(user.getFennum() + "");
    }

    class MyOnClikeListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.follow_heart_iv_head://点击头像进入用户空间
                    break;
                case R.id.follow_heart_tv_set://点击文集进入用户空间

                    break;
                case R.id.follow_heart_btn_pull://点击弹出下拉菜单
                    if(isPull){
                        popuMenu.setVisibility(View.GONE);
                        isPull = false;
                    }else{
                        popuMenu.setVisibility(View.VISIBLE);
                        isPull = true;
                    }
                    break;
                case R.id.follow_heart_ln_report:
                    break;
                case R.id.follow_heart_btn_collection:
                    if(isCollect) {
                        collected.setBackgroundResource(R.drawable.collectionunselect);//取消收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) -1;
                        collectionNum.setText(new StringBuilder().append(collectionNums));
                        isCollect = false;
                        presenter.confirmUnCollect();
                    }else{
                        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
                        collectionNum.setText(new StringBuilder().append(collectionNums));
                        isCollect = true;
                        presenter.confirmCollect();
                    }
                    break;
                case R.id.follow_heart_btn_comment:

                    break;
                case R.id.follow_heart_btn_like:
                    if(!isLike) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) + 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        isLike = true;
                        presenter.confirmFavourite();
                    }else{
                        like.setBackgroundResource(R.drawable.likeunselect);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) - 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        isLike = false;
                        presenter.confirmUnFavourite();
                    }
                    break;
                case R.id.follow_heart_btn_follow:
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
        if(isLike){
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(context, "点赞失败", Toast.LENGTH_SHORT).show();
            isLike = false;
        }else{
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(context, "取消点赞失败", Toast.LENGTH_SHORT).show();
            isLike = true;
        }

    }

    @Override
    public void changeCollectionError() {
        if(isCollect) {
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
            isCollect = false;
        }else{
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
            isCollect = true;
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

    }

    @Override
    public void loadComment(List<Comment> commentList) {

    }
}
