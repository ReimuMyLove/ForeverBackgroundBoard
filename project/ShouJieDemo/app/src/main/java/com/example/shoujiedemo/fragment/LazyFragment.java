package com.example.shoujiedemo.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LazyFragment extends Fragment {

    private boolean isFirstLoad = true;//判断是否为第一次加载
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(getItemResourceId(), null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFirstLoad){
            initData();
            isFirstLoad = false;
        }

    }

    /**
     * 获取布局文件id
     * @return
     */
    public abstract int getItemResourceId();

    /**
     * 初始化数据
     */
    public void initData(){

    }

    /**
     * 初始化View
     * @param view 视图
     */
    protected void initView(View view){

    }

    /**
     * 初始化事件
     */
    protected void initEvent(){

    }

}
