package com.example.shoujiedemo.fround.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.HotEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.fround.adapter.HotAdapter;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.HotLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.HotLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.view.HotView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门页面
 */
public class HotFragment extends Fragment implements HotView {

    private HotEvent hotEvent;
    private List<Content> contentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HotLoadDataPresenter presenter;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;
    private HotAdapter hotAdapter;

    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pageNum = 0;
        Log.e("Hot","onAttach");
        Log.e("onAttach",contentList.size() + "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HotLoadDataPresenterImpl(this);
        Log.e("Hot","onCreate");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Hot","onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Hot","onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Hot","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Hot","onPause");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Hot","onDetach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        recyclerView = view.findViewById(R.id.hot_rlv_view);
        smartRefreshLayout = view.findViewById(R.id.hot_smartrefresh);
        smartRefreshLayout.setHeaderHeight(100);
        if (pageNum == 0){
            smartRefreshLayout.autoRefresh();
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ++pageNum;
                presenter.confirmInitContent(pageNum);
                refreshLayout.finishRefresh();
                Log.i("page",pageNum + "");
            }
        });
        Log.e("Hot","onCreateView");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Hot","onDestroy");
        //contentList.clear();
    }

    /**
     * 点赞后回调改变fragment状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onHotEventThread(HotEvent event){
        Log.d("event",event.getType() + "");
        hotEvent = event;
        if(hotEvent != null){
            switch(hotEvent.getType()){
                case "like":
                    if(hotEvent.isValue()){
                        contentList.get(hotEvent.getPosition()).setLike(true);
                        contentList.get(hotEvent.getPosition()).setLikes(contentList.get(hotEvent.getPosition()).getLikes() + 1);
                        hotAdapter.notifyDataSetChanged();
                        Log.e("fragment","true");
                    }else{
                        contentList.get(hotEvent.getPosition()).setLike(false);
                        contentList.get(hotEvent.getPosition()).setLikes(contentList.get(hotEvent.getPosition()).getLikes() - 1);
                        hotAdapter.notifyDataSetChanged();
                        Log.e("fragment","false");
                    }
                    hotEvent = null;
                    break;
                case "collect":
                    if(hotEvent.isValue()){
                        contentList.get(hotEvent.getPosition()).setCollect(true);
                        contentList.get(hotEvent.getPosition()).setCollectnum(contentList.get(hotEvent.getPosition()).getCollectnum() + 1);
                        hotAdapter.notifyDataSetChanged();
                    }else{
                        contentList.get(hotEvent.getPosition()).setCollect(false);
                        contentList.get(hotEvent.getPosition()).setCollectnum(contentList.get(hotEvent.getPosition()).getCollectnum() - 1);
                        hotAdapter.notifyDataSetChanged();
                    }
                    hotEvent = null;
                    break;
                case "comment":
                    contentList.get(hotEvent.getPosition()).setCheatnum(hotEvent.getIntValue());
                    hotAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void showContentListData(List<Content> contents) {
        Log.e("contents",contentList.size() + "");
        if(contentList.size() == 0 || contentList == null) {
            this.contentList = contents;
            hotAdapter = new HotAdapter(getContext(),contentList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(hotAdapter);
            Log.e("contents","true");
        }else{
            contentList.addAll(contents);
            hotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void noData() {
        pageNum--;
        Log.i("page",pageNum + "");
        smartRefreshLayout.finishRefresh();
        Toast.makeText(getContext(),"失败",Toast.LENGTH_SHORT).show();
    }
}
