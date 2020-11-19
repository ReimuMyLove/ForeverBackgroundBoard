package com.example.shoujiedemo.home.recommen.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
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
    private int currentPosition = 0;//当前页面位置

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }


    /**
     * 初始化视图
     */
    private void initView(View view) {
        homeView = view.findViewById(R.id.home_view);
        homeTab = view.findViewById(R.id.tab_layout);

        homeFragments.add(new RecommenFragment());
        homeFragments.add(new FollowFragment());

        homeView.setUserInputEnabled(true);
        homeView.setAdapter(new MyFragmentPagerAdapter(getActivity(),homeFragments));

        //将ViewPager2与TabLayout绑定
        new TabLayoutMediator(homeTab, homeView,true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                homeView.setUserInputEnabled(true);
                if(position == 0){
                    tab.setText("每日一言");
                }else{
                    tab.setText("我的关注");
                }
            }
        }).attach();



    }

}
