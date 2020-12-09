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
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.bean.SearchEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.fround.adapter.HotAdapter;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.HotLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.HotLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.view.HotView;
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
 * 热门页面
 */
public class HotFragment extends Fragment implements HotView {

    private MsgEvent hotEvent;
    private List<Content> contentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HotLoadDataPresenter presenter;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;
    private HotAdapter hotAdapter;
    private int refreshTag = 0;

    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pageNum = 1;
        Log.e("Hot","onAttach");
        Log.e("onAttach",contentList.size() + "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HotLoadDataPresenterImpl(this);
        EventBus.getDefault().register(this);
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
        smartRefreshLayout.setFooterHeight(150);
        smartRefreshLayout.setEnableLoadMore(true);
        if (pageNum == 1){
            smartRefreshLayout.autoRefresh();
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                presenter.confirmInitContent(pageNum);
                refreshLayout.finishRefresh(600);

            }

        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                presenter.confirmInitContent(pageNum);
                refreshLayout.finishLoadMore(600);
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
    public void onMainEventThread(MsgEvent event){
        Log.d("event",event.getType() + "");
        hotEvent = event;
        if(hotEvent != null){
            switch(hotEvent.getType()){
                case "like":
                    if(hotEvent.isValue()){
                        for(Content content:contentList){
                            if(event.getId() == content.getId()){
                                content.setLike(true);
                                content.setLikes(content.getLikes() + 1);
                                break;
                            }
                        }
                        hotAdapter.notifyDataSetChanged();
                    }else{
                        for(Content content:contentList){
                            if(event.getId() == content.getId()){
                                content.setLike(false);
                                content.setLikes(content.getLikes() - 1);
                                break;
                            }
                        }
                        hotAdapter.notifyDataSetChanged();

                    }
                    hotEvent = null;
                    break;
                case "collect":
                    if(hotEvent.isValue()){
                        for(Content content:contentList){
                            if(event.getId() == content.getId()){
                                content.setCollect(true);
                                content.setCollectnum(content.getCollectnum() + 1);
                                break;
                            }
                        }
                        hotAdapter.notifyDataSetChanged();
                    }else{
                        for(Content content:contentList){
                            if(event.getId() == content.getId()){
                                content.setCollect(false);
                                content.setCollectnum(content.getCollectnum() - 1);
                                break;
                            }
                        }
                        hotAdapter.notifyDataSetChanged();

                    }
                    hotEvent = null;
                    break;
                case "comment":
                    if(event.isValue()) {
                        for (Content content : contentList) {
                            if (event.getId() == content.getId()) {
                                content.setCheatnum(content.getCheatnum() + 1);
                                break;
                            }
                        }
                    }else{
                        for (Content content : contentList) {
                            if (event.getId() == content.getId()) {
                                content.setCheatnum(content.getCheatnum() - 1);
                                break;
                            }
                        }
                    }
                    hotAdapter.notifyDataSetChanged();

                    break;
            }
        }
    }

    @Override
    public void showContentListData(List<Content> contents) {
        if(contentList.size() < 10 || contentList == null) {
            if(refreshTag == 0) {
                this.contentList = contents;
                hotAdapter = new HotAdapter(getContext(), contentList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(hotAdapter);
                refreshTag = 1;
            }else{
                List<Content> newContents = new ArrayList<>();
                for(Content content :contentList){
                    for(Content content1:contents){
                        if(content.getId() == content1.getId())
                            newContents.add(content1);
                    }
                }
                contentList.addAll(contents);
                contentList.removeAll(newContents);
                hotAdapter.notifyDataSetChanged();
            }
            if(contentList.size() == 10)
                pageNum++;
        }else{
            List<Content> newList = new ArrayList<>();
            for(Content content :contentList){
                for(Content content1:contents){
                    if(content.getId() == content1.getId())
                        newList.add(content1);
                }
            }
            contentList.addAll(contents);
            contentList.removeAll(newList);
            if(contentList.size() % 10 == 0)
                ++pageNum;
            hotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showSearchList(List<Content> contents, String flag) {

    }

    @Override
    public void noData() {
        pageNum--;
        Log.i("page",pageNum + "");
        smartRefreshLayout.finishRefresh();
        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void SearchMain(SearchEvent event){
        List<Content> searchList = new ArrayList<>();
        if(event.getPosition() == 0){
            for(Content content :contentList){
                if(!content.toString().contains(event.getTag())){
                    searchList.add(content);
                }
            }
            contentList.removeAll(searchList);
            hotAdapter.notifyDataSetChanged();
        }
    }
}