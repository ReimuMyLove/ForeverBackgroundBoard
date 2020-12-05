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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.ArticleEvent;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.fround.adapter.ArticleAdapter;
import com.example.shoujiedemo.fround.presenter.ArticleLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenter;
import com.example.shoujiedemo.fround.view.ArticleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章页面
 */
public class ArticleFragment extends Fragment implements ArticleView {

    private List<Content> articleList = new ArrayList<>();
    private ArticleAdapter articleAdapter;
    private RecyclerView recyclerView;
    private FroundLoadDataPresenter presenter;
    private ArticleEvent msgEvent;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;


    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = 0;
        presenter = new ArticleLoadDataPresenterImpl(this);
        EventBus.getDefault().register(this);
        Log.e("Article","onCreate");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Article","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("Article","onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        recyclerView = view.findViewById(R.id.article_rlv_view);
        smartRefreshLayout = view.findViewById(R.id.article_smartrefresh);
        smartRefreshLayout.setHeaderHeight(100);
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ++pageNum;
                if(pageNum == 1){
                    presenter.confirmInitContent(0,pageNum);
                    refreshLayout.finishRefresh();
                    Log.i("page",pageNum + "");
                }else{
                    presenter.confirmInitContent(0,pageNum);
                    refreshLayout.finishRefresh();
                    Log.i("page",pageNum + "");
                }
            }
        });
        return view;
    }

    /*private void initView(View view) {

        presenter.confirmInitContent(0,pageNum);
    }*/

    @Override
    public void showContentListData(List<Content> articles) {
        if(articleList.size() == 0 || articleList == null) {
            this.articleList = articles;
            articleAdapter = new ArticleAdapter(articleList, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(articleAdapter);
        }else{
            articleList.addAll(articles);
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void noData() {
        pageNum--;
        Log.i("page",pageNum + "");
        smartRefreshLayout.finishRefresh();
        Toast.makeText(getContext(),"失败",Toast.LENGTH_SHORT).show();
    }

    /**
     * 点赞后回调改变fragment状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onArticleEventThread(ArticleEvent event){
        Log.d("event",event.getType() + "");
        msgEvent = event;
        if(msgEvent != null){
            switch(msgEvent.getType()){
                case "like":
                    if(msgEvent.isValue()){
                        articleList.get(msgEvent.getPosition()).setLike(true);
                        articleList.get(msgEvent.getPosition()).setLikes(articleList.get(msgEvent.getPosition()).getLikes() + 1);
                        articleAdapter.notifyDataSetChanged();
                        Log.e("fragment","true");
                    }else{
                        articleList.get(msgEvent.getPosition()).setLike(false);
                        articleList.get(msgEvent.getPosition()).setLikes(articleList.get(msgEvent.getPosition()).getLikes() - 1);
                        articleAdapter.notifyDataSetChanged();
                        Log.e("fragment","false");
                    }
                    msgEvent = null;
                    break;
                case "collect":
                    if(msgEvent.isValue()){
                        articleList.get(msgEvent.getPosition()).setCollect(true);
                        articleList.get(msgEvent.getPosition()).setCollectnum(articleList.get(msgEvent.getPosition()).getCollectnum() + 1);
                        articleAdapter.notifyDataSetChanged();
                    }else{
                        articleList.get(msgEvent.getPosition()).setCollect(false);
                        articleList.get(msgEvent.getPosition()).setCollectnum(articleList.get(msgEvent.getPosition()).getCollectnum() - 1);
                        articleAdapter.notifyDataSetChanged();
                    }
                    msgEvent = null;
                    break;
                case "comment":
                    articleList.get(msgEvent.getPosition()).setCheatnum(msgEvent.getIntValue());
                    articleAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
