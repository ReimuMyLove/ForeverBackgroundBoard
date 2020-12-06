package com.example.shoujiedemo.fround.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.HeartEvent;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.bean.PoemEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Heart;
import com.example.shoujiedemo.fround.adapter.ArticleAdapter;
import com.example.shoujiedemo.fround.adapter.PoemAdapter;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.PoemLoadPresenterImpl;
import com.example.shoujiedemo.fround.view.PoemView;
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
 * 诗页面
 */
public class PoemFragment extends Fragment implements PoemView {

    private List<Content> poemList = new ArrayList<>();
    private PoemAdapter poemAdapter;
    private RecyclerView recyclerView;
    private FroundLoadDataPresenter presenter;
    private PoemEvent msgEvent;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;

    public PoemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = 0;
        presenter = new PoemLoadPresenterImpl(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poem, container, false);
        view.findViewById(R.id.poem_rlv_view);

        recyclerView = view.findViewById(R.id.poem_rlv_view);

        smartRefreshLayout = view.findViewById(R.id.poem_smartrefresh);
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
                presenter.confirmInitContent(3,pageNum);
                refreshLayout.finishRefresh(400);

            }

        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                presenter.confirmInitContent(3,pageNum);
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
    public void showContentListData(List<Content> poems) {
        if(poemList.size() == 0 || poemList == null) {
            this.poemList = poems;
            poemAdapter = new PoemAdapter(poemList, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(poemAdapter);
        }else{
            poemList.addAll(poems);
            poemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void noData() {
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
    public void onPoemEventThread(PoemEvent event){
        Log.d("event",event.getType() + "");
        msgEvent = event;
        if(msgEvent != null){
            switch(msgEvent.getType()){
                case "like":
                    if(msgEvent.isValue()){
                        poemList.get(msgEvent.getPosition()).setLike(true);
                        poemList.get(msgEvent.getPosition()).setLikes(poemList.get(msgEvent.getPosition()).getLikes() + 1);
                        poemAdapter.notifyDataSetChanged();
                        Log.e("fragment","true");
                    }else{
                        poemList.get(msgEvent.getPosition()).setLike(false);
                        poemList.get(msgEvent.getPosition()).setLikes(poemList.get(msgEvent.getPosition()).getLikes() - 1);
                        poemAdapter.notifyDataSetChanged();
                        Log.e("fragment","false");
                    }
                    msgEvent = null;
                    break;
                case "collect":
                    if(msgEvent.isValue()){
                        poemList.get(msgEvent.getPosition()).setCollect(true);
                        poemList.get(msgEvent.getPosition()).setCollectnum(poemList.get(msgEvent.getPosition()).getCollectnum() + 1);
                        poemAdapter.notifyDataSetChanged();
                    }else{
                        poemList.get(msgEvent.getPosition()).setCollect(false);
                        poemList.get(msgEvent.getPosition()).setCollectnum(poemList.get(msgEvent.getPosition()).getCollectnum() - 1);
                        poemAdapter.notifyDataSetChanged();
                    }
                    msgEvent = null;
                    break;
                case "comment":
                    poemList.get(msgEvent.getPosition()).setCheatnum(msgEvent.getIntValue());
                    poemAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
