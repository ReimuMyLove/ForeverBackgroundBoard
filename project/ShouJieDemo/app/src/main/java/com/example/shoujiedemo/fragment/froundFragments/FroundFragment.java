package com.example.shoujiedemo.fragment.froundFragments;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 发现页面
 */
public class FroundFragment extends Fragment {

    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager2 froundViewPager2;
    private TabLayout froudnTab;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager2 viewPager2;//父Viewpager
    private int currentPosition = 0;    //当前滑动位置
    private int oldPositon = 0;          //上一个滑动位置
    private int currentState = 0;        //记录当前手指按下状态
    private List<Integer> scrolledPixeledList = new ArrayList<>(); //记录手指滑动时的像素坐标记录

    public FroundFragment(ViewPager2 viewPager2) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fround, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        froundViewPager2 = view.findViewById(R.id.fround_view);
        froudnTab = view.findViewById(R.id.fround_topTab);

        fragments.add(new HotFragment());
        fragments.add(new ArticleFragment());
        fragments.add(new MindFragment());
        fragments.add(new PoemFragment());
        fragments.add(new MusicFragment());

        fragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity(),fragments);
        froundViewPager2.setAdapter(fragmentPagerAdapter);

        new TabLayoutMediator(froudnTab, froundViewPager2, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("热门");
                }else if(position == 1){
                    tab.setText("文章");
                }else if(position == 2){
                    tab.setText("感悟");
                }else if(position == 3){
                    tab.setText("诗");
                }else if(position == 4){
                    tab.setText("音乐");
                }
            }
        }).attach();

        froundViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                if(currentState == 1){
                    scrolledPixeledList.add(positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentState = state;
                if(state ==0){
                    if(currentPosition == oldPositon){
                        Log.d("position", oldPositon + "");
                        if(currentPosition == 0){
                            if (scrolledPixeledList.size() > 1 && scrolledPixeledList.get(scrolledPixeledList.size() - 1) == 0) {
                                //有可能出现滑到一半放弃的情况也是可以出现currentPosition == oldPositon=0，则先判断是否是往右滑时放弃
                                return;
                            }
                            //若还有上一个bottom fragment页面则切换
                            if(viewPager2.getCurrentItem() > 0){
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1);
                            }
                        }else if(currentPosition == fragmentPagerAdapter.getItemCount() - 1){
                            if(viewPager2.getCurrentItem() <  fragmentPagerAdapter.getItemCount() - 1){
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
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
