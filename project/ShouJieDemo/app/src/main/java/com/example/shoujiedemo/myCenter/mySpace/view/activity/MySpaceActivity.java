package com.example.shoujiedemo.myCenter.mySpace.view.activity;

import android.os.Bundle;
import android.telephony.ims.RegistrationManager;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpaceArticleFragment;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpaceIdeaFragment;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.MySpacePoemFragment;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MySpaceActivity extends BaseActivity {
    ImageView
            mySpace_background;             //获取用户背景
    CircleImageView
            mySpace_userImg;                //获取用户头像
    TextView
            mySpace_userName;               //获取用户名
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

    private int currentPosition = 0;//当前页面位置
    private int oldPosition = 0;//上一个滑动的页面位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myspace);
        //获取view控件
        FindView();
        //设置控件宽高
        SetViewHW();
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
                    currentPosition = 0;
                }else if(position == 1){
                    tab.setText("　　感悟　　");
                    mySpace_view.setCurrentItem(1);
                    currentPosition = 1;
                }else if(position == 2){
                    tab.setText("　　诗　　");
                    mySpace_view.setCurrentItem(2);
                    currentPosition = 2;
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
}
