package com.example.shoujiedemo.fround.fragments;


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
import com.example.shoujiedemo.bean.HeartEvent;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.bean.SearchEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Heart;
import com.example.shoujiedemo.fround.adapter.ArticleAdapter;
import com.example.shoujiedemo.fround.adapter.HeartAdapter;
import com.example.shoujiedemo.fround.presenter.ArticleLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.HeartLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.view.ArticleView;
import com.example.shoujiedemo.fround.view.HeartView;
import com.example.shoujiedemo.util.UserUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 感悟页面
 */
public class MindFragment extends Fragment implements HeartView {

    private MsgEvent msgEvent;
    private List<Content> heartList = new ArrayList<>();
    private HeartAdapter heartAdapter;
    private RecyclerView recyclerView;
    private FroundLoadDataPresenter presenter;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;
    private int refreshTag = 0;
    private boolean isRefresh = false;
    private String flag;

    public MindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = 1;
        presenter = new HeartLoadDataPresenterImpl(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mind, container, false);
        recyclerView = view.findViewById(R.id.heart_rlv_view);

        smartRefreshLayout = view.findViewById(R.id.heart_smartrefresh);
        smartRefreshLayout.setHeaderHeight(100);
        smartRefreshLayout.setFooterHeight(150);
        smartRefreshLayout.setEnableLoadMore(true);
        if(!isRefresh) {
            smartRefreshLayout.autoRefresh();
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(flag != null){
                    presenter.searchData(flag,2,pageNum,UserUtil.USER_ID);
                }
                heartList.clear();
                presenter.confirmInitContent(2,1, UserUtil.USER_ID);
                refreshLayout.finishRefresh(600);

            }

        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                presenter.confirmInitContent(2,pageNum,UserUtil.USER_ID);
                refreshLayout.finishLoadMore(600);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showContentListData(List<Content> hearts) {
        if(heartList.size() < 10 || heartList == null) {
            if(refreshTag == 0) {
                this.heartList = hearts;
                heartAdapter = new HeartAdapter(heartList, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(heartAdapter);
                refreshTag = 1;
            }else{
                List<Content> newHearts = new ArrayList<>();
                for(Content content:heartList){
                    for(Content content1:hearts){
                        if(content.getId() == content1.getId())
                            newHearts.add(content1);
                    }
                }
                heartList.addAll(hearts);
                heartList.removeAll(newHearts);
                heartAdapter.notifyDataSetChanged();
            }
            if(heartList.size() == 10)
                pageNum++;
        }else{
            List<Content> newList = new ArrayList<>();
            for(Content content :heartList){
                for(Content content1:hearts){
                    if(content.getId() == content1.getId())
                        newList.add(content1);
                }
            }
            heartList.addAll(hearts);
            heartList.removeAll(newList);

            if(heartList.size() % 10 == 0)
                pageNum++;

            heartAdapter.notifyDataSetChanged();
        }

        isRefresh = true;
    }

    @Override
    public void showSearchList(List<Content> contents) {
        if(contents != null) {
            HeartAdapter searthAdapter = new HeartAdapter(contents,getActivity());
            recyclerView.setAdapter(searthAdapter);
            pageNum++;
        }
    }

    @Override
    public void onData() {
        pageNum--;
        Log.i("page",pageNum + "");
        smartRefreshLayout.finishRefresh();
        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
        isRefresh = true;
    }

    /**
     * 点赞后回调改变fragment状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMainEventThread(MsgEvent event){
        Log.d("event",event.getType() + "");
        msgEvent = event;
        if(msgEvent != null){
            switch(msgEvent.getType()){
                case "like":
                    if(msgEvent.isValue()){
                        for(Content content:heartList){
                            if(event.getId() == content.getId()){
                                content.setLike(true);
                                content.setLikes(content.getLikes() + 1);
                                break;
                            }
                        }
                        heartAdapter.notifyDataSetChanged();
                    }else{
                        for(Content content:heartList){
                            if(event.getId() == content.getId()){
                                content.setLike(false);
                                content.setLikes(content.getLikes() - 1);
                                break;
                            }
                        }
                        heartAdapter.notifyDataSetChanged();

                    }
                    msgEvent = null;
                    break;
                case "collect":
                    if(msgEvent.isValue()){
                        for(Content content:heartList){
                            if(event.getId() == content.getId()){
                                content.setCollect(true);
                                content.setCollectnum(content.getCollectnum() + 1);
                                break;
                            }
                        }
                        heartAdapter.notifyDataSetChanged();
                    }else{
                        for(Content content:heartList){
                            if(event.getId() == content.getId()){
                                content.setCollect(false);
                                content.setCollectnum(content.getCollectnum() - 1);
                                break;
                            }
                        }
                        heartAdapter.notifyDataSetChanged();
                    }
                    msgEvent = null;
                    break;
                case "comment":
                    if(msgEvent.isValue()) {
                        for (Content content : heartList) {
                            if (event.getId() == content.getId()) {
                                content.setCheatnum(content.getCheatnum() + 1);
                                break;
                            }
                        }
                    }else{
                        for (Content content : heartList) {
                            if (event.getId() == content.getId()) {
                                content.setCheatnum(content.getCheatnum() - 1);
                                break;
                            }
                        }
                    }
                    msgEvent = null;
                    heartAdapter.notifyDataSetChanged();
                    break;
                case "follow":
                    if(event.isValue()){
                        for(Content content:heartList){
                            if(content.getUser().getId() == event.getId()){
                                content.getUser().setFollow(true);
                                break;
                            }
                        }
                    }else{
                        for(Content content:heartList){
                            if(content.getUser().getId() == event.getId()){
                                content.getUser().setFollow(false);
                                break;
                            }
                        }
                    }
                    heartAdapter.notifyDataSetChanged();
                    msgEvent = null;
                    break;
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void SearchMain(SearchEvent event){

        if(event.getPosition() == 2) {
            flag = event.getTag();
            pageNum = 1;
            presenter.searchData(flag, 2, pageNum, UserUtil.USER_ID);
        }
    }
}
