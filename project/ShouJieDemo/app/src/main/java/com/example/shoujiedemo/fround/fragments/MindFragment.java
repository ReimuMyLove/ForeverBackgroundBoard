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
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Heart;
import com.example.shoujiedemo.fround.adapter.ArticleAdapter;
import com.example.shoujiedemo.fround.adapter.HeartAdapter;
import com.example.shoujiedemo.fround.presenter.ArticleLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.HeartLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.view.ArticleView;
import com.example.shoujiedemo.fround.view.HeartView;
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

    private HeartEvent msgEvent;
    private List<Content> heartList = new ArrayList<>();
    private HeartAdapter heartAdapter;
    private RecyclerView recyclerView;
    private FroundLoadDataPresenter presenter;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;

    public MindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = 0;
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
        if(pageNum == 0) {
            smartRefreshLayout.autoRefresh();
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ++pageNum;
                presenter.confirmInitContent(2,pageNum);
                refreshLayout.finishRefresh(400);

            }

        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                presenter.confirmInitContent(2,pageNum);
                refreshLayout.finishLoadMore(400);
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
        if(heartList.size() == 0 || heartList == null) {
            this.heartList = hearts;
            heartAdapter = new HeartAdapter(heartList, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(heartAdapter);
        }else{
            heartList.addAll(hearts);
            heartAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onData() {
        pageNum--;
        Log.i("page",pageNum + "");
        smartRefreshLayout.finishRefresh();
        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
    }

    /**
     * 点赞后回调改变fragment状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onHeartEventThread(HeartEvent event){
        Log.d("event",event.getType() + "");
        msgEvent = event;
        if(msgEvent != null){
            switch(msgEvent.getType()){
                case "like":
                    if(msgEvent.isValue()){
                        heartList.get(msgEvent.getPosition()).setLike(true);
                        heartList.get(msgEvent.getPosition()).setLikes(heartList.get(msgEvent.getPosition()).getLikes() + 1);
                        heartAdapter.notifyDataSetChanged();
                        Log.e("fragment","true");
                    }else{
                        heartList.get(msgEvent.getPosition()).setLike(false);
                        heartList.get(msgEvent.getPosition()).setLikes(heartList.get(msgEvent.getPosition()).getLikes() - 1);
                        heartAdapter.notifyDataSetChanged();
                        Log.e("fragment","false");
                    }
                    msgEvent = null;
                    break;
                case "collect":
                    if(msgEvent.isValue()){
                        heartList.get(msgEvent.getPosition()).setCollect(true);
                        heartList.get(msgEvent.getPosition()).setCollectnum(heartList.get(msgEvent.getPosition()).getCollectnum() + 1);
                        heartAdapter.notifyDataSetChanged();
                    }else{
                        heartList.get(msgEvent.getPosition()).setCollect(false);
                        heartList.get(msgEvent.getPosition()).setCollectnum(heartList.get(msgEvent.getPosition()).getCollectnum() - 1);
                        heartAdapter.notifyDataSetChanged();
                    }
                    msgEvent = null;
                    break;
                case "comment":
                    heartList.get(msgEvent.getPosition()).setCheatnum(msgEvent.getIntValue());
                    heartAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
