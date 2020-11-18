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

    public FroundFragment() {
        // Required empty public constructor
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

        froundViewPager2.setAdapter(new MyFragmentPagerAdapter(getActivity(),fragments));

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

    }

}
