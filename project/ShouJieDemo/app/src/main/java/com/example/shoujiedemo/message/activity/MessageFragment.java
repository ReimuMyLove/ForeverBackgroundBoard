package com.example.shoujiedemo.message.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.bean.DepthPageTransformer;
import com.example.shoujiedemo.home.follow.fragment.FollowFragment;
import com.example.shoujiedemo.home.recommen.activity.DateActivity;
import com.example.shoujiedemo.home.recommen.fragment.RecommenFragment;
import com.example.shoujiedemo.message.fragment.CommentMessageFragment;
import com.example.shoujiedemo.message.fragment.FollowMessageFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息页面
 */
public class MessageFragment extends Fragment {

    private ViewPager2 messageView;
    private TabLayout messageTab;
    private List<Fragment> messageFragments = new ArrayList<>();
    private ViewPager2 viewPager2;
    private MyFragmentPagerAdapter fragmentStatePagerAdapter;
    private int currentPosition = 0;    //当前滑动位置
    private int oldPositon = 0;          //上一个滑动位置
    private int currentState = 0;        //记录当前手指按下状态
    private List<Integer> scrolledPixeledList = new ArrayList<>(); //记录手指滑动时的像素坐标记录
    private FollowMessageFragment followMessageFragment;
    private CommentMessageFragment commentMessageFragment;

    public MessageFragment() {
        // Required empty public constructor
    }

    public MessageFragment(ViewPager2 viewPager2){
        this.viewPager2 = viewPager2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        messageView = view.findViewById(R.id.message_view);
        messageTab = view.findViewById(R.id.tab_layout);


        if(followMessageFragment == null && commentMessageFragment == null) {
            followMessageFragment = new FollowMessageFragment();
            commentMessageFragment = new CommentMessageFragment();

            messageFragments.add(followMessageFragment);
            messageFragments.add(commentMessageFragment);
        }


       // messageView.setPageTransformer(new DepthPageTransformer());
        fragmentStatePagerAdapter = new MyFragmentPagerAdapter(getActivity(), messageFragments);
        messageView.setAdapter(fragmentStatePagerAdapter);

        messageView.setUserInputEnabled(true);
        //将ViewPager2与TabLayout绑定
        new TabLayoutMediator(messageTab, messageView, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("关注");
                } else {
                    tab.setText("评论");
                }
            }
        }).attach();

        messageView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                if (currentState == 1) {
                    scrolledPixeledList.add(positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {

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
                                Log.e("currentPosition",currentPosition + "");
                                viewPager2.setCurrentItem(1);
                            }
                        } else if (currentPosition == fragmentStatePagerAdapter.getItemCount() - 1) {
                            Log.e("elsecurrentPosition",currentPosition + "");
                            Log.e("bbbbb", + viewPager2.getCurrentItem()+"," + fragmentStatePagerAdapter.getItemCount());
                            if (viewPager2.getCurrentItem() == fragmentStatePagerAdapter.getItemCount()) {
                                viewPager2.setCurrentItem(3);
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
