package com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.R;

/**
 * 感悟页面
 */
public class MySpaceIdeaFragment extends Fragment {
    View contentView;

    RecyclerView
            mySpace_ideaGroup;          //感悟列表

    public MySpaceIdeaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
            return contentView;
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mind, container, false);
    }

    private void findView(View view){
        mySpace_ideaGroup = view.findViewById(R.id.mySpace_ideaGroup);
    }
}
