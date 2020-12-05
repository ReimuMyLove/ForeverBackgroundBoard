package com.example.shoujiedemo.home.follow.fragment;


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
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.follow.adapter.FollowContentAdapter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowInitPresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowInitPresenterImpl;
import com.example.shoujiedemo.home.follow.view.FollowView;
import com.example.shoujiedemo.util.UserUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 我的关注
 */
public class FollowFragment extends Fragment implements FollowView {

    //private List<Content> contents = new ArrayList<>();//关注内容列表
    private RecyclerView contentRlv;
    private FollowContentAdapter contentAdapter;
    private MyFollowInitPresenter presenter;
    private List<Content> contentList;//关注内容列表
    private MsgEvent msgEvent;
    private SmartRefreshLayout smartRefreshLayout;
    private int pageNum;

    public FollowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        pageNum = 0;
        presenter = new MyFollowInitPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        smartRefreshLayout = view.findViewById(R.id.follow_smartrefresh);
        contentRlv = view.findViewById(R.id.follow_rlv_view);
        smartRefreshLayout.setHeaderHeight(100);
        smartRefreshLayout.autoRefresh();
        presenter.confirmInitContent(2,pageNum);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ++pageNum;
                presenter.confirmInitContent(2,pageNum);
                refreshLayout.finishRefresh();

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    /**
     * 加载关注列表内容
     */
    @Override
    public void showContentListData(List<Content> contents) {
        if(contentList == null) {
            contentList = contents;
            contentAdapter = new FollowContentAdapter(getContext(), contents);
            contentRlv.setLayoutManager(new LinearLayoutManager(getContext()));
            contentRlv.setAdapter(contentAdapter);

        }else{
            contentList.addAll(contents);
            contentAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(),"刷新完成",Toast.LENGTH_SHORT).show();
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
   public void onMainEventThread(MsgEvent event){
       Log.d("event",event.getType() + "");
       msgEvent = event;
       if(msgEvent != null){
           switch(msgEvent.getType()){
               case "like":
                   if(msgEvent.isValue()){
                       contentList.get(msgEvent.getPosition()).setLike(true);
                       contentList.get(msgEvent.getPosition()).setLikes(contentList.get(msgEvent.getPosition()).getLikes() + 1);
                       contentAdapter.notifyDataSetChanged();
                       Log.e("fragment","true");
                   }else{
                       contentList.get(msgEvent.getPosition()).setLike(false);
                       contentList.get(msgEvent.getPosition()).setLikes(contentList.get(msgEvent.getPosition()).getLikes() - 1);
                       contentAdapter.notifyDataSetChanged();
                       Log.e("fragment","false");
                   }
                   msgEvent = null;
                   break;
               case "collect":
                   if(msgEvent.isValue()){
                       contentList.get(msgEvent.getPosition()).setCollect(true);
                       contentList.get(msgEvent.getPosition()).setCollectnum(contentList.get(msgEvent.getPosition()).getCollectnum() + 1);
                       contentAdapter.notifyDataSetChanged();
                   }else{
                       contentList.get(msgEvent.getPosition()).setCollect(false);
                       contentList.get(msgEvent.getPosition()).setCollectnum(contentList.get(msgEvent.getPosition()).getCollectnum() - 1);
                       contentAdapter.notifyDataSetChanged();
                   }
                   msgEvent = null;
                   break;
               case "comment":
                   contentList.get(msgEvent.getPosition()).setCheatnum(msgEvent.getIntValue());
                   contentAdapter.notifyDataSetChanged();
                   break;
           }
       }
   }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("fragment","onDestroy()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("fragment","onStop");
    }
}
