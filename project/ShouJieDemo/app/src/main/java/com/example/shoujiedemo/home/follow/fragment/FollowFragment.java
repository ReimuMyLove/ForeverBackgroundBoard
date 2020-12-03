package com.example.shoujiedemo.home.follow.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Article;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Heart;
import com.example.shoujiedemo.entity.Poem;
import com.example.shoujiedemo.home.follow.adapter.ArticleViewHodler;
import com.example.shoujiedemo.home.follow.adapter.FollowContentAdapter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowInitPresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowInitPresenterImpl;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.view.FollowView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的关注
 */
public class FollowFragment extends Fragment implements FollowView {

    private List<Content> contents = new ArrayList<>();//关注内容列表
    private RecyclerView contentRlv;
    private FollowContentAdapter contentAdapter;
    private MyFollowInitPresenter presenter;
    private List<Content> contentList;
    private MsgEvent msgEvent;

    public FollowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Log.e("fragment","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        presenter = new MyFollowInitPresenterImpl(this);
        Log.e("fragment","onCreateView");
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     *  初始化控件
     * @param view
     */
    private void initView(View view) {
        contentRlv = view.findViewById(R.id.follow_rlv_view);

        presenter.confirmInitContent();

    }

    /**
     * 加载关注列表内容
     */
    @Override
    public void showContentListData(List<Content> contents) {
        contentList = contents;
        contentAdapter = new FollowContentAdapter(getContext(),contents);
        contentRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        contentRlv.setAdapter(contentAdapter);
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
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("fragment","onStop");
    }
}
