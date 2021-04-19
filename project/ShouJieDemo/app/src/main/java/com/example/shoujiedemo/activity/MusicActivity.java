package com.example.shoujiedemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.CommentAdapter;
import com.example.shoujiedemo.adapter.SetAdapter;
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
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.MusicPlayUtil;
import com.example.shoujiedemo.util.UserUtil;
import com.example.shoujiedemo.util.ViewWrapper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity implements ContentView {

    private AppCompatSeekBar seekBar;
    private Button btnPaly;
    private ImageView songCover;
    private int totalTime;
    private int currentTime;
    private TextView tvCurrentTime;
    private TextView tvTotalTime;
    private NestedScrollView scrollView;
    private RecyclerView rlComment;
    private TextView userName;
    private TextView date;
    private TextView text;
    private ImageView head;
    private TextView set;
    private TextView fanNum;
    private Button btnFollow;
    private TextView tag ;
    private Button collected;
    private TextView collectionNum;
    private Button comment;
    private TextView commentNum;
    private Button like;
    private TextView likeNum;
    private ImageView followAnim;
    private EditText edComment;
    private Button btnSendComment;
    private TextView commentNum2;
    private TextView likeNum2;
    private TextView collectNum2;
    private CommentAdapter commentAdapter;
    private View operation;
    private Button btnCollect;
    private boolean isInitComment = true;
    private int pageNum = 1;
    private SmartRefreshLayout refreshLayout;
    private Comment deleteComment;
    private TextView songName;
    private TextView singer;
    private ImageView musicCover;
    private boolean animFinish = false;

    private boolean isFollow = false;

    private MyFollowOperatePresenter presenter;
    private AnimationDrawable loadingDrawable;
    private MyOnClikListener myOnClikListener;
    private List<Comment> commentList = new ArrayList<>();

    private User user;
    private Music music;

    private int screenHeight;//屏幕高度
    private int commentListHeight;//评论列表长度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        EventBus.getDefault().register(this);
        presenter = new MyMusicPresenterImpl(this);

        initView();
        setOnClikListener();
        getHeightToAnim();

        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(false);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadComment(music.getId(),pageNum);
                refreshLayout.finishLoadMore(600);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i % 60 < 10)
                    tvCurrentTime.setText(i / 60 + ":0" + i% 60);
                else
                    tvCurrentTime.setText(i / 60 + ":" + i % 60);
                //currentTime = i;
                music.setCurrenttime(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("stop","开始滑动");
                MusicEvent event = new MusicEvent();
                event.setCurrentTime(music.getCurrenttime());
                //event.setDuration(totalTime);
                event.setTag(4);
                EventBus.getDefault().postSticky(event);

            }
        });


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            AnimatorSet animatorSet = new AnimatorSet();
            AnimatorSet animatorSet1 = new AnimatorSet();
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if(oldScrollY > scrollY){//上滑
                    if(!animatorSet.isRunning() && !animatorSet1.isRunning()) {
                        if (scrollY <= text.getBottom()) {
                            if (animFinish) {
                                ViewWrapper viewWrapper = new ViewWrapper(edComment);
                                ObjectAnimator animator = ObjectAnimator.ofInt(viewWrapper, "trueWidth",
                                        550, 0);
                                ObjectAnimator animator2 = ObjectAnimator.ofFloat(collected, "translationX",
                                        480, 0);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(like, "translationX",
                                        400, 0);
                                ObjectAnimator animtor4 = ObjectAnimator.ofFloat(comment, "alpha",
                                        0, 0.25f, 0.5f, 1)
                                        .setDuration(250);
                                animtor4.start();
                                ObjectAnimator animator5 = ObjectAnimator.ofFloat(btnSendComment, "alpha", 1, 0.5f, 0.25f, 0)
                                        .setDuration(250);
                                animator5.start();
                                animatorSet.playTogether(animator ,animator2, animator3);
                                animatorSet.setDuration(250);
                                animatorSet.start();
                                edComment.setHint("");
                                animator.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        collectionNum.setVisibility(View.VISIBLE);
                                        commentNum.setVisibility(View.VISIBLE);
                                        likeNum.setVisibility(View.VISIBLE);
                                        comment.setVisibility(View.VISIBLE);
                                        animFinish = false;
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                            }
                        }
                    }
                }

                if(oldScrollY < scrollY) {//下滑
                    if(!animatorSet.isRunning() && !animatorSet1.isRunning()) {
                        if (scrollY >= (rlComment.getTop() - (screenHeight - commentListHeight)) || scrollY >= (rlComment.getTop() - screenHeight)) {
                            if (!animFinish) {
                                ViewWrapper viewWrapper = new ViewWrapper(edComment);
                                ObjectAnimator animator = ObjectAnimator.ofInt(viewWrapper, "trueWidth",
                                        0, 550);
                                ObjectAnimator animator2 = ObjectAnimator.ofFloat(collected, "translationX",
                                        0, 480);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(like, "translationX",
                                        0, 400);
                                ObjectAnimator animtor4 = ObjectAnimator.ofFloat(comment, "alpha",
                                        1, 0.5f, 0)
                                        .setDuration(250);
                                animtor4.start();
                                ObjectAnimator animator5 = ObjectAnimator.ofFloat(btnSendComment, "alpha", 0, 0.5f, 1)
                                        .setDuration(250);
                                animator5.start();
                                animatorSet1.playTogether(animator,animator2, animator3);
                                animatorSet1.setDuration(250);
                                animatorSet1.start();
                                btnSendComment.setVisibility(View.VISIBLE);
                                animFinish = true;
                                commentNum.setVisibility(View.INVISIBLE);
                                likeNum.setVisibility(View.INVISIBLE);
                                collectionNum.setVisibility(View.INVISIBLE);
                                animatorSet1.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        edComment.setHint("写回复:");
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                            }
                        }
                    }
                }

            }
        });



        btnPaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music.getStart() == 0) {
                    btnPaly.setBackgroundResource(R.drawable.pause);
                    Intent intent = new Intent(MusicActivity.this, NetPlayMusicService.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("music",music);
                    intent.putExtra("bundle",bundle);
                    //String url = music.getPath();
                    //intent.putExtra("url", url);
                    startService(intent);
                    EventBus.getDefault().postSticky(new Boolean(false));
                    music.setPause(false);
                    music.setStart(1);
                } else if(music.isPause()){
                    //暂停之后继续播放
                    btnPaly.setBackgroundResource(R.drawable.pause);
                    MusicEvent event = new MusicEvent();
                    event.setTag(3);
                    EventBus.getDefault().postSticky(event);
                    EventBus.getDefault().postSticky(new Boolean(false));
                    music.setPause(false);
                }else if(!music.isPause()){
                    //暂停
                    btnPaly.setBackgroundResource(R.drawable.play);
                    MusicEvent event = new MusicEvent();
                    event.setTag(1);
                    EventBus.getDefault().postSticky(event);
                    EventBus.getDefault().postSticky(new Boolean(true));
                    music.setPause(true);
                }

            }
        });

    }

    private void setOnClikListener() {
        myOnClikListener = new MyOnClikListener();
        head.setOnClickListener(myOnClikListener);
        //set.setOnClickListener(myOnClikListener);
        like.setOnClickListener(myOnClikListener);
        collected.setOnClickListener(myOnClikListener);
        btnFollow.setOnClickListener(myOnClikListener);
        btnSendComment.setOnClickListener(myOnClikListener);
    }

    /**
     * 初始化数据
     */
    public void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if(bundle != null) {
            music = (Music) bundle.getSerializable("music");
            user = (User) bundle.getSerializable("user");
        }
    }

    private void initView() {

        initData();
        Log.e("start",music.getStart() + "");

        seekBar = findViewById(R.id.seekBar);
        btnPaly = findViewById(R.id.play_music);
        songCover = findViewById(R.id.music_cover);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        head = findViewById(R.id.iv_head_details_music);
        date = findViewById(R.id.tv_date_details_music);
        text = findViewById(R.id.music_text);
        like = findViewById(R.id.follow_music_details_btn_like);
        userName = findViewById(R.id.tv_userName_details_music);
        edComment = findViewById(R.id.follow_music_ed_comment);
        btnSendComment = findViewById(R.id.follow_music_btn_send_comment);
        scrollView = findViewById(R.id.follow_music_scrollview);
        rlComment = findViewById(R.id.music_comment_list);
        likeNum2 = findViewById(R.id.follow_music_tv_fa_num2);
        collectNum2 = findViewById(R.id.follow_music_tv_collection_num2);
        commentNum2 = findViewById(R.id.follow_music_tv_comment_num2);
        operation = findViewById(R.id.operation);
        songCover = findViewById(R.id.music_cover);
        refreshLayout = findViewById(R.id.music_refresh);
        collectionNum = findViewById(R.id.follow_music_details_tv_collection_num);
        comment = findViewById(R.id.follow_music_details_btn_comment);
        commentNum = findViewById(R.id.follow_music_details_tv_comment_num);
        followAnim = findViewById(R.id.music_details_iv_anim);
        fanNum = findViewById(R.id.tv_fanNum_details_music);
        btnFollow = findViewById(R.id.music_details_btn_follow);
        songName = findViewById(R.id.song_name);
        singer = findViewById(R.id.songer);
        musicCover = findViewById(R.id.music_cover);


        if(music != null)
            seekBar.setProgress(music.getCurrenttime());

        if(music.isPause())
            btnPaly.setBackgroundResource(R.drawable.play);
        else
            btnPaly.setBackgroundResource(R.drawable.pause);


        if (music.isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        likeNum = findViewById(R.id.follow_music_details_tv_like_num);
        collected = findViewById(R.id.follow_music_details_btn_collection);

        if (music.isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);


        if(user.isFollow()){
            btnFollow.setText("关注+");
        }else{
            btnFollow.setText("已关注");
        }
        text.setText(music.getText());
        date.setText(music.getTime());
        fanNum.setText(new StringBuilder().append(user.getFennum()));
        likeNum.setText(new StringBuilder().append(music.getLikes()));
        collectionNum.setText(new StringBuilder().append(music.getCollectnum()));
        commentNum.setText(new StringBuilder().append(music.getCheatnum()));
        Log.e("userName",user.getName());
        userName.setText(user.getName());
        commentNum2.setText(new StringBuilder().append(music.getCheatnum()));
        likeNum2.setText(new StringBuilder().append(music.getLikes()));
        collectNum2.setText(new StringBuilder().append(music.getCollectnum()));
        songName.setText(music.getName());
        singer.setText(music.getSinger());


        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.iv_default)
                .fallback(R.drawable.ouran_default)
                .centerCrop();

        if(music.getUser().getPicname() !=null) {
            Glide.with(this)
                    .load(ConfigUtil.BASE_HEAD_URL + music.getUser().getPicname())
                    .apply(requestOptions)
                    .into(head);
        }

        if(music.getPic() !=null) {
            Glide.with(this)
                    .load(music.getPic())
                    .apply(requestOptions)
                    .into(songCover);
        }

        //presenter.loadComment(music.getId(),pageNum);
    }

    /**
     * 测量高度
     */
    public void getHeightToAnim(){
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        screenHeight = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        //int screenHeight = (int) (scr / density);// 屏幕高度(dp)
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        scrollView.measure(w, h);

        scrollView.measure(w, h);

        rlComment.measure(w,h);
        commentListHeight = rlComment.getMeasuredHeight();

        float scale = this.getResources().getDisplayMetrics().density;
        int txtheight = text.getMeasuredHeight();
        Log.i("text.getBottom()",screenHeight + "");
        Log.i("txtHeight",txtheight + "");


        if(screenHeight >= txtheight){
            ViewWrapper viewWrapper = new ViewWrapper(edComment);
            ObjectAnimator animator = ObjectAnimator.ofInt(viewWrapper, "trueWidth",
                    0, 550);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(collected, "translationX",
                    0, 510);
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(like, "translationX",
                    0, 435);
            ObjectAnimator animator4 = ObjectAnimator.ofFloat(comment, "translationX",
                    0,370);
            ObjectAnimator animator5 = ObjectAnimator.ofFloat(btnSendComment, "alpha", 0, 0.5f, 1)
                    .setDuration(500);
            animator5.start();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator, animator4, animator2, animator3);
            animatorSet.setDuration(500);
            animatorSet.start();
            animFinish = true;
            commentNum.setVisibility(View.GONE);
            likeNum.setVisibility(View.GONE);
            collectionNum.setVisibility(View.GONE);
            btnSendComment.setVisibility(View.VISIBLE);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    edComment.setHint("写回复:");
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    class MyOnClikListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.follow_heart_iv_head://点击头像进入用户空间

                    break;
                case R.id.follow_heart_tv_set://点击文集进入用户空间

                    break;
                case R.id.follow_heart_details_btn_collection://收藏
                    if (music.isCollect()) {
                        collected.setBackgroundResource(R.drawable.collectionunselect);//取消收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) - 1;
                        collectionNum.setText(new StringBuilder().append(collectionNums));
                        collectNum2.setText(new StringBuilder().append(collectionNums));
                        music.setCollect(false);
                        presenter.confirmUnCollect(UserUtil.USER_ID, music.getId());
                        MsgEvent event = new MsgEvent();
                        event.setType("collect");
                        event.setValue(music.isCollect());
                        //event.setPosition(position);
                        event.setId(music.getId());
                        EventBus.getDefault().postSticky(event);
                    } else {
                        presenter.loadSet(UserUtil.USER_ID);
                    }
                    break;
                case R.id.follow_heart_details_btn_comment://评论按钮跳转到详情界面

                    break;
                case R.id.follow_heart_details_btn_like://点赞
                    if (!music.isLike()) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) + 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        likeNum2.setText(new StringBuilder().append(likeNums));
                        music.setLike(true);
                        presenter.confirmFavourite(UserUtil.USER_ID, music.getId());
                        //通知点赞按钮改变
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setValue(music.isLike());
                        event.setId(music.getId());
                        //event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    } else {
                        like.setBackgroundResource(R.drawable.likeunselect);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) - 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        likeNum2.setText(new StringBuilder().append(likeNums));
                        music.setLike(false);
                        presenter.confirmUnFavourite(UserUtil.USER_ID, music.getId());
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setValue(music.isLike());
                        event.setId(music.getId());
                        //event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }
                    break;
                case R.id.follow_heart_details_btn_follow:
                    followAnim.setVisibility(View.VISIBLE);
                    btnFollow.setVisibility(View.INVISIBLE);
                    loadingDrawable = (AnimationDrawable) followAnim.getDrawable();
                    loadingDrawable.start();
                    if (isFollow) {  //关注
                        isFollow = false;
                        presenter.confirmFollow(UserUtil.USER_ID, user.getId());
                    } else {         //取关
                        isFollow = true;
                        presenter.confirmUnFolly(UserUtil.USER_ID, user.getId());
                    }
                    break;
                case R.id.follow_heart_btn_send_comment:
                    presenter.confirmComment(UserUtil.USER_ID, music.getId(), edComment.getText().toString());
                    edComment.setText(null);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED,sticky = true)
    public void updateProcess(MusicEvent event){
        if(event.getTag() == 5) {
            if(MusicPlayUtil.MUSIC_IS_PLAY == music.getId()) {
                if (event.getCurrentTime() < event.getDuration()) {
                    seekBar.setMax(event.getDuration());
                    if(event.getDuration() % 60 < 10)
                        tvTotalTime.setText(event.getDuration() / 60 + ":0" + event.getDuration() % 60);
                    else
                        tvTotalTime.setText(event.getDuration() / 60 + ":" + event.getDuration() % 60);
                    totalTime = event.getDuration();
                    seekBar.setProgress(event.getCurrentTime());
                } else {
                    Log.i("CurrentTime", "" + event.getCurrentTime());
                    Log.i("Duration", "" + event.getDuration());
                    btnPaly.setBackgroundResource(R.drawable.play);
                    music.setStart(0);
                    music.setPause(true);
                    music.setStop(true);
                }
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
        if(music.isLike()){
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(getApplicationContext(), "点赞失败", Toast.LENGTH_SHORT).show();
            music.setLike(false);
        }else{
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(getApplicationContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();
            music.setLike(true);
        }

    }

    @Override
    public void changeCollectionError() {
        if(music.isCollect()) {
            collected.setBackgroundResource(R.drawable.collectionunselect);
            int collectNums = music.getCollectnum() -1;
            music.setCollectnum(collectNums);
            collectionNum.setText(new StringBuilder().append(collectNums));
            collectNum2.setText(new StringBuilder().append(collectNums));
            Toast.makeText(getApplicationContext(), "收藏失败", Toast.LENGTH_SHORT).show();
            music.setCollect(false);
        }else{
            collected.setBackgroundResource(R.drawable.collectionunselect);
            int collectNums = music.getCollectnum() + 1;
            music.setCollectnum(collectNums);
            collectionNum.setText(new StringBuilder().append(collectNums));
            collectNum2.setText(new StringBuilder().append(collectNums));
            Toast.makeText(getApplicationContext(), "取消收藏失败", Toast.LENGTH_SHORT).show();
            music.setCollect(true);
        }
    }

    @Override
    public void report() {

    }

    @Override
    public void share() {

    }

    @Override
    public void comment(Comment comment1) {
        User user = new User();
        user.setId(UserUtil.USER_ID);
        user.setName(UserUtil.USER_NAME);
        user.setPicname(userName + ".png");
        comment1.setUser(user);
        commentList.add(comment1);
        int commentNums = Integer.parseInt(commentNum.getText().toString());
        commentNums++;
        music.setCheatnum(commentNums);
        MsgEvent event = new MsgEvent();
        event.setType("comment");
        event.setValue(true);
        event.setIntValue(music.getCheatnum());
        event.setId(music.getId());
        EventBus.getDefault().postSticky(event);
        commentNum.setText(new StringBuilder().append(commentNums));
        commentNum2.setText(new StringBuilder().append(commentNums));
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadComment(List<Comment> comments) {
        if(isInitComment) {
            this.commentList = comments;
            commentAdapter = new CommentAdapter(comments, getApplicationContext());
            rlComment.setLayoutManager(new LinearLayoutManager(this));
            rlComment.setAdapter(commentAdapter);
            isInitComment = false;
            if(commentList.size() == 10)
                pageNum++;
        }else{
            List<Comment> newComments = new ArrayList<>();
            for(Comment comment:commentList){
                for(Comment comment1:comments){
                    if(comment.getId() == comment1.getId())
                        newComments.add(comment1);
                }
            }
            commentList.addAll(comments);
            commentList.removeAll(newComments);
            if(commentList.size() % 10 == 0)
                pageNum++;
            commentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleteComment() {
        for(Comment comment1 :commentList){
            if(comment1.getId() == deleteComment.getId()){
                commentList.remove(comment1);
                break;
            }
        }
        commentNum.setText(new StringBuilder().append(commentList.size()));
        commentNum2.setText(new StringBuilder().append(commentList.size()));
        commentAdapter.notifyDataSetChanged();
        MsgEvent event = new MsgEvent();
        event.setId(music.getId());
        event.setType("comment");
        event.setValue(false);
        event.setIntValue(music.getCheatnum());
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void collect() {
        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
        collectionNum.setText(new StringBuilder().append(collectionNums));
        collectNum2.setText(new StringBuilder().append(collectionNums));
        music.setCollect(true);
        MsgEvent event = new MsgEvent();
        event.setType("collect");
        event.setValue(music.isCollect());
        //event.setPosition(position);
        event.setId(music.getId());
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void noSet() {

    }

    @Override
    public void showSet(List<Set> sets) {

    }

    @Override
    public void deleteContent() {

    }

    @Override
    public void deleteError() {

    }

}