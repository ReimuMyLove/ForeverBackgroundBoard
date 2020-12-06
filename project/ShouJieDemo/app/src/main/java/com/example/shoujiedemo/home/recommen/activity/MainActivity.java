package com.example.shoujiedemo.home.recommen.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.fround.fragments.FroundFragment;
import com.example.shoujiedemo.home.recommen.fragment.HomeFragment;
import com.example.shoujiedemo.fragment.MessageFragment;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.OwnerFragment;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.StatusBarUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager2 viewPager2;
    private HomeFragment homeFragment;
    private FroundFragment froundFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //初始化ViewPager2,Fragment列表
        viewPager2 = findViewById(R.id.main_view);
        List<Fragment> fragments = new ArrayList<>();
        homeFragment = new HomeFragment(viewPager2);
        fragments.add(homeFragment);
        froundFragment = new FroundFragment(viewPager2,this);
        fragments.add(froundFragment);
        fragments.add(new MessageFragment());
        fragments.add(new OwnerFragment());
        //绑定Fragment

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(this,fragments);
        viewPager2.setAdapter(myFragmentPagerAdapter);

        //初始化BottomNative
        final BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //监听页面状态
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //滑动页面时底部按钮改变
                navigationView.getMenu().getItem(position).setChecked(true);
                if(position > 1){
                    viewPager2.setUserInputEnabled(true);
                }else{
                    viewPager2.setUserInputEnabled(false);
                }
                if(position <= 2){
                    StatusBarUtil.setStatusBarDarkTheme(MainActivity.this,true);
                }else{
                    StatusBarUtil.setStatusBarDarkTheme(MainActivity.this,false);
                }

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

        });


    }


    //底部按钮绑定fragment改变
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.item_home:
                    viewPager2.setCurrentItem(0,false);
                    break;
                case R.id.item_fround:
                    viewPager2.setCurrentItem(1,false);
                    break;
                case R.id.item_message:
                    viewPager2.setCurrentItem(2,false);
                    break;
                case R.id.item_owner:
                    viewPager2.setCurrentItem(3,false);
                    break;
            }
            return true;
        }

    };

}
