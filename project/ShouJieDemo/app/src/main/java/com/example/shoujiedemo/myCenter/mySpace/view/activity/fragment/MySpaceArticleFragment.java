package com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.R;

/**
 * 文章页面
 */
public class MySpaceArticleFragment extends Fragment {
    View contentView;

    public MySpaceArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (contentView != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
            return contentView;
        }
        return inflater.inflate(R.layout.fragment_article, container, false);
    }
}
