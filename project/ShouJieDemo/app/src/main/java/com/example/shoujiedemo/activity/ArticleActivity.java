package com.example.shoujiedemo.activity;

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
import com.example.shoujiedemo.bean.ArticleEvent;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenterImpl;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.view.ArticleView;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.myCenter.myCenter.service.UserImgView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;
import com.example.shoujiedemo.util.ViewWrapper;
import com.hyb.library.PreventKeyboardBlockUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity implements ContentView, ArticleView {

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
    private NestedScrollView scrollView;
    private EditText edComment;
    private Button btnSendComment;
    private RecyclerView rlComment;
    private TextView commentNum2;
    private TextView likeNum2;
    private TextView shareNum2;
    private TextView collectNum2;
    private CommentAdapter commentAdapter;
    private TextView writer;
    private ListView setList;
    private Button btnCollect;
    private Button dismiss;
    private ImageView cover;

   // private View headView;

    private List<Comment> commentList = new ArrayList<>();
    private int screenHeight;//屏幕高度
    private int commentListHeight;//评论列表长度
    private View setAlterView;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private SetAdapter setAdapter;
    private Set set1;

    private Content article;
    private User user;

    private boolean isFollow = false;
    private int position;//位置
    private boolean animFinish = false;//判断动画是否完成

    private MyFollowOperatePresenter presenter;
    private AnimationDrawable loadingDrawable;
    private MyOnClikListener myOnClikListener;
    private MyFollowAtriclePresenter atriclePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        builder = new AlertDialog.Builder(this);

        initData();
        initView();
        getHeightToAnim();
        setOnClikListener();

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
                                ObjectAnimator animator1 = ObjectAnimator.ofFloat(share, "translationX",
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
                                animatorSet.playTogether(animator, animator1, animator2, animator3);
                                animatorSet.setDuration(250);
                                animatorSet.start();
                                edComment.setHint("");
                                animator.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        shareNum.setVisibility(View.VISIBLE);
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
                                ObjectAnimator animator1 = ObjectAnimator.ofFloat(share, "translationX",
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
                                animatorSet1.playTogether(animator, animator1, animator2, animator3);
                                animatorSet1.setDuration(250);
                                animatorSet1.start();
                                btnSendComment.setVisibility(View.VISIBLE);
                                animFinish = true;
                                commentNum.setVisibility(View.INVISIBLE);
                                likeNum.setVisibility(View.INVISIBLE);
                                shareNum.setVisibility(View.INVISIBLE);
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

    }

    private void setOnClikListener() {
        myOnClikListener = new MyOnClikListener();
        head.setOnClickListener(myOnClikListener);
       //set.setOnClickListener(myOnClikListener);
        like.setOnClickListener(myOnClikListener);
        collected.setOnClickListener(myOnClikListener);
        btnFollow.setOnClickListener(myOnClikListener);
        share.setOnClickListener(myOnClikListener);
        btnSendComment.setOnClickListener(myOnClikListener);
        comment.setOnClickListener(myOnClikListener);
    }

    private void initView() {

        head = findViewById(R.id.iv_head_details_article);
        title = findViewById(R.id.tv_title_details_article);
        date = findViewById(R.id.tv_date_details_article);
        text = findViewById(R.id.tv_content_details_article);
        like = findViewById(R.id.follow_article_details_btn_like);
        userName = findViewById(R.id.tv_userName_details_article);
        edComment = findViewById(R.id.follow_article_ed_comment);
        btnSendComment = findViewById(R.id.follow_article_btn_send_comment);
        rlComment = findViewById(R.id.follow_article_comment_list);
        likeNum2 = findViewById(R.id.follow_article_tv_fa_num2);
        collectNum2 = findViewById(R.id.follow_article_tv_collection_num2);
        shareNum2 = findViewById(R.id.follow_article_tv_share_num2);
        commentNum2 = findViewById(R.id.follow_article_tv_comment_num2);
        writer = findViewById(R.id.tv_writer_details_article);
        cover = findViewById(R.id.iv_cover_article_details);



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
        scrollView = findViewById(R.id.follow_article_text_scrollview);


        if(isFollow){
            btnFollow.setText("关注+");
        }else{
            btnFollow.setText("已关注");
        }
        title.setText(article.getTitle());
        date.setText(article.getTime());
        fanNum.setText(new StringBuilder().append(user.getFennum()));
        likeNum.setText(new StringBuilder().append(article.getLikes()));
        collectionNum.setText(new StringBuilder().append(article.getCollectnum()));
        shareNum.setText(new StringBuilder().append(article.getForwardnum()));
        commentNum.setText(new StringBuilder().append(article.getCheatnum()));
        userName.setText(user.getName());
        commentNum2.setText(new StringBuilder().append(article.getCheatnum()));
        likeNum2.setText(new StringBuilder().append(article.getLikes()));
        collectNum2.setText(new StringBuilder().append(article.getCollectnum()));
        shareNum2.setText(new StringBuilder().append(article.getForwardnum()));
        writer.setText(article.getWriter());

        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(this)
                .load(ConfigUtil.BASE_IMG_URL + article.getPic())
                .apply(requestOptions)
                .into(cover);

        Glide.with(this)
                .load( ConfigUtil.BASE_HEAD_URL + article.getUser().getPicname())
                .into(head);


        presenter.loadComment(article.getId());//加载评论

        atriclePresenter.confirmLoadAtricleContent(article.getId());

        setAlterView = LayoutInflater.from(this).inflate(R.layout.collect_alterdialog_view,null,false);
        setList = setAlterView.findViewById(R.id.set_list);
        btnCollect = setAlterView.findViewById(R.id.item_btn_collect);
        dismiss = setAlterView.findViewById(R.id.set_btn_dismss);
        alert = builder.create();


    }

    public void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        article = (Content)bundle.getSerializable("article");
        isFollow = bundle.getBoolean("isFollow");
        user = (User)bundle.getSerializable("user");
        position = bundle.getInt("position");
        presenter = new MyFollowOperatePresenterImpl(this);
        atriclePresenter = new MyFollowAtriclePresenterImpl(this);


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
        //int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
       //int screenHeight = (int) (height / density);// 屏幕高度(dp)
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        scrollView.measure(w, h);

        rlComment.measure(w,h);
        commentListHeight = rlComment.getMeasuredHeight();

        float scale = this.getResources().getDisplayMetrics().density;
        int txtheight = text.getMeasuredHeight();
        Log.i("text.getBottom()",screenHeight + "");
        Log.i("txtHeight",txtheight + "");

        /*if(screenHeight >= txtheight){
            ViewWrapper viewWrapper = new ViewWrapper(edComment);
            ObjectAnimator animator = ObjectAnimator.ofInt(viewWrapper, "trueWidth",
                    0, 550);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(share, "translationX",
                    0, 550);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(collected, "translationX",
                    0, 480);
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(like, "translationX",
                    0, 400);
            ObjectAnimator animtor4 = ObjectAnimator.ofFloat(comment, "alpha",
                    1, 0.5f, 0)
                    .setDuration(300);
            animtor4.start();
            ObjectAnimator animator5 = ObjectAnimator.ofFloat(btnSendComment, "alpha", 0, 0.5f, 1)
                    .setDuration(500);
            animator5.start();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator, animator1, animator2, animator3);
            animatorSet.setDuration(600);
            animatorSet.start();
            //btnSendComment.setVisibility(View.VISIBLE);
            animFinish = true;
            commentNum.setVisibility(View.INVISIBLE);
            //comment.setVisibility(View.INVISIBLE);
            likeNum.setVisibility(View.INVISIBLE);
            shareNum.setVisibility(View.INVISIBLE);
            collectionNum.setVisibility(View.INVISIBLE);
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
        }*/
    }

    @Override
    public void loadContent(String article) {
        text.setText(article);
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
                        collectNum2.setText(new StringBuilder().append(collectionNums));
                        article.setCollect(false);
                        presenter.confirmUnCollect(UserUtil.USER_ID,article.getId());
                        MsgEvent event = new MsgEvent();
                        event.setType("collect");
                        event.setValue(article.isCollect());
                        event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }else{
                        presenter.loadSet(UserUtil.USER_ID);
                    }
                    break;
                case R.id.follow_article_details_btn_comment://评论按扭下滑
                    scrollView.scrollTo(0,rlComment.getTop());
                    break;
                case R.id.follow_article_details_btn_like://点赞
                    if(!article.isLike()) {
                        like.setBackgroundResource(R.drawable.likeselected);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) + 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        likeNum2.setText(new StringBuilder().append(likeNums));
                        article.setLike(true);
                        presenter.confirmFavourite(UserUtil.USER_ID,article.getId());
                        Log.i("isLike",article.isLike() + "");
                        //通知点赞按钮改变
                        ArticleEvent event = new ArticleEvent();
                        event.setType("like");
                        event.setValue(article.isLike());
                        event.setPosition(position);
                        EventBus.getDefault().postSticky(event);
                    }else{
                        like.setBackgroundResource(R.drawable.likeunselect);
                        int likeNums = Integer.parseInt(likeNum.getText().toString()) - 1;
                        likeNum.setText(new StringBuilder().append(likeNums));
                        likeNum2.setText(new StringBuilder().append(likeNums));
                        article.setLike(false);
                        presenter.confirmUnFavourite(UserUtil.USER_ID,article.getId());
                        Log.i("isLike",article.isLike() + "");
                        ArticleEvent event = new ArticleEvent();
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
                        presenter.confirmFollow(UserUtil.USER_ID,user.getId());
                    }else {         //取关
                        isFollow= true;
                        presenter.confirmUnFolly(UserUtil.USER_ID,user.getId());
                    }
                    break;
                case R.id.follow_article_btn_send_comment:
                    presenter.confirmComment(UserUtil.USER_ID,article.getId());
                    edComment.setText(null);

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
            int likeNums = article.getLikes() -1;
            article.setLikes(likeNums);
            likeNum.setText(new StringBuilder().append(likeNums));
            likeNum2.setText(new StringBuilder().append(likeNums));
            Toast.makeText(getApplicationContext(), "点赞失败", Toast.LENGTH_SHORT).show();
            article.setLike(false);
        }else{
            like.setBackgroundResource(R.drawable.likeselected);
            int likeNums = article.getLikes() + 1;
            article.setLikes(likeNums);
            likeNum.setText(new StringBuilder().append(likeNums));
            likeNum2.setText(new StringBuilder().append(likeNums));
            Toast.makeText(getApplicationContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();
            article.setLike(true);
        }

    }

    @Override
    public void changeCollectionError() {
        if(article.isCollect()) {
            collected.setBackgroundResource(R.drawable.collectionunselect);
            int collectNums = article.getCollectnum() -1;
            article.setCollectnum(collectNums);
            collectionNum.setText(new StringBuilder().append(collectNums));
            collectNum2.setText(new StringBuilder().append(collectNums));
            Toast.makeText(getApplicationContext(), "收藏失败", Toast.LENGTH_SHORT).show();
            article.setCollect(false);
        }else{
            collected.setBackgroundResource(R.drawable.collectionselected);
            int collectNums = article.getCollectnum() + 1;
            article.setCollectnum(collectNums);
            collectionNum.setText(new StringBuilder().append(collectNums));
            collectNum2.setText(new StringBuilder().append(collectNums));
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
        String text = edComment.getText().toString();
        String userName = UserUtil.USER_NAME;
        User user = new User();
        user.setId(UserUtil.USER_ID);
        user.setName(userName);
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        commentList.add(comment);
        int commentNums = Integer.parseInt(commentNum.getText().toString());
        commentNums++;
        article.setCheatnum(commentNums);
        MsgEvent event = new MsgEvent();
        event.setType("comment");
        event.setIntValue(article.getCheatnum());
        event.setPosition(position);
        EventBus.getDefault().postSticky(event);
        commentNum.setText(new StringBuilder().append(commentNums));
        commentNum2.setText(new StringBuilder().append(commentNums));
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void collect() {
        collected.setBackgroundResource(R.drawable.collectionselected);//收藏
        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
        collectionNum.setText(new StringBuilder().append(collectionNums));
        collectNum2.setText(new StringBuilder().append(collectionNums));
        article.setCollect(true);
        ArticleEvent event = new ArticleEvent();
        event.setType("collect");
        event.setValue(article.isCollect());
        event.setPosition(position);
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void loadComment(List<Comment> commentList) {
        this.commentList = commentList;
        commentAdapter = new CommentAdapter(commentList,getApplicationContext());
        rlComment.setLayoutManager(new LinearLayoutManager(this));
        rlComment.setAdapter(commentAdapter);
    }

    @Override
    public void showSet(final List<Set> sets) {
        setAdapter = new SetAdapter(sets,this);
        setList.setAdapter(setAdapter);
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.confirmCollect(UserUtil.USER_ID,article.getId());
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

}