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
 * 诗页面
 */
public class MySpacePoemFragment extends Fragment {
    View contentView;
    RecyclerView
            mySpace_poemGroup;          //诗列表

    public MySpacePoemFragment() {
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
        contentView = inflater.inflate(R.layout.fragment_poem, container, false);
        // Inflate the layout for this fragment
        return contentView;
    }

    private void findView(View view){
        mySpace_poemGroup = view.findViewById(R.id.mySpace_poemGroup);
    }
}
