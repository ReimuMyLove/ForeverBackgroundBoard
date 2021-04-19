package com.example.shoujiedemo.myCenter.mySpace.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.activity.AddGroupActivity;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpaceArticleFragment;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpaceMusicFragment;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MySpaceView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MySpaceActivity extends BaseActivity implements MySpaceView {
    ImageView
            mySpace_background;             //获取用户背景
    CircleImageView
            mySpace_userImg;                //获取用户头像
    TextView
            mySpace_userName,               //获取用户名
            mySpace_userFans;               //获取用户粉丝数目
    TabLayout
            mySpace_topTab;                 //获取tab栏
    TabItem
            mySpace_article,                //获取文章标题栏
            mySpace_music;                  //获取音乐标题栏
    ViewPager2
            mySpace_view;                   //获取对应viewPage2
    List<Fragment>
            fragments = new ArrayList<>();  //获取fragments数组
    Button
            mySpace_follow,                 //关注按钮
            mySpace_add;                    //添加按钮
    MySpacePresenter
            mySpacePresenter;               //绑定Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myspace);
        EventBus.getDefault().register(this);
        mySpacePresenter = new MySpacePresenter(this);
        //获取view控件
        FindView();
        //设置控件宽高
        SetViewHW();
        //设置监听器
        setListener();
        //设置控件属性
        setViewValue();
        //绑定fragment页面
        fragments.add(new MySpaceArticleFragment());   //文章页面
        fragments.add(new MySpaceMusicFragment());     //音乐页面
        //绑定Fragment
        mySpace_view.setAdapter(new MyFragmentPagerAdapter(this,fragments));
        //绑定点击监听器
        new TabLayoutMediator(mySpace_topTab, mySpace_view, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("　　文集　　");
                    mySpace_view.setCurrentItem(0);
                }else if(position == 1){
                    tab.setText("　　音乐　　");
                    mySpace_view.setCurrentItem(1);
                }
            }
        }).attach();
        //绑定滑动监听器

        mySpace_view.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mySpace_topTab.selectTab(mySpace_topTab.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        //设置初始加载fragment
        mySpace_view.setCurrentItem(0);
    }

    /**
     * 获取view方法
     */
    public void FindView() {
        mySpace_background = findViewById(R.id.mySpace_background);
        mySpace_userImg = findViewById(R.id.mySpace_userImg);
        mySpace_userName = findViewById(R.id.mySpace_userName);
        mySpace_article = findViewById(R.id.mySpace_article);
        mySpace_music = findViewById(R.id.mySpace_music);
        mySpace_view = findViewById(R.id.mySpace_view);
        mySpace_topTab = findViewById(R.id.mySpace_topTab);
        mySpace_userFans = findViewById(R.id.mySpace_userFans);
        mySpace_follow = findViewById(R.id.mySpace_follow);
        mySpace_add = findViewById(R.id.mySpace_add);
    }

    /**
     * 设置物块长宽高
     */
    private void SetViewHW() {
        /* 获取屏幕宽度 */
        DisplayMetrics wm = getResources().getDisplayMetrics();
        int width = wm.widthPixels;
        /* 获取控件自身高度 */
        ViewGroup.LayoutParams
                mS_bg,      //背景图
                mS_uI;      //头像

        //获取背景图
        mS_bg = mySpace_background.getLayoutParams();
        mS_bg.height = (width*5/7);       //设置背景图为13:10的长宽比
        //获取头像控件
        mS_uI = mySpace_userImg.getLayoutParams();
        mS_uI.height = (width/5);
        mS_uI.width = (width/5); //设置头像宽高均为1/5屏幕宽
    }

    /**
     * 设置控件属性
     */
    @SuppressLint("SetTextI18n")
    private void setViewValue(){
        User user = (User)getIntent().getSerializableExtra("user");
        if(user!=null){     //判断是否有用户对象传递过来
            if(user.getId() != UserUtil.USER_ID){
                //判断传递过来的用户对象与本地用户是否一致
                mySpace_add.setVisibility(View.INVISIBLE);
                mySpace_follow.setVisibility(View.VISIBLE);
                getOwnerInfo(user.getId());
            }else{
                Toast.makeText(this, "偶然：未知错误", Toast.LENGTH_SHORT).show();
            }
        }else{
            mySpace_add.setVisibility(View.VISIBLE);            //添加文集可见
            mySpace_follow.setVisibility(View.INVISIBLE);       //自身空间关注按钮不可见
            mySpace_userFans.setText(UserUtil.USER_FANS+"");    //粉丝数
            mySpace_userName.setText(UserUtil.USER_NAME);       //用户名
            UserUtil.RECENT_USER_ID = UserUtil.USER_ID;         //用户ID(不可见,作为参数使用
            if (!UserUtil.USER_IMG.equals("")){                 //用户头像
                String URL = ConfigUtil.BASE_HEAD_URL+UserUtil.USER_IMG;
                RequestOptions requestOptions = new RequestOptions().centerCrop();
                Glide.with(getBaseContext())
                        .load(URL)
                        .apply(requestOptions)
                        .into(mySpace_userImg);
            }
            if(!UserUtil.USER_SPACE_BACKGROUND.equals("")){     //用户背景图
                String URL = ConfigUtil.BASE_HEAD_URL+UserUtil.USER_SPACE_BACKGROUND;
                RequestOptions requestOptions = new RequestOptions().centerCrop();
                Glide.with(getBaseContext())
                        .load(URL)
                        .apply(requestOptions)
                        .into(mySpace_background);
            }
        }
    }

    /**
     * 设置监听器方法
     */
    public void setListener(){
        MyListener listener = new MyListener();
        mySpace_follow.setOnClickListener(listener);
        mySpace_add.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mySpace_add:
                    addSet();
                    break;
                case R.id.mySpace_follow:
                    addFollow();
            }
        }
    }

    /**
     * 添加新文集方法
     */
    private void addSet(){
        Intent intent = new Intent(this, AddGroupActivity.class);
        startActivityForResult(intent,5);
    }

    /**
     *  添加文集数据回调方法
     */

    @Override
    public void addGroupFailed() {
        Toast.makeText(this, "偶然：添加失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addGroupSuccessful(Set set) {
        EventBus.getDefault().postSticky(set);
        Toast.makeText(this, "偶然：添加成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 关注方法
     */
    private void addFollow(){
        int followID = UserUtil.RECENT_USER_ID;
        int userID = UserUtil.USER_ID;
        mySpacePresenter.addFollow(userID,followID);
    }

    /**
     * 关注回调方法
     */

    @Override
    public void addFollowFailed() {
        Toast.makeText(this, "偶然：关注失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addFollowSuccessful() {
        mySpace_follow.setBackgroundResource(R.drawable.followed_mypace);
        Toast.makeText(this, "偶然：关注成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取当前空间主人信息方法
     */
    public void getOwnerInfo(int ownerID){
        mySpacePresenter.getOwnerInfo(ownerID);
    }

    /**
     * 获取当前空间主人信息回调方法
     */
    @Override
    public void getOwnerInfoFailed() {
        Toast.makeText(this, "偶然：获取空间信息失败", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getOwnerInfoSuccessful(User userInfo) {
        if (userInfo != null){
            mySpace_userFans.setText(userInfo.getFennum()+"");
            mySpace_userName.setText(userInfo.getName());
            if (userInfo.getPicname() != null ){
                if(!userInfo.getPicname().equals("")){
                    String URL = ConfigUtil.BASE_HEAD_URL+userInfo.getPicname();
                    RequestOptions requestOptions = new RequestOptions().centerCrop();
                    Glide.with(getBaseContext())
                            .load(URL)
                            .apply(requestOptions)
                            .into(mySpace_userImg);
                }
            }
            if (userInfo.getBackgroundpic2()!=null){
                if(!userInfo.getBackgroundpic2().equals("")){
                    String URL = ConfigUtil.BASE_BACKGROUND_URL+userInfo.getBackgroundpic2();
                    RequestOptions requestOptions = new RequestOptions().centerCrop();
                    Glide.with(getBaseContext())
                            .load(URL)
                            .apply(requestOptions)
                            .into(mySpace_background);
                }
            }
            if (userInfo.isFollow()){
                mySpace_follow.setBackgroundResource(R.drawable.follow_mys);
                mySpace_follow.setClickable(true);
            }else{
                mySpace_follow.setBackgroundResource(R.drawable.followed_mypace);
                mySpace_follow.setClickable(false);
            }
        }
    }
}
