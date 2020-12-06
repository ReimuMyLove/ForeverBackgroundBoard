package com.example.shoujiedemo.home.recommen.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;

import com.example.shoujiedemo.bean.DepthPageTransformer;
import com.example.shoujiedemo.bean.ScaleInTransformer;
import com.example.shoujiedemo.home.follow.fragment.FollowFragment;
import com.example.shoujiedemo.util.StatusBarUtil;
import com.google.android.material.tabs.TabLayout;

import com.example.shoujiedemo.R;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */
public class HomeFragment extends Fragment {

    private ViewPager2 homeView;
    private TabLayout homeTab;
    private List<Fragment> homeFragments = new ArrayList<>();
    private ViewPager2 viewPager2;
    private MyFragmentPagerAdapter fragmentStatePagerAdapter;
    private int currentPosition = 0;    //当前滑动位置
    private int oldPositon = 0;          //上一个滑动位置
    private int currentState = 0;        //记录当前手指按下状态
    private List<Integer> scrolledPixeledList = new ArrayList<>(); //记录手指滑动时的像素坐标记录
    private RecommenFragment recommenFragment;
    private FollowFragment followFragment;


    public HomeFragment(){

    }

    public HomeFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.e("Recommen","onCreate");
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e("start","開水");
    }

    /**
     * 初始化视图
     */
    private void initView(View view) {
        homeView = view.findViewById(R.id.home_view);
        homeTab = view.findViewById(R.id.tab_layout);

        if(recommenFragment == null) {
            recommenFragment = new RecommenFragment(homeView);
            followFragment = new FollowFragment();
            homeFragments.add(recommenFragment);
            homeFragments.add(followFragment);
        }


            homeView.setUserInputEnabled(true);
            homeView.setPageTransformer(new DepthPageTransformer());
            fragmentStatePagerAdapter = new MyFragmentPagerAdapter(getActivity(), homeFragments);
            homeView.setAdapter(fragmentStatePagerAdapter);

            //将ViewPager2与TabLayout绑定
            new TabLayoutMediator(homeTab, homeView, true, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    if (position == 0) {
                        tab.setText("每日美文");
                    } else {
                        tab.setText("我的关注");
                    }
                }
            }).attach();

            homeView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPosition = position;
                    if (currentState == 1) {
                        scrolledPixeledList.add(positionOffsetPixels);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                    if (position > 0) {
                        homeView.setUserInputEnabled(true);
                    } else {
                        homeView.setUserInputEnabled(false);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    currentState = state;
                    if (state == 0) {
                        if (currentPosition == oldPositon) {
                            Log.d("position", oldPositon + "");
                            if (currentPosition == 0) {
                                if (scrolledPixeledList.size() > 1 && scrolledPixeledList.get(scrolledPixeledList.size() - 1) == 0) {
                                    //有可能出现滑到一半放弃的情况也是可以出现currentPosition == oldPositon=0，则先判断是否是往右滑时放弃
                                    return;
                                }
                                //若还有上一个bottom fragment页面则切换
                                if (viewPager2.getCurrentItem() > 0) {

                                }
                            } else if (currentPosition == fragmentStatePagerAdapter.getItemCount() - 1) {
                                if (viewPager2.getCurrentItem() < fragmentStatePagerAdapter.getItemCount() - 1) {
                                    viewPager2.setCurrentItem(1);
                                }
                            }
                        }
                        oldPositon = currentPosition;
                        scrolledPixeledList.clear();//清空滑动记录
                    }
                }
            });

    }


}
