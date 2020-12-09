package com.example.shoujiedemo.myCenter.mySpace.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.activity.AddGroupActivity;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpaceArticleFragment;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpaceIdeaFragment;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpacePoemFragment;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MySpaceView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;

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
            mySpace_poem,                   //获取诗集标题栏
            mySpace_idea;                   //获取感悟标题栏
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
        mySpacePresenter = new MySpacePresenter(this);
        //获取view控件
        FindView();
        //设置控件宽高
        SetViewHW();
        //设置控件属性
        setViewValue();
        //设置监听器
        setListener();
        //绑定fragment页面
        fragments.add(new MySpaceArticleFragment());   //文章页面
        fragments.add(new MySpaceIdeaFragment());      //感悟页面
        fragments.add(new MySpacePoemFragment());      //诗页面
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
                    tab.setText("　　感悟　　");
                    mySpace_view.setCurrentItem(1);
                }else if(position == 2){
                    tab.setText("　　诗　　");
                    mySpace_view.setCurrentItem(2);
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
        mySpace_poem = findViewById(R.id.mySpace_poem);
        mySpace_article = findViewById(R.id.mySpace_article);
        mySpace_idea = findViewById(R.id.mySpace_idea);
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
        if(UserUtil.RECENT_USER_ID != UserUtil.USER_ID){
            mySpace_add.setVisibility(View.INVISIBLE);
            mySpace_follow.setVisibility(View.VISIBLE);
        }else{
            mySpace_add.setVisibility(View.VISIBLE);
            mySpace_follow.setVisibility(View.INVISIBLE);
        }
        mySpace_userFans.setText(UserUtil.USER_FANS+"");
        mySpace_userName.setText(UserUtil.USER_NAME);
        String URL = ConfigUtil.BASE_HEAD_URL+UserUtil.USER_IMG;
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(getBaseContext())
                .load(URL)
                .apply(requestOptions)
                .into(mySpace_userImg);
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
     * 获取数据方法
     */
    private void getData(int userID){
        mySpacePresenter.getGroups(userID);
    }

    /**
     * 添加新文集方法
     */
    private void addSet(){
        Intent intent = new Intent(this,AddGroupActivity.class);
        startActivityForResult(intent,5);
        /*
        final String[] groupName = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(MySpaceActivity.this,R.layout.dialog_addgroup,null);
        final EditText name = view.findViewById(R.id.mySpace_dialog_name);
        builder.setCancelable(true);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                groupName[0] = name.getText().toString();
                Log.e("文集名字",groupName[0]);
                addArticleGroup(UserUtil.RECENT_USER_ID,groupName[0]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
         */
    }

    private void addArticleGroup(int userID,String groupName) {
        mySpacePresenter.addGroups(userID,groupName);
    }

    @Override
    public void addGroupFailed() {
        Toast.makeText(this, "偶然：添加失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addGroupSuccessful(Set set) {
        EventBus.getDefault().postSticky(set);
    }

    /**
     * 关注方法
     */
    private void addFollow(){

    }

}
