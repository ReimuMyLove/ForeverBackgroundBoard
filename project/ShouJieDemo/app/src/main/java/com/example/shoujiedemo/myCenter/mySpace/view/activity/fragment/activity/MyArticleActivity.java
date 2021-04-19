package com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.follow.adapter.FollowContentAdapter;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MyArticleView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.UserUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyArticleActivity extends BaseActivity implements MyArticleView {
    SmartRefreshLayout
            hot_smartRefresh;         //smartRefreshView
    RecyclerView
            hot_rlv_view;             //RecyclerView
    MySpacePresenter
            mySpacePresenter;

    private MsgEvent hotEvent;
    private List<Content> contentList = new ArrayList<>();
    private FollowContentAdapter adapter;
    private MsgEvent msgEvent;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hot);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if(bundle != null){
            groupId = bundle.getInt("groupID");
        }
        findView();
        EventBus.getDefault().register(this);
        mySpacePresenter = new MySpacePresenter(this);

        hot_smartRefresh.setEnableRefresh(false);
        //setData();
        hot_smartRefresh.setHeaderHeight(100);
        hot_smartRefresh.setFooterHeight(150);
        hot_smartRefresh.setEnableLoadMore(false);
        setData();

        /*hot_smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    setData();
                    refreshLayout.finishLoadMore(600);
            }
        });*/
    }

    private void findView(){
        hot_rlv_view = findViewById(R.id.hot_rlv_view);
        hot_smartRefresh = findViewById(R.id.hot_smartrefresh);
    }

    private void setData(){
        /*int groupID = -1;
        if(getIntent().getStringExtra("groupID")!=null){
            groupID = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("groupID")));
        }*/
        mySpacePresenter.getGroupDetail(groupId,UserUtil.USER_ID);
    }

    /**
     * 获取文集数据回调方法
     * @param contents  列表
     */
    @Override
    public void getGroupDetailSuccessful(List<Content> contents) {
        Log.e("文集内容",contents.toString());
        contentList.addAll(contents);
        adapter = new FollowContentAdapter(this,contentList);
        hot_rlv_view.setLayoutManager(new LinearLayoutManager(this));
        hot_rlv_view.setAdapter(adapter);
    }

    @Override
    public void getGroupDetailFailed() {
        Toast.makeText(this, "偶然：获取文集内容失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("Article","onDestroy");
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
                        adapter.notifyDataSetChanged();
                    }else{
                        for(Content content:contentList){
                            if(event.getId() == content.getId()){
                                content.setLike(false);
                                content.setLikes(content.getLikes() - 1);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();

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
                        adapter.notifyDataSetChanged();
                    }else{
                        for(Content content:contentList){
                            if(event.getId() == content.getId()){
                                content.setCollect(false);
                                content.setCollectnum(content.getCollectnum() - 1);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();

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
                    adapter.notifyDataSetChanged();
                    hotEvent = null;
                    break;
                case "follow":
                    if(event.isValue()){
                        for(Content content:contentList){
                            if(content.getUser().getId() == event.getId()){
                                content.getUser().setFollow(true);
                                break;
                            }
                        }
                    }else{
                        for(Content content:contentList){
                            if(content.getUser().getId() == event.getId()){
                                content.getUser().setFollow(false);
                                break;
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    hotEvent = null;
                    break;
            }
        }
    }
}
