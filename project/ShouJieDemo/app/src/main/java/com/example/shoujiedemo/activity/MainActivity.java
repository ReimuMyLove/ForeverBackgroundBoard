package com.example.shoujiedemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.fragment.froundFragments.FroundFragment;
import com.example.shoujiedemo.fragment.homeFragments.HomeFragment;
import com.example.shoujiedemo.fragment.MessageFragment;
import com.example.shoujiedemo.fragment.OwnerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private int currentPosition = 0;//当前页面位置
    private int oldPostion = 0;//上一个滑动的页面位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化ViewPager2,Fragment列表
        viewPager2 = findViewById(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FroundFragment());
        fragments.add(new MessageFragment());
        fragments.add(new OwnerFragment());
        //绑定Fragment
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(this,fragments);
        viewPager2.setOffscreenPageLimit(fragments.size());//设置预加载页
        viewPager2.setAdapter(myFragmentPagerAdapter);
        viewPager2.setUserInputEnabled(false);


        //初始化BottomNative
        final BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);
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
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
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
                    viewPager2.setCurrentItem(0);
                    break;
                case R.id.item_fround:
                    viewPager2.setCurrentItem(1);
                    break;
                case R.id.item_message:
                    viewPager2.setCurrentItem(2);
                    break;
                case R.id.item_owner:
                    viewPager2.setCurrentItem(3);
                    break;
            }
            return true;
        }

    };
}