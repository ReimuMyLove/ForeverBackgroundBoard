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
import com.example.shoujiedemo.bean.SearchEvent;
import com.example.shoujiedemo.bean.UploadBean;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.adapter.ArticleAdapter;
import com.example.shoujiedemo.fround.adapter.HotAdapter;
import com.example.shoujiedemo.fround.presenter.ArticleLoadDataPresenterImpl;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenter;
import com.example.shoujiedemo.fround.view.ArticleView;
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
 * 文章页面
 */
public class ArticleFragment extends Fragment implements ArticleView {

    private List<Content> articleList = new ArrayList<>();
    private List<Content> searchList = new ArrayList<>();
    private ArticleAdapter articleAdapter;
    private ArticleAdapter searchAdapter;
    private RecyclerView recyclerView;
    private FroundLoadDataPresenter presenter;
    private MsgEvent msgEvent;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;
    private int refreshTag = 0;
    private boolean isRefresh = false;
    private int searchTag = 0;
    private boolean isSearchRefresh = false;
    private String flag = "";
    private int search = 0;


    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = 1;
        presenter = new ArticleLoadDataPresenterImpl(this);
        EventBus.getDefault().register(this);
        Log.e("Article","onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isRefresh && articleList.size() == 0){
            presenter.confirmInitContent(0, 1, UserUtil.USER_ID);
        }
        Log.i("Article","onPase");
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
        smartRefreshLayout.setFooterHeight(150);
        smartRefreshLayout.setEnableLoadMore(true);
        if(!isRefresh) {
            smartRefreshLayout.autoRefresh();
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(search == 1 && flag.length() != 0) {
                    pageNum = 1;
                    searchList.clear();
                    searchTag = 0;
                    presenter.searchData(flag,0,pageNum,UserUtil.USER_ID);
                    refreshLayout.finishRefresh(600);
                }else{
                    pageNum = 1;
                    articleList.clear();
                    refreshTag = 0;
                    presenter.confirmInitContent(0, 1, UserUtil.USER_ID);
                    refreshLayout.finishRefresh(600);
                }

            }

        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(flag.length() != 0 && search == 1){
                    presenter.searchData(flag,0,pageNum,UserUtil.USER_ID);
                    refreshLayout.finishLoadMore(600);
                }else {
                    presenter.confirmInitContent(0, pageNum, UserUtil.USER_ID);
                    refreshLayout.finishLoadMore(600);
                }
            }
        });
        return view;
    }


    @Override
    public void showContentListData(List<Content> articles) {
        if(articleList.size() < 10 || articleList == null) {
            if(refreshTag == 0) {
                this.articleList = articles;
                articleAdapter = new ArticleAdapter(articleList,getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(articleAdapter);
                refreshTag = 1;
            }else{
                List<Content> newContents = new ArrayList<>();
                for(Content content :articleList){
                    for(Content content1:articles){
                        if(content.getId() == content1.getId())
                            newContents.add(content1);
                    }
                }
                articleList.addAll(articles);
                articleList.removeAll(newContents);
                articleAdapter.notifyDataSetChanged();
            }
            if(articleList.size() == 10)
                pageNum++;
        }else{
            List<Content> newList = new ArrayList<>();
            for(Content content :articleList){
                for(Content content1:articles){
                    if(content.getId() == content1.getId())
                        newList.add(content1);
                }
            }
            articleList.addAll(articles);
            articleList.removeAll(newList);
            if(articleList.size() % 10 == 0)
                ++pageNum;
            articleAdapter.notifyDataSetChanged();
        }
        isRefresh = true;
    }

    @Override
    public void showSearchList(List<Content> contents) {
        /*if(contents != null) {
            ArticleAdapter searthAdapter = new ArticleAdapter(contents,getActivity());
            recyclerView.setAdapter(searthAdapter);
            pageNum++;
        }*/
        if(searchList.size() < 10 || searchList == null) {
            if(searchTag == 0) {
                this.searchList = contents;
                searchAdapter = new ArticleAdapter(searchList, getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(searchAdapter);
                searchTag = 1;
            }else{
                List<Content> newContents = new ArrayList<>();
                for(Content content :searchList){
                    for(Content content1:contents){
                        if(content.getId() == content1.getId())
                            newContents.add(content1);
                    }
                }
                searchList.addAll(contents);
                searchList.removeAll(newContents);
                searchAdapter.notifyDataSetChanged();
            }
            if(articleList.size() == 10)
                pageNum++;
        }else{
            List<Content> newList = new ArrayList<>();
            for(Content content :searchList){
                for(Content content1:contents){
                    if(content.getId() == content1.getId())
                        newList.add(content1);
                }
            }
            searchList.addAll(contents);
            searchList.removeAll(newList);
            if(searchList.size() % 10 == 0)
                ++pageNum;
            searchAdapter.notifyDataSetChanged();
        }
        isSearchRefresh = true;
    }

    @Override
    public void noData() {
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
        msgEvent = event;
        if(msgEvent != null){
            switch(msgEvent.getType()){
                case "like":
                    if(msgEvent.isValue()){
                        for(Content content:articleList){
                            if(event.getId() == content.getId()){
                                content.setLike(true);
                                content.setLikes(content.getLikes() + 1);
                                break;
                            }
                        }
                        articleAdapter.notifyDataSetChanged();
                    }else{
                        for(Content content:articleList){
                            if(event.getId() == content.getId()){
                                content.setLike(false);
                                content.setLikes(content.getLikes() - 1);
                                break;
                            }
                        }
                        articleAdapter.notifyDataSetChanged();

                    }
                    msgEvent = null;
                    break;
                case "collect":
                    if(msgEvent.isValue()){
                        for(Content content:articleList){
                            if(event.getId() == content.getId()){
                                content.setCollect(true);
                                content.setCollectnum(content.getCollectnum() + 1);
                                break;
                            }
                        }
                        articleAdapter.notifyDataSetChanged();
                    }else{
                        for(Content content:articleList){
                            if(event.getId() == content.getId()){
                                content.setCollect(false);
                                content.setCollectnum(content.getCollectnum() - 1);
                                break;
                            }
                        }
                        articleAdapter.notifyDataSetChanged();

                    }
                    msgEvent = null;
                    break;
                case "comment":
                    if(event.isValue()) {
                        for (Content content : articleList) {
                            if (event.getId() == content.getId()) {
                                content.setCheatnum(content.getCheatnum() + 1);
                                break;
                            }
                        }
                    }else{
                        for (Content content : articleList) {
                            if (event.getId() == content.getId()) {
                                content.setCheatnum(content.getCheatnum() - 1);
                                break;
                            }
                        }
                    }
                    articleAdapter.notifyDataSetChanged();
                    msgEvent = null;
                    break;
                case "follow":
                    if(event.isValue()){
                        for(Content content:articleList){
                            if(content.getUser().getId() == event.getId()){
                                content.getUser().setFollow(true);
                                break;
                            }
                        }
                    }else{
                        for(Content content:articleList){
                            if(content.getUser().getId() == event.getId()){
                                content.getUser().setFollow(false);
                                break;
                            }
                        }
                    }
                    articleAdapter.notifyDataSetChanged();
                    msgEvent = null;
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void SearchMain(SearchEvent event){
        flag = event.getTag();
        if(event.getPosition() == 1) {
            search = 1;
            pageNum = 1;
            presenter.searchData(flag, 0, pageNum, UserUtil.USER_ID);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void UploadRefresh(UploadBean bean){
        if(bean.getTypeId() == 0){
            articleList.clear();
            pageNum = 1;
            presenter.confirmInitContent(0,pageNum,UserUtil.USER_ID);
        }
    }


}
