package com.example.shoujiedemo.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.activity.HeartActivity;
import com.example.shoujiedemo.activity.PoemActivity;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

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
    ListView setList;
    Button btnCollect;
    Button dismiss;
    ImageView cover;

    private MyOnClikeListener myOnClikeListener;
    private List<Content> contents = new ArrayList<>();
    private MyFollowOperatePresenter presenter;
    private User user;
    private View setAlterView;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private SetAdapter setAdapter;
    private Set set1;

    private AnimationDrawable loadingDrawable;
    private int position;

    private boolean isFollow = false;
    private boolean isPull = false;

    public HeartViewHodler(@NonNull View itemView, Context context, List<Content> contents) {
        super(itemView);
        this.context = context;
        this.contents = contents;
        presenter = new MyFollowOperatePresenterImpl(this);
        builder = new AlertDialog.Builder(context);
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
        cover = itemView.findViewById(R.id.follow_heart_iv_cover);

        if (contents.get(position).isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);
        if (contents.get(position).isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);

        setAlterView = LayoutInflater.from(context).inflate(R.layout.collect_alterdialog_view,null,false);
        setList = setAlterView.findViewById(R.id.set_list);
        btnCollect = setAlterView.findViewById(R.id.item_btn_collect);
        dismiss = setAlterView.findViewById(R.id.set_btn_dismss);
        alert = builder.create();


        /*Glide.with(context)
                .load( ConfigUtil.BASE_HEAD_URL + contents.get(position).getPic())
                .into(cover);*/
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
        heartContent.setOnClickListener(myOnClikeListener);

    }

    public void initData(int position){
        this.position = position;
        heartContent.setText(contents.get(position).getText());
        likeNum.setText(contents.get(position).getLikes() + "");
        commentNum.setText(contents.get(position).getCheatnum() + "");
        //shareNum.setText(contents.get(position).getForwardnum() + "");
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
        if( contents.get(position).getPic() != null) {
            Glide.with(context)
                    .load(ConfigUtil.BASE_HEAD_URL + contents.get(position).getUser().getPicname())
                    .into(head);
        }
        if(contents.get(position).getUser().getPicname() != null) {
            Glide.with(context)
                    .load(ConfigUtil.BASE_IMG_URL + contents.get(position).getPic())
                    .into(cover);
        }
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
                    if(contents.get(position).isCollect()) {
                        collected.setBackgroundResource(R.drawable.collectionunselect);//取消收藏
                        /*int collectionNums = Integer.parseInt(collectionNum.getText().toString()) -1;
                        contents.get(position).setCollectnum(collectionNums);
                        collectionNum.setText(collectionNums + "");*/
                        contents.get(position).setCollect(false);
                        MsgEvent event = new MsgEvent();
                        event.setId(contents.get(position).getId());
                        event.setType("collect");
                        event.setValue(contents.get(position).isCollect());
                        EventBus.getDefault().postSticky(event);
                        presenter.confirmUnCollect(UserUtil.USER_ID,contents.get(position).getId());
                    }else{
                        presenter.loadSet(UserUtil.USER_ID);
                    }
                    break;
                case R.id.follow_heart_btn_comment:
                        comment(new Comment());
                    break;
                case R.id.follow_heart_btn_like:
                    if(!contents.get(position).isLike()) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        /*int likeNums = Integer.parseInt(likeNum.getText().toString()) + 1;
                        contents.get(position).setLikes(likeNums);
                        likeNum.setText(likeNums + "");*/
                        contents.get(position).setLike(true);
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setId(contents.get(position).getId());
                        event.setValue(contents.get(position).isLike());
                        EventBus.getDefault().postSticky(event);
                        presenter.confirmFavourite(UserUtil.USER_ID,contents.get(position).getId());
                    }else{
                        like.setBackgroundResource(R.drawable.likeunselect);
                        /*int likeNums = Integer.parseInt(likeNum.getText().toString()) - 1;
                        contents.get(position).setLikes(likeNums);
                        likeNum.setText(likeNums + "");*/
                        contents.get(position).setLike(false);
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setId(contents.get(position).getId());
                        event.setValue(contents.get(position).isLike());
                        EventBus.getDefault().postSticky(event);
                        presenter.confirmUnFavourite(UserUtil.USER_ID,contents.get(position).getId());
                    }
                    break;
                case R.id.follow_heart_btn_follow:
                    followAnim.setVisibility(View.VISIBLE);
                    btnFollow.setVisibility(View.INVISIBLE);
                    loadingDrawable =(AnimationDrawable)followAnim.getDrawable();
                    loadingDrawable.start();
                    if(isFollow) {  //关注
                        isFollow = false;
                        presenter.confirmFollow(UserUtil.USER_ID,contents.get(position).getUserid());
                    }else {         //取关
                        isFollow= true;
                        presenter.confirmUnFolly(UserUtil.USER_ID,contents.get(position).getUserid());
                    }
                    break;
                case R.id.follow_heart_tv_cotent:
                    Intent intent2 = new Intent(context,HeartActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("heart",contents.get(position));
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
    public void comment(Comment comment) {
        Intent intent2 = new Intent(context,HeartActivity.class);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("heart",contents.get(position));
        bundle2.putBoolean("isFollow",isFollow);
        bundle2.putSerializable("user",contents.get(position).getUser());
        bundle2.putInt("position",position);
        intent2.putExtra("bundle",bundle2);
        context.startActivity(intent2);
    }

    @Override
    public void loadComment(List<Comment> commentList) {

    }

    @Override
    public void deleteComment() {

    }


    @Override
    public void showSet(final List<Set> sets) {
        setAdapter = new SetAdapter(sets,context);
        setList.setAdapter(setAdapter);
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.confirmCollect(UserUtil.USER_ID,contents.get(position).getId());
                //Toast.makeText(context,"" + set1.getName(),Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });

        setList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                set1 = (Set) setAdapter.getItem(i);
                view.setBackgroundColor(0x30CFCFCF);

            }
        });

        alert.show();
        Window window = alert.getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(setAlterView);
        window.setLayout(800,1000);

    }

    @Override
    public void collect() {
        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
        /*int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
        contents.get(position).setCollectnum(collectionNums);
        collectionNum.setText(collectionNums + "");*/
        contents.get(position).setCollect(true);
        MsgEvent event = new MsgEvent();
        event.setId(contents.get(position).getId());
        event.setType("collect");
        event.setValue(contents.get(position).isCollect());
        EventBus.getDefault().postSticky(event);
        Toast.makeText(context,"收藏成功" + set1.getName(),Toast.LENGTH_SHORT).show();
    }
}