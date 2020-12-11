package com.example.shoujiedemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.CommentAdapter;
import com.example.shoujiedemo.adapter.SetAdapter;
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
import com.example.shoujiedemo.util.ViewWrapper;
import com.hyb.library.PreventKeyboardBlockUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class HeartActivity extends AppCompatActivity implements ContentView {

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
    private RecyclerView rlComment;
    private NestedScrollView scrollView;
    private TextView commentNum2;
    private TextView likeNum2;
    private TextView collectNum2;
    private CommentAdapter commentAdapter;
    private View operation;
    private View setAlterView;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private SetAdapter setAdapter;
    private Set set1;
    private ListView setList;
    private Button btnCollect;
    private Button dismiss;
    private ImageView cover;
    private boolean isInitComment = true;
    private int pageNum = 1;
    private SmartRefreshLayout refreshLayout;
    private Comment deleteComment;


    private Content heart;
    private User user;

    private boolean animFinish = false;
    private boolean isFollow = false;
    private int position;


    private List<Comment> commentList = new ArrayList<>();
    private int screenHeight;//屏幕高度
    private int commentListHeight;//评论列表长度

    private MyFollowOperatePresenter presenter;
    private AnimationDrawable loadingDrawable;
    private MyOnClikListener myOnClikListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
        builder = new AlertDialog.Builder(this);
        EventBus.getDefault().register(this);

        initView();
        setOnClikListener();
        getHeightToAnim();
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(false);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadComment(heart.getId(),pageNum);
                refreshLayout.finishLoadMore(600);
            }
        });
    }

    private void initView() {

        initData();

        head = findViewById(R.id.iv_head_details_heart);
        date = findViewById(R.id.tv_date_details_heart);
        text = findViewById(R.id.tv_content_details_heart);
        like = findViewById(R.id.follow_heart_details_btn_like);
        userName = findViewById(R.id.tv_userName_details_heart);
        edComment = findViewById(R.id.follow_heart_ed_comment);
        btnSendComment = findViewById(R.id.follow_heart_btn_send_comment);
        scrollView = findViewById(R.id.follow_heart_scrollview);
        rlComment = findViewById(R.id.follow_heart_comment_list);
        likeNum2 = findViewById(R.id.follow_heart_tv_fa_num2);
        collectNum2 = findViewById(R.id.follow_heart_tv_collection_num2);
        commentNum2 = findViewById(R.id.follow_heart_tv_comment_num2);
        operation = findViewById(R.id.operation);
        cover = findViewById(R.id.heart_details_iv_cover);
        refreshLayout = findViewById(R.id.heart_refresh);


        if (heart.isLike())
            like.setBackgroundResource(R.drawable.likeselected);
        else
            like.setBackgroundResource(R.drawable.likeunselect);

        likeNum = findViewById(R.id.follow_heart_details_tv_like_num);
        collected = findViewById(R.id.follow_heart_details_btn_collection);

        if (heart.isCollect())
            collected.setBackgroundResource(R.drawable.collectionselected);
        else
            collected.setBackgroundResource(R.drawable.collectionunselect);

        collectionNum = findViewById(R.id.follow_heart_details_tv_collection_num);
        comment = findViewById(R.id.follow_heart_details_btn_comment);
        commentNum = findViewById(R.id.follow_heart_details_tv_comment_num);
        followAnim = findViewById(R.id.follow_heart_details_iv_follow_anim);
        fanNum = findViewById(R.id.tv_fanNum_details_heart);
        btnFollow = findViewById(R.id.follow_heart_details_btn_follow);
        if(isFollow){
            btnFollow.setText("关注+");
        }else{
            btnFollow.setText("已关注");
        }
        text.setText(heart.getText());
        date.setText(heart.getTime());
        fanNum.setText(new StringBuilder().append(user.getFennum()));
        likeNum.setText(new StringBuilder().append(heart.getLikes()));
        collectionNum.setText(new StringBuilder().append(heart.getCollectnum()));
        commentNum.setText(new StringBuilder().append(heart.getCheatnum()));
        Log.e("userName",user.getName());
        userName.setText(user.getName());
        commentNum2.setText(new StringBuilder().append(heart.getCheatnum()));
        likeNum2.setText(new StringBuilder().append(heart.getLikes()));
        collectNum2.setText(new StringBuilder().append(heart.getCollectnum()));
        setAlterView = LayoutInflater.from(this).inflate(R.layout.collect_alterdialog_view,null,false);
        setList = setAlterView.findViewById(R.id.set_list);
        btnCollect = setAlterView.findViewById(R.id.item_btn_collect);
        dismiss = setAlterView.findViewById(R.id.set_btn_dismss);
        alert = builder.create();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.iv_default)
                .fallback(R.drawable.ouran_default)
                .centerCrop();

        if(heart.getUser().getPicname() !=null) {
            Glide.with(this)
                    .load(ConfigUtil.BASE_HEAD_URL + heart.getUser().getPicname())
                    .apply(requestOptions)
                    .into(head);
        }

        if(heart.getPic() !=null) {
            Glide.with(this)
                    .load(ConfigUtil.BASE_IMG_URL + heart.getPic())
                    .apply(requestOptions)
                    .into(cover);
        }

        presenter.loadComment(heart.getId(),pageNum);
    }

    /**
     * 初始化数据
     */
    public void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        heart = (Content)bundle.getSerializable("heart");
        isFollow = bundle.getBoolean("isFollow");
        user = (User)bundle.getSerializable("user");
        position = bundle.getInt("position");
        presenter = new MyFollowOperatePresenterImpl(HeartActivity.this);
    }

    private void setOnClikListener() {
        myOnClikListener = new MyOnClikListener();
        //head.setOnClickListener(myOnClikListener);
        //set.setOnClickListener(myOnClikListener);
        like.setOnClickListener(myOnClikListener);
        collected.setOnClickListener(myOnClikListener);
        btnFollow.setOnClickListener(myOnClikListener);
        btnSendComment.setOnClickListener(myOnClikListener);
    }

    class MyOnClikListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.follow_heart_iv_head://点击头像进入用户空间

                    break;
                case R.id.follow_heart_tv_set://点击文集进入用户空间

                    break;
                case R.id.follow_heart_details_btn_collection://收藏
                    if(heart.isCollect()) {
                        collected.setBackgroundResource(R.drawable.collectionunselect);//取消收藏
                        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) -1;
                        collectionNum.setText(new StringBuilder().append(collectionNums));
                        collectNum2.setText(new StringBuilder().append(collectionNums));
                        heart.setCollect(false);
                        presenter.confirmUnCollect(UserUtil.USER_ID,heart.getId());
                        MsgEvent event = new MsgEvent();
                        event.setType("collect");
                        event.setValue(heart.isCollect());
                        //event.setPosition(position);
                        event.setId(heart.getId());
                        EventBus.getDefault().postSticky(event);
                    }else{
                        presenter.loadSet(UserUtil.USER_ID);
                    }
                    break;
                case R.id.follow_heart_details_btn_comment://评论按钮跳转到详情界面

                    break;
                case R.id.follow_heart_details_btn_like://点赞
                    if(!heart.isLike()) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) + 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        likeNum2.setText(new StringBuilder().append(likeNums));
                        heart.setLike(true);
                        presenter.confirmFavourite(UserUtil.USER_ID,heart.getId());
                        //通知点赞按钮改变
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setValue(heart.isLike());
                        event.setId(heart.getId());
                        //event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }else{
                        like.setBackgroundResource(R.drawable.likeunselect);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) - 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        likeNum2.setText(new StringBuilder().append(likeNums));
                        heart.setLike(false);
                        presenter.confirmUnFavourite(UserUtil.USER_ID,heart.getId());
                        MsgEvent event = new MsgEvent();
                        event.setType("like");
                        event.setValue(heart.isLike());
                        event.setId(heart.getId());
                        //event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }
                    break;
                case R.id.follow_heart_details_btn_follow:
                    followAnim.setVisibility(View.VISIBLE);
                    btnFollow.setVisibility(View.INVISIBLE);
                    loadingDrawable =(AnimationDrawable)followAnim.getDrawable();
                    loadingDrawable.start();
                    if(isFollow) {  //关注
                        isFollow = false;
                        presenter.confirmFollow(UserUtil.USER_ID,user.getId());
                    }else {         //取关
                        isFollow= true;
                        presenter.confirmUnFolly(UserUtil.USER_ID,user.getId());
                    }
                    break;
                case R.id.follow_heart_btn_send_comment:
                    presenter.confirmComment(UserUtil.USER_ID,heart.getId(),edComment.getText().toString());
                    edComment.setText(null);
                    break;
            }
        }
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
                    0, 430);
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(like, "translationX",
                    0, 365);
            ObjectAnimator animator4 = ObjectAnimator.ofFloat(comment, "translationX",
                    0,300);
            ObjectAnimator animator5 = ObjectAnimator.ofFloat(btnSendComment, "alpha", 0, 0.5f, 1)
                    .setDuration(500);
            animator5.start();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator, animator4, animator2, animator3);
            animatorSet.setDuration(500);
            animatorSet.start();
            //btnSendComment.setVisibility(View.VISIBLE);
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
        if(heart.isLike()){
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(getApplicationContext(), "点赞失败", Toast.LENGTH_SHORT).show();
            heart.setLike(false);
        }else{
            like.setBackgroundResource(R.drawable.likeunselect);
            Toast.makeText(getApplicationContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();
            heart.setLike(true);
        }

    }

    @Override
    public void changeCollectionError() {
        if(heart.isCollect()) {
            collected.setBackgroundResource(R.drawable.collectionunselect);
            int collectNums = heart.getCollectnum() -1;
            heart.setCollectnum(collectNums);
            collectionNum.setText(new StringBuilder().append(collectNums));
            collectNum2.setText(new StringBuilder().append(collectNums));
            Toast.makeText(getApplicationContext(), "收藏失败", Toast.LENGTH_SHORT).show();
            heart.setCollect(false);
        }else{
            collected.setBackgroundResource(R.drawable.collectionunselect);
            int collectNums = heart.getCollectnum() + 1;
            heart.setCollectnum(collectNums);
            collectionNum.setText(new StringBuilder().append(collectNums));
            collectNum2.setText(new StringBuilder().append(collectNums));
            Toast.makeText(getApplicationContext(), "取消收藏失败", Toast.LENGTH_SHORT).show();
            heart.setCollect(true);
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
        String userName = UserUtil.USER_NAME;
        User user = new User();
        user.setId(UserUtil.USER_ID);
        user.setName(userName);
        user.setPicname(userName + ".png");
        comment1.setUser(user);
        commentList.add(comment1);
        int commentNums = Integer.parseInt(commentNum.getText().toString());
        commentNums++;
        heart.setCheatnum(commentNums);
        MsgEvent event = new MsgEvent();
        event.setType("comment");
        event.setValue(true);
        event.setIntValue(heart.getCheatnum());
        event.setId(heart.getId());
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
                commentList.remove(deleteComment);
                break;
            }
        }
        commentNum.setText(new StringBuilder().append(commentList.size()));
        commentNum2.setText(new StringBuilder().append(commentList.size()));
        commentAdapter.notifyDataSetChanged();
        MsgEvent event = new MsgEvent();
        event.setId(heart.getId());
        event.setType("comment");
        event.setValue(false);
        event.setIntValue(heart.getCheatnum());
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void noSet() {

    }


    @Override
    public void showSet(final List<Set> sets) {
        setAdapter = new SetAdapter(sets,this);
        setList.setAdapter(setAdapter);
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.confirmCollect(UserUtil.USER_ID,heart.getId());
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
    protected void onResume() {
        super.onResume();
        PreventKeyboardBlockUtil.getInstance(this).setBtnView(edComment).register();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreventKeyboardBlockUtil.getInstance(this).unRegister();
    }

    @Override
    public void collect() {
        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
        collectionNum.setText(new StringBuilder().append(collectionNums));
        collectNum2.setText(new StringBuilder().append(collectionNums));
        heart.setCollect(true);
        MsgEvent event = new MsgEvent();
        event.setType("collect");
        event.setValue(heart.isCollect());
        //event.setPosition(position);
        event.setId(heart.getId());
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 删除评论后回调
     * @param comment
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onDeleteMain(Comment comment){
        deleteComment = comment;
        presenter.deleteComment(comment.getId());
    }
}