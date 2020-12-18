package com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MusicAdapter;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.home.follow.adapter.FollowContentAdapter;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MyMusicView;
import com.example.shoujiedemo.util.UserUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 感悟页面
 */
public class MySpaceMusicFragment extends Fragment implements MyMusicView {
    View contentView;

    RecyclerView
            music_rlv_view;          //感悟列表
    SmartRefreshLayout
            music_smartRefresh;
    MySpacePresenter
            mySpacePresenter;
    int userID;

    private List<Music> musicList = new ArrayList<>();
    private MusicAdapter musicAdapter;
    private MsgEvent msgEvent;

    public MySpaceMusicFragment() {
        // Required empty public constructor
       // EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
            return contentView;
        }
        contentView = inflater.inflate(R.layout.fragment_music, container, false);
        //获取控件
        // Inflate the layout for this fragment
        findView(contentView);
        userID = UserUtil.RECENT_USER_ID;
        mySpacePresenter = new MySpacePresenter(this);
        getData(userID);

        music_smartRefresh.setEnableLoadMore(false);
        music_smartRefresh.setEnableRefresh(false);
        /*music_smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    getData(UserUtil.USER_ID);
                    refreshLayout.finishLoadMore(600);

            }
        });*/
        return contentView;
    }

    private void findView(View view){
        music_rlv_view = view.findViewById(R.id.music_rlv_view);
        music_smartRefresh = view.findViewById(R.id.music_smartrefresh);
    }

    private void getData(int userID){
        mySpacePresenter.getMusic(userID);
    }

    @Override
    public void getMusicListSuccess(List<Music> musics) {
        musicList.addAll(musics);
        musicAdapter = new MusicAdapter(musicList,getActivity());
        music_rlv_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        music_rlv_view.setAdapter(musicAdapter);
    }

    @Override
    public void getMusicListError() {
        Toast.makeText(getActivity(),"暂无数据",Toast.LENGTH_SHORT).show();
    }

    /**
     * 点赞后回调改变fragment状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMainEventThread(MsgEvent event) {
        msgEvent = event;
        if (msgEvent != null) {
            switch (msgEvent.getType()) {
                case "like":
                    if (msgEvent.isValue()) {
                        for (Music music : musicList) {
                            if (event.getId() == music.getId()) {
                                music.setLike(true);
                                music.setLikes(music.getLikes() + 1);
                                break;
                            }
                        }
                        musicAdapter.notifyDataSetChanged();
                    } else {
                        for (Music music : musicList) {
                            if (event.getId() == music.getId()) {
                                music.setLike(false);
                                music.setLikes(music.getLikes() - 1);
                                break;
                            }
                        }
                        musicAdapter.notifyDataSetChanged();

                    }
                    msgEvent = null;
                    break;
                case "collect":
                    if (msgEvent.isValue()) {
                        for (Music music : musicList) {
                            if (event.getId() == music.getId()) {
                                music.setCollect(true);
                                music.setCollectnum(music.getCollectnum() + 1);
                                break;
                            }
                        }
                        musicAdapter.notifyDataSetChanged();
                    } else {
                        for (Music music : musicList) {
                            if (event.getId() == music.getId()) {
                                music.setCollect(false);
                                music.setCollectnum(music.getCollectnum() - 1);
                                break;
                            }
                        }
                        musicAdapter.notifyDataSetChanged();

                    }
                    msgEvent = null;
                    break;
                case "comment":
                    if (event.isValue()) {
                        for (Music music : musicList) {
                            if (event.getId() == music.getId()) {
                                music.setCheatnum(music.getCheatnum() + 1);
                                break;
                            }
                        }
                    } else {
                        for (Music music : musicList) {
                            if (event.getId() == music.getId()) {
                                music.setCheatnum(music.getCheatnum() - 1);
                                break;
                            }
                        }
                    }
                    musicAdapter.notifyDataSetChanged();
                    msgEvent = null;
                    break;
                case "follow":
                    if (event.isValue()) {
                        for (Music music : musicList) {
                            if (music.getUser().getId() == event.getId()) {
                                music.getUser().setFollow(true);
                                break;
                            }
                        }
                    } else {
                        for (Music music : musicList) {
                            if (music.getUser().getId() == event.getId()) {
                                music.getUser().setFollow(false);
                                break;
                            }
                        }
                    }
                    musicAdapter.notifyDataSetChanged();
                    msgEvent = null;
                    break;
            }
        }
    }
}
