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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.activity.ArticleActivity;
import com.example.shoujiedemo.activity.MusicActivity;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.bean.MusicEvent;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.service.NetPlayMusicService;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.presenter.MyMusicPresenterImpl;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.MySpaceActivity;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.MusicPlayUtil;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MusicViewHodler  extends RecyclerView.ViewHolder implements ContentView {


    private Context context;
    private List<Music> musicList = new ArrayList<>();
    ImageView head;
    TextView userName;
    TextView set;
    TextView fanNum;
    Button btnFollow;
    Button btnPull;
    View lnDelete;
    Button collected;
    TextView collectionNum;
    Button comment;
    TextView commentNum;
    Button like;
    TextView likeNum;
    View popuMenu;
    ImageView followAnim;
    TextView songName;
    TextView singer;
    Button btnPlay;
    ImageView musicCover;
    View itemMusic;
    TextView text;
    TextView date;
    View lnReport;


    private User user;
    private int position;

    private MyFollowOperatePresenter presenter;

    private AnimationDrawable loadingDrawable;
    private MyOnClikListener myOnClikListener;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private View confirmDelete;
    private Button btnDelete;
    private Button dismissDelete;
    private ImageView loading;
    private Animation animation;
    private RecyclerView.Adapter adapter;

    private boolean isPull = false;

    public MusicViewHodler(@NonNull View itemView, Context context, List<Music> musicList,RecyclerView.Adapter adapter) {
        super(itemView);
        this.context = context;
        this.musicList = musicList;
        this.context = context;
        this.adapter = adapter;
        builder = new AlertDialog.Builder(context);
        presenter = new MyMusicPresenterImpl(this);
        initView();
        setMyOnClikListenser();
    }

    private void initView() {

        head = itemView.findViewById(R.id.follow_music_iv_head);
        userName = itemView.findViewById(R.id.follow_music_tv_user_name);
        fanNum = itemView.findViewById(R.id.follow_music_tv_fan);
        btnFollow = itemView.findViewById(R.id.follow_music_btn_follow);
        btnPull = itemView.findViewById(R.id.follow_music_btn_pull);
        lnDelete = itemView.findViewById(R.id.follow_music_ln_delete);
        collected = itemView.findViewById(R.id.follow_music_btn_collect);
        collectionNum = itemView.findViewById(R.id.follow_music_tv_collect_num);
        comment = itemView.findViewById(R.id.follow_music_btn_comment);
        commentNum = itemView.findViewById(R.id.follow_music_tv_comment_num);
        like = itemView.findViewById(R.id.follow_music_btn_like);
        likeNum = itemView.findViewById(R.id.follow_music_tv_like_num);
        popuMenu = itemView.findViewById(R.id.follow_music_pull_menu);
        followAnim = itemView.findViewById(R.id.follow_music_iv_follow_anim);
        songName = itemView.findViewById(R.id.item_songName);
        singer = itemView.findViewById(R.id.item_music_songer);
        lnReport = itemView.findViewById(R.id.follow_music_ln_delete);
        btnPlay = itemView.findViewById(R.id.item_play);
        musicCover = itemView.findViewById(R.id.item_music_cover);
        text = itemView.findViewById(R.id.item_music_text);
        date = itemView.findViewById(R.id.item_music_date);
        itemMusic = itemView.findViewById(R.id.item_music);
        confirmDelete = LayoutInflater.from(context).inflate(R.layout.confirm_delete_view,null,false);
        btnDelete = confirmDelete.findViewById(R.id.delete_content_item_btn_commit);
        dismissDelete = confirmDelete.findViewById(R.id.delete_content_item_btn_dismiss);
        loading = confirmDelete.findViewById(R.id.delete_content_loading);

        alert = builder.create();


        if(musicList.get(position).getUser().getId() == UserUtil.USER_ID)
            btnFollow.setVisibility(View.INVISIBLE);
        else
            btnFollow.setVisibility(View.VISIBLE);

        if(musicList.get(position).getUser().getId() != UserUtil.USER_ID)
            btnPull.setVisibility(View.GONE);
        else
            btnPull.setVisibility(View.VISIBLE);

        if(MusicPlayUtil.MUSIC_IS_PLAY == musicList.get(position).getId() && (!MusicPlayUtil.IS_PAUSE && !MusicPlayUtil.IS_STOP)){
            btnPlay.setBackgroundResource(R.drawable.play);
        }else{
            btnPlay.setBackgroundResource(R.drawable.pause);
        }

        if (musicList.get(position).isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);


        if (musicList.get(position).isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        if(musicList.get(position).getUser().isFollow())
            btnFollow.setText("关注+");
        else
            btnFollow.setText("已关注");


    }

    public void initData(int position){
        this.position = position;
        likeNum.setText(musicList.get(position).getLikes() + "");
        commentNum.setText(musicList.get(position).getCheatnum() + "");
        collectionNum.setText(musicList.get(position).getCollectnum() + "");
        user = musicList.get(position).getUser();
        userName.setText(user.getName());
        fanNum.setText(user.getFennum() + "");
        text.setText(musicList.get(position).getText());
        singer.setText(musicList.get(position).getSinger());
        songName.setText(musicList.get(position).getName());
        date.setText(musicList.get(position).getTime());


        if(musicList.get(position).getUser().getId() == UserUtil.USER_ID)
            btnFollow.setVisibility(View.INVISIBLE);
        else
            btnFollow.setVisibility(View.VISIBLE);

        if(musicList.get(position).getUser().getId() != UserUtil.USER_ID)
            btnPull.setVisibility(View.GONE);
        else
            btnPull.setVisibility(View.VISIBLE);

        if(MusicPlayUtil.MUSIC_IS_PLAY == musicList.get(position).getId() && (!MusicPlayUtil.IS_PAUSE && !MusicPlayUtil.IS_STOP)){
            btnPlay.setBackgroundResource(R.drawable.pause);
        }else{
            btnPlay.setBackgroundResource(R.drawable.play);
        }

        if(user.isFollow())
            btnFollow.setText("关注+");
        else
            btnFollow.setText("已关注");

        if (musicList.get(position).isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        if (musicList.get(position).isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.iv_default)
                .fallback(R.drawable.ouran_default)
                .centerCrop();

        if(musicList.get(position).getPic() != null) {
            Glide.with(context)
                    .load( musicList.get(position).getPic())
                    .apply(requestOptions)
                    .into(musicCover);
        }

        if(musicList.get(position).getUser().getPicname() != null) {
            Glide.with(context)
                    .load(ConfigUtil.BASE_HEAD_URL + musicList.get(position).getUser().getPicname())
                    .apply(requestOptions)
                    .into(head);
        }
    }

    /**
     * onclik内部类
     */
    public void setMyOnClikListenser(){
        myOnClikListener = new MyOnClikListener();
        head.setOnClickListener(myOnClikListener);
        like.setOnClickListener(myOnClikListener);
        collected.setOnClickListener(myOnClikListener);
        btnPull.setOnClickListener(myOnClikListener);
        btnFollow.setOnClickListener(myOnClikListener);
        itemMusic.setOnClickListener(myOnClikListener);
        btnPlay.setOnClickListener(myOnClikListener);
        lnReport.setOnClickListener(myOnClikListener);
        btnDelete.setOnClickListener(myOnClikListener);
        dismissDelete.setOnClickListener(myOnClikListener);
    }


    class MyOnClikListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.follow_music_iv_head://点击头像进入用户空间
                    Intent intent1 = new Intent(context, MySpaceActivity.class);
                    intent1.putExtra("user",user);
                    context.startActivity(intent1);
                    break;
                case R.id.follow_article_tv_set:

                    break;
                case R.id.follow_music_btn_pull://点击弹出下拉菜单
                    if(isPull){
                        popuMenu.setVisibility(View.GONE);
                        isPull = false;
                    }else{
                        popuMenu.setVisibility(View.VISIBLE);
                        isPull = true;
                    }
                    break;
                case R.id.follow_music_ln_delete:
                    alert.show();
                    Window window = alert.getWindow();
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.setContentView(confirmDelete);
                    window.setLayout(700,500);
                    break;
                case R.id.delete_content_item_btn_commit:
                    loading.setVisibility(View.VISIBLE);
                    animation = AnimationUtils.loadAnimation(context, R.anim.loading_music_anim_rotate);
                    LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
                    animation.setInterpolator(lin);
                    loading.startAnimation(animation);
                    presenter.deleteContent(musicList.get(position).getId());
                    break;
                case R.id.delete_content_item_btn_dismiss:
                    popuMenu.setVisibility(View.GONE);
                    isPull = false;
                    alert.dismiss();
                case R.id.follow_music_btn_collect:
                    if(musicList.get(position).isCollect()) {
                        collected.setBackgroundResource(R.drawable.collectionunselect);//取消收藏
                        musicList.get(position).setCollect(false);
                        /*MsgEvent event = new MsgEvent();
                        event.setId(musicList.get(position).getId());
                        event.setType("collect");
                        event.setValue(musicList.get(position).isCollect());
                        EventBus.getDefault().postSticky(event);*/
                        int collectNums = Integer.parseInt(collectionNum.getText().toString());
                        musicList.get(position).setCollectnum(collectNums - 1);
                        collectionNum.setText(musicList.get(position).getCollectnum() + "");
                        presenter.confirmUnCollect(UserUtil.USER_ID,musicList.get(position).getId());
                    }else{
                        //presenter.loadSet(UserUtil.USER_ID);
                        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
                        musicList.get(position).setCollect(true);
                        int collectNums = Integer.parseInt(collectionNum.getText().toString());
                        musicList.get(position).setCollectnum(collectNums + 1);
                        collectionNum.setText(musicList.get(position).getCollectnum() + "");
                        presenter.confirmCollect(UserUtil.USER_ID,musicList.get(position).getId());
                    }
                    break;
                case R.id.follow_music_btn_comment://评论按钮跳转到详情界面
                    comment(new Comment());
                    break;
                case R.id.follow_music_btn_like://点赞
                    if(!musicList.get(position).isLike()) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        musicList.get(position).setLike(true);
                        /*MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setId(musicList.get(position).getId());
                        event.setValue(musicList.get(position).isLike());
                        EventBus.getDefault().postSticky(event);*/
                        int likeNums = Integer.parseInt(likeNum.getText().toString());
                        musicList.get(position).setLikes(likeNums + 1);
                        likeNum.setText(musicList.get(position).getLikes() + "");
                        presenter.confirmFavourite(UserUtil.USER_ID,musicList.get(position).getId());
                    }else{
                        like.setBackgroundResource(R.drawable.likeunselect);
                        musicList.get(position).setLike(false);
                        /*MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setId(musicList.get(position).getId());
                        event.setValue(musicList.get(position).isLike());
                        EventBus.getDefault().postSticky(event);*/
                        int likeNums = Integer.parseInt(likeNum.getText().toString());
                        musicList.get(position).setLikes(likeNums - 1);
                        likeNum.setText(musicList.get(position).getLikes() + "");
                        presenter.confirmUnFavourite(UserUtil.USER_ID,musicList.get(position).getId());
                    }
                    break;
                case R.id.follow_music_btn_follow:
                    followAnim.setVisibility(View.VISIBLE);
                    btnFollow.setVisibility(View.INVISIBLE);
                    loadingDrawable =(AnimationDrawable)followAnim.getDrawable();
                    loadingDrawable.start();
                    if(user.isFollow()) {  //关注
                        user.setFollow(false);
                        MsgEvent event = new MsgEvent();
                        event.setType("follow");
                        event.setId(user.getId());
                        event.setValue(user.isFollow());
                        EventBus.getDefault().postSticky(event);
                        presenter.confirmFollow(UserUtil.USER_ID,musicList.get(position).getUserid());
                    }else {         //取关
                        user.setFollow(true);
                        MsgEvent event = new MsgEvent();
                        event.setType("follow");
                        event.setId(user.getId());
                        event.setValue(user.isFollow());
                        EventBus.getDefault().postSticky(event);
                        presenter.confirmUnFolly(UserUtil.USER_ID,musicList.get(position).getUserid());
                    }
                    break;
                case R.id.item_music:
                    Intent intent2 = new Intent(context, MusicActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("music",musicList.get(position));
                    bundle2.putSerializable("user",musicList.get(position).getUser());
                    intent2.putExtra("bundle",bundle2);
                    context.startActivity(intent2);
                    break;
                case R.id.item_play:
                    musicList.get(position).setStop(false);
                    EventBus.getDefault().postSticky(musicList.get(position).getUser());
                    if(musicList.get(position).getStart() == 0){
                        btnPlay.setBackgroundResource(R.drawable.pause);
                        Intent intent = new Intent(context, NetPlayMusicService.class);
                        Bundle bundle = new Bundle();
                        Music music = musicList.get(position);
                        musicList.get(position).setPause(false);
                        music.setPause(false);
                        music.setTag(0);
                        musicList.get(position).setStart(1);
                        bundle.putSerializable("music", music);
                        intent.putExtra("bundle", bundle);
                        context.startService(intent);
                    }else if(musicList.get(position).getStart() == 1){
                        if(MusicPlayUtil.MUSIC_IS_PLAY == musicList.get(position).getId() && MusicPlayUtil.IS_PAUSE){
                            btnPlay.setBackgroundResource(R.drawable.play);
                            MusicEvent event = new MusicEvent();
                            event.setTag(3);
                            EventBus.getDefault().postSticky(event);
                            musicList.get(position).setPause(false);
                        }else {
                            btnPlay.setBackgroundResource(R.drawable.play);
                            MusicEvent event = new MusicEvent();
                            event.setTag(1);
                            EventBus.getDefault().postSticky(event);
                            musicList.get(position).setPause(true);
                        }
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
            user.setFollow(true);
        }
    }

    @Override
    public void setUnFollyError() {
        if(loadingDrawable.isRunning()) {
            loadingDrawable.stop();
            btnFollow.setVisibility(View.VISIBLE);
            followAnim.setVisibility(View.INVISIBLE);
            Toast.makeText(context,"取消关注失败",Toast.LENGTH_SHORT).show();
            user.setFollow(false);
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
            user.setFollow(false);
        }
    }

    @Override
    public void setFollowError() {
        if(loadingDrawable.isRunning()){
            loadingDrawable.stop();
            btnFollow.setVisibility(View.VISIBLE);
            followAnim.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
            user.setFollow(true);
        }
    }

    @Override
    public void changeLikeError() {
        if(musicList.get(position).isLike()){
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(context, "点赞失败", Toast.LENGTH_SHORT).show();
            musicList.get(position).setLike(false);
        }else{
            like.setBackgroundResource(R.drawable.likeselected);
            Toast.makeText(context, "取消点赞失败", Toast.LENGTH_SHORT).show();
            musicList.get(position).setLike(true);
        }

    }

    @Override
    public void changeCollectionError() {
        if(musicList.get(position).isCollect()) {
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
            musicList.get(position).setCollect(false);
        }else{
            collected.setBackgroundResource(R.drawable.collectionunselect);
            Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
            musicList.get(position).setCollect(true);
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
        Intent intent2 = new Intent(context, MusicActivity.class);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("music",musicList.get(position));
        bundle2.putSerializable("user",musicList.get(position).getUser());
        intent2.putExtra("bundle",bundle2);
        context.startActivity(intent2);
    }

    @Override
    public void collect() {
        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
        /*int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
        contents.get(position).setCollectnum(collectionNums);
        collectionNum.setText(collectionNums + "");*/
        musicList.get(position).setCollect(true);
        /*MsgEvent event = new MsgEvent();
        event.setId(musicList.get(position).getId());
        event.setType("collect");
        event.setValue(musicList.get(position).isCollect());
        EventBus.getDefault().postSticky(event);*/
        Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadComment(List<Comment> commentList) {

    }

    @Override
    public void deleteComment() {

    }

    @Override
    public void noSet() {

    }


    @Override
    public void showSet(List<Set> sets) {

    }

    @Override
    public void deleteContent() {
        loading.clearAnimation();
        popuMenu.setVisibility(View.GONE);
        isPull = false;
        alert.dismiss();
        musicList.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteError() {
        Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
    }


}
