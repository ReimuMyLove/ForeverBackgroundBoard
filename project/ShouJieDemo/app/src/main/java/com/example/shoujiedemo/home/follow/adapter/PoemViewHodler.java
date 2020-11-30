package com.example.shoujiedemo.home.follow.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.activity.ArticleActivity;
import com.example.shoujiedemo.home.follow.activity.PoemActivity;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;

import java.util.ArrayList;
import java.util.List;

public class PoemViewHodler  extends RecyclerView.ViewHolder implements ContentView {

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
    TextView poemTile;
    TextView writerName;
    TextView poemContent;
    ImageView followAnim;
    View lnPoem;

    private AnimationDrawable loadingDrawable;
    private int position;

    private MyFollowOperatePresenter presenter;
    private User user;

    private Context context;

    private boolean isFollow = false;
    private boolean isPull = false;

    private MyOnClikeListener myOnClikeListener;
    private List<Content> contents = new ArrayList<>();

    public PoemViewHodler(@NonNull View itemView, Context context, List<Content> contents) {
        super(itemView);
        this.context = context;
        this.contents = contents;
        presenter = new MyFollowOperatePresenterImpl(this);
        initView();
        setMyOnClikeListenser();
    }

    private void initView() {
        poemTile = itemView.findViewById(R.id.follow_poem_tv_title);
        poemContent = itemView.findViewById(R.id.follow_poem_tv_cotent);
        writerName = itemView.findViewById(R.id.follow_poem_tv_writer_name);
        head = itemView.findViewById(R.id.follow_poem_iv_head);
        userName = itemView.findViewById(R.id.follow_poem_tv_user_name);
        set = itemView.findViewById(R.id.follow_poem_tv_set);
        fanNum = itemView.findViewById(R.id.follow_poem_tv_fan);
        btnFollow = itemView.findViewById(R.id.follow_poem_btn_follow);
        btnPull = itemView.findViewById(R.id.follow_poem_btn_pull);
        lnReport = itemView.findViewById(R.id.follow_poem_ln_report);
        unLike = itemView.findViewById(R.id.follow_poem_tv_unLike);
        tag = itemView.findViewById(R.id.follow_article_tv_tag);
        share = itemView.findViewById(R.id.follow_poem_btn_share);
        shareNum = itemView.findViewById(R.id.follow_poem_tv_share_num);
        collected = itemView.findViewById(R.id.follow_poem_btn_collection);
        lnPoem = itemView.findViewById(R.id.follow_poem_ln_content);

        if (contents.get(position).isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);

        collectionNum = itemView.findViewById(R.id.follow_poem_tv_collection_num);
        comment = itemView.findViewById(R.id.follow_poem_btn_comment);
        commentNum = itemView.findViewById(R.id.follow_poem_tv_comment_num);
        like = itemView.findViewById(R.id.follow_poem_btn_like);

        if (contents.get(position).isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        likeNum = itemView.findViewById(R.id.follow_poem_tv_like_num);
        popuMenu = itemView.findViewById(R.id.follow_poem_pull_menu);
        followAnim = itemView.findViewById(R.id.follow_poem_iv_follow_anim);
    }

    public void initData(int position){
        this.position = position;
       poemContent.setText(contents.get(position).getText());
       poemTile.setText(contents.get(position).getTitle());
       writerName.setText(contents.get(position).getWriter());
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
        lnPoem.setOnClickListener(myOnClikeListener);
    }

    class MyOnClikeListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.follow_poem_iv_head://点击头像进入用户空间
                    break;
                case R.id.follow_poem_tv_set://点击文集进入用户空间

                    break;
                case R.id.follow_poem_btn_pull://点击弹出下拉菜单
                    if(isPull){
                        popuMenu.setVisibility(View.GONE);
                        isPull = false;
                    }else{
                        popuMenu.setVisibility(View.VISIBLE);
                        isPull = true;
                    }
                    break;
                case R.id.follow_poem_ln_report:

                    break;
                case R.id.follow_poem_btn_share:

                    break;
                case R.id.follow_poem_btn_collection:
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
                case R.id.follow_poem_btn_comment:

                    break;
                case R.id.follow_poem_btn_like:
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
                case R.id.follow_poem_btn_follow:
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
                case R.id.follow_poem_ln_content:
                    Intent intent2 = new Intent(context, PoemActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("poem",contents.get(position));
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

    }
}
