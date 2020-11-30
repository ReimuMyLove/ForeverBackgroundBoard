package com.example.shoujiedemo.home.follow.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Article;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.adapter.ArticleViewHodler;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;

import org.greenrobot.eventbus.EventBus;

public class ArticleActivity extends AppCompatActivity implements ContentView {

    private TextView title;
    private TextView userName;
    private TextView date;
    private TextView text;
    private ImageView head;
    private TextView set;
    private TextView fanNum;
    private Button btnFollow;
    private TextView tag ;
    private Button share;
    private TextView shareNum;
    private Button collected;
    private TextView collectionNum;
    private Button comment;
    private TextView commentNum;
    private Button like;
    private TextView likeNum;
    private ImageView followAnim;

    private Content article;
    private User user;

    private boolean isFollow = false;
    private int position;

    private MyFollowOperatePresenter presenter;
    private AnimationDrawable loadingDrawable;
    private MyOnClikListener myOnClikListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initData();
        initView();
        setOnClikListener();

    }

    private void setOnClikListener() {
        myOnClikListener = new MyOnClikListener();
        //head.setOnClickListener(myOnClikListener);
       //set.setOnClickListener(myOnClikListener);
        like.setOnClickListener(myOnClikListener);
        collected.setOnClickListener(myOnClikListener);
        btnFollow.setOnClickListener(myOnClikListener);
        share.setOnClickListener(myOnClikListener);
    }

    private void initView() {
        head = findViewById(R.id.iv_head_details_article);
        title = findViewById(R.id.tv_title_details_article);
        date = findViewById(R.id.tv_date_details_article);
        text = findViewById(R.id.tv_content_details_article);
        like = findViewById(R.id.follow_article_details_btn_like);
        userName = findViewById(R.id.tv_userName_details_article);

        if (article.isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        likeNum = findViewById(R.id.follow_article_details_tv_like_num);
        collected = findViewById(R.id.follow_article_details_btn_collection);

        if (article.isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);

        collectionNum = findViewById(R.id.follow_article_details_tv_collection_num);
        comment = findViewById(R.id.follow_article_details_btn_comment);
        commentNum = findViewById(R.id.follow_article_details_tv_comment_num);
        share  = findViewById(R.id.follow_article_details_btn_share);
        shareNum = findViewById(R.id.follow_article_details_tv_share_num);
        followAnim = findViewById(R.id.follow_article_details_iv_follow_anim);
        fanNum = findViewById(R.id.tv_fanNum_details_article);
        btnFollow = findViewById(R.id.follow_article_details_btn_follow);
        if(isFollow){
            btnFollow.setText("关注+");
        }else{
            btnFollow.setText("已关注");
        }
        text.setText(article.getText());
        title.setText(article.getTitle());
        date.setText(article.getTime());
        fanNum.setText(new StringBuilder().append(user.getFennum()));
        likeNum.setText(new StringBuilder().append(article.getLikes()));
        collectionNum.setText(new StringBuilder().append(article.getCollectnum()));
        shareNum.setText(new StringBuilder().append(article.getForwardnum()));
        commentNum.setText(new StringBuilder().append(article.getCheatnum()));
        userName.setText(user.getName());
    }

    public void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        article = (Content)bundle.getSerializable("article");
        isFollow = bundle.getBoolean("isFollow");
        user = (User)bundle.getSerializable("user");
        position = bundle.getInt("position");
        presenter = new MyFollowOperatePresenterImpl(this);

    }

    class MyOnClikListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.follow_article_iv_head://点击头像进入用户空间

                    break;
                case R.id.follow_article_tv_set://点击文集进入用户空间

                    break;
                case R.id.follow_article_details_btn_share://分享

                    break;
                case R.id.follow_article_details_btn_collection://收藏
                    if(article.isCollect()) {
                        collected.setBackgroundResource(R.drawable.collectionunselect);//取消收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) -1;
                        collectionNum.setText(new StringBuilder().append(collectionNums));
                        article.setCollect(false);
                        presenter.confirmUnCollect();
                        MsgEvent event = new MsgEvent();
                        event.setType("collect");
                        event.setValue(article.isCollect());
                        event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }else{
                        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
                        collectionNum.setText(new StringBuilder().append(collectionNums));
                        article.setCollect(true);
                        presenter.confirmCollect();
                        MsgEvent event = new MsgEvent();
                        event.setType("collect");
                        event.setValue(article.isCollect());
                        event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }
                    break;
                case R.id.follow_article_details_btn_comment://评论按钮跳转到详情界面
                    comment();
                    break;
                case R.id.follow_article_details_btn_like://点赞
                    if(!article.isLike()) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) + 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        article.setLike(true);
                        presenter.confirmFavourite();
                        //通知点赞按钮改变
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setValue(article.isLike());
                        event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }else{
                        like.setBackgroundResource(R.drawable.likeunselect);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) - 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        article.setLike(false);
                        presenter.confirmUnFavourite();
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setValue(article.isLike());
                        event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }
                    break;
                case R.id.follow_article_details_btn_follow:
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
            Toast.makeText(getApplicationContext(),"取消关注",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"取消关注失败",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
            isFollow= false;
        }
    }

    @Override
    public void setFollowError() {
        if(loadingDrawable.isRunning()){
            loadingDrawable.stop();
            btnFollow.setVisibility(View.VISIBLE);
            followAnim.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_SHORT).show();
            isFollow = true;
        }
    }

    @Override
    public void changeLikeError() {
        if(article.isLike()){
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(getApplicationContext(), "点赞失败", Toast.LENGTH_SHORT).show();
            article.setLike(false);
        }else{
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(getApplicationContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();
            article.setLike(true);
        }

    }

    @Override
    public void changeCollectionError() {
        if(article.isCollect()) {
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(getApplicationContext(), "收藏失败", Toast.LENGTH_SHORT).show();
            article.setCollect(false);
        }else{
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(getApplicationContext(), "取消收藏失败", Toast.LENGTH_SHORT).show();
            article.setCollect(true);
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