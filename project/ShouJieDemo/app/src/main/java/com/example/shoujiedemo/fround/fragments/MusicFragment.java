package com.example.shoujiedemo.fround.fragments;


import android.content.Intent;
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

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MusicAdapter;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.bean.SearchEvent;
import com.example.shoujiedemo.bean.UploadBean;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.fround.presenter.HotLoadDataPresenter;
import com.example.shoujiedemo.fround.presenter.MusicLoadPresenterImpl;
import com.example.shoujiedemo.fround.service.NetPlayMusicService;
import com.example.shoujiedemo.fround.view.MusicView;
import com.example.shoujiedemo.util.MusicPlayUtil;
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
 * 音乐页面
 */
public class MusicFragment extends Fragment implements MusicView {

    private List<Music> musicList = new ArrayList<>();
    private List<Music> searchList = new ArrayList<>();
    private MusicAdapter searchAdapter;
    private MusicAdapter musicAdapter;
    private RecyclerView recyclerView;
    private HotLoadDataPresenter presenter;
    private MsgEvent msgEvent;
    private int pageNum;
    private SmartRefreshLayout smartRefreshLayout;
    private int refreshTag = 0;
    private boolean isRefresh = false;
    private String flag = "";
    private int search = 0;
    private int searchTag = 0;
    private boolean isSearchRefresh = false;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = 1;
        EventBus.getDefault().register(this);
        presenter = new MusicLoadPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        recyclerView = view.findViewById(R.id.music_rlv_view);
        smartRefreshLayout = view.findViewById(R.id.music_smartrefresh);
        smartRefreshLayout.setHeaderHeight(140);
        smartRefreshLayout.setFooterHeight(150);
        smartRefreshLayout.setEnableLoadMore(true);
        if(!isRefresh) {
            smartRefreshLayout.autoRefresh();
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(flag.length() != 0 && search == 1){
                    pageNum =1;
                    searchList.clear();
                    searchTag = 0;
                    presenter.searchData(flag,pageNum,UserUtil.USER_ID);
                }else {
                    pageNum = 1;
                    musicList.clear();
                    presenter.confirmInitContent(1, UserUtil.USER_ID);
                    refreshLayout.finishRefresh(600);
                }

            }

        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if(flag.length() != 0 && search == 1) {
                    presenter.confirmInitContent(pageNum, UserUtil.USER_ID);
                    refreshLayout.finishLoadMore(600);
                }else{
                    presenter.searchData(flag,pageNum,UserUtil.USER_ID);
                    refreshLayout.finishLoadMore(600);
                }
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
    public void showContentListData(List<Music> musics) {
        if(musicList.size() < 10 || musicList == null) {
            if(refreshTag == 0) {
                this.musicList = musics;
                musicAdapter = new MusicAdapter(musicList, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(musicAdapter);
                refreshTag = 1;
            }else{
                List<Music> newContents = new ArrayList<>();
                for(Music music :musicList){
                    for(Music music1:musics){
                        if(music.getId() == music1.getId())
                            newContents.add(music1);
                    }
                }
                musicList.addAll(musics);
                musicList.removeAll(newContents);
                musicAdapter.notifyDataSetChanged();
            }
            if(musicList.size() == 10)
                pageNum++;
        }else{
            List<Music> newList = new ArrayList<>();
            for(Music music :musicList){
                for(Music music1:musics){
                    if(music.getId() == music1.getId())
                        newList.add(music1);
                }
            }
            musicList.addAll(musics);
            musicList.removeAll(newList);
            if(musicList.size() % 10 == 0)
                ++pageNum;
            musicAdapter.notifyDataSetChanged();
        }
        isRefresh = true;

    }


    @Override
    public void showSearchList(List<Music> musics) {
        if(searchList.size() < 10 || searchList == null) {
            if(searchTag == 0) {
                this.searchList = musics;
                searchAdapter = new MusicAdapter(searchList, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(searchAdapter);
                searchTag = 1;
            }else{
                List<Music> newHearts = new ArrayList<>();
                for(Music content:searchList){
                    for(Music content1:musics){
                        if(content.getId() == content1.getId())
                            newHearts.add(content1);
                    }
                }
                searchList.addAll(musics);
                searchList.removeAll(newHearts);
                searchAdapter.notifyDataSetChanged();
            }
            if(searchList.size() == 10)
                pageNum++;
        }else{
            List<Music> newList = new ArrayList<>();
            for(Music content :searchList){
                for(Music content1:musics){
                    if(content.getId() == content1.getId())
                        newList.add(content1);
                }
            }
            searchList.addAll(musics);
            searchList.removeAll(newList);

            if(searchList.size() % 10 == 0)
                pageNum++;

            searchAdapter.notifyDataSetChanged();
        }

        isSearchRefresh = true;
    }

    @Override
    public void noData() {

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
                        for(Music music:musicList){
                            if(event.getId() == music.getId()){
                                music.setLike(true);
                                music.setLikes(music.getLikes() + 1);
                                break;
                            }
                        }
                        musicAdapter.notifyDataSetChanged();
                    }else{
                        for(Music music:musicList){
                            if(event.getId() == music.getId()){
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
                    if(msgEvent.isValue()){
                        for(Music music:musicList){
                            if(event.getId() == music.getId()){
                                music.setCollect(true);
                                music.setCollectnum(music.getCollectnum() + 1);
                                break;
                            }
                        }
                        musicAdapter.notifyDataSetChanged();
                    }else{
                        for(Music music:musicList){
                            if(event.getId() == music.getId()){
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
                    if(event.isValue()) {
                        for (Music music : musicList) {
                            if (event.getId() == music.getId()) {
                                music.setCheatnum(music.getCheatnum() + 1);
                                break;
                            }
                        }
                    }else{
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
                    if(event.isValue()){
                        for(Music music:musicList){
                            if(music.getUser().getId() == event.getId()){
                                music.getUser().setFollow(true);
                                break;
                            }
                        }
                    }else{
                        for(Music music:musicList){
                            if(music.getUser().getId() == event.getId()){
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

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void SearchMain(SearchEvent event){
        flag = event.getTag();
        Log.e("tag",flag);
        if(event.getPosition() == 3) {
            pageNum = 1;
            search = 1;
            presenter.searchData(flag, pageNum, UserUtil.USER_ID);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMainPlay(Music music){
        if(music.getTag() == 1){
            if(MusicPlayUtil.IS_STOP){
                for (Music music1 : musicList) {
                    if (music.getId() == music1.getId()) {
                        music1.setPause(true);
                        music1.setStop(true);
                        music1.setStart(0);
                        break;
                    }
                }
                musicAdapter.notifyDataSetChanged();
                /*Intent intent = new Intent(getActivity(), NetPlayMusicService.class);
                getActivity().stopService(intent);*/
            }else {
                for (Music music1 : musicList) {
                    if (MusicPlayUtil.MUSIC_IS_PLAY == music1.getId()) {
                        music1.setPause(music.isPause());
                        music1.setStop(music.isStop());
                        music1.setStart(1);
                    }else{
                        music1.setPause(true);
                        music1.setStop(true);
                        music1.setStart(0);
                    }
                }
            }
            musicAdapter.notifyDataSetChanged();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void UploadRefresh(UploadBean bean){
        if(bean.getTypeId() == 1){
           musicList.clear();
            pageNum = 1;
            presenter.confirmInitContent(pageNum,UserUtil.USER_ID);
        }
    }
}
