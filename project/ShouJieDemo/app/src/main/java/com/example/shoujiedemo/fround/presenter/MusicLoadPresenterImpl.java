package com.example.shoujiedemo.fround.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.model.FroundHotDataModel;
import com.example.shoujiedemo.fround.model.FroundLoadDataModel;
import com.example.shoujiedemo.fround.model.MusicLoadDataModelImpl;
import com.example.shoujiedemo.fround.view.MusicView;
import com.example.shoujiedemo.util.MusicPlayUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MusicLoadPresenterImpl implements HotLoadDataPresenter,HotLoadDataPresenterListener{

    private FroundHotDataModel model;
    private MusicView view;

    public MusicLoadPresenterImpl(MusicView view) {
        this.view = view;
        model = new MusicLoadDataModelImpl();
    }



    @Override
    public void onLoadContentDataSuccess(String jsons) {
        List<Music> musicList = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<LikeBean> likeBeans = new ArrayList<>();
        List<User> follows = new ArrayList<>();
        List<CollectBean> collectBeans = new ArrayList<>();
        Gson gson = new Gson();
        String user = null;
        String music = null;
        String like = null;
        String follow = null;
        String collect = null;
        try {
            JSONObject jsonArray = new JSONObject(jsons);
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            user = userArray.toString();
            JSONArray contentArray = jsonArray.getJSONArray("musicdate");
            music = contentArray.toString();
            JSONArray likeArray = jsonArray.getJSONArray("likedate");
            like = likeArray.toString();
            JSONArray followArry = jsonArray.getJSONArray("followdate");
            follow = followArry.toString();
            JSONArray collectArray = jsonArray.getJSONArray("collectdate");
            collect = collectArray.toString();

            Log.e("lieke",like);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        musicList = gson.fromJson(music, new TypeToken<List<Music>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        likeBeans = gson.fromJson(like,new TypeToken<List<LikeBean>>() {}.getType() );
        follows = gson.fromJson(follow,new TypeToken<List<User>>() {}.getType() );
        collectBeans = gson.fromJson(collect,new TypeToken<List<CollectBean>>() {}.getType() );

        if(follows != null) {
            for (User user1 : users) {
                user1.setFollow(true);
                for (User user2 : follows) {
                    if (user1.getId() == user2.getId()) {
                        user1.setFollow(false);
                    }
                }
            }
        }

        if(musicList != null) {
            for (Music content1 : musicList) {
                for (User user1 : users) {
                    if (content1.getUserid() == user1.getId()) {
                        content1.setUser(user1);
                    }
                }
            }
        }

        if(likeBeans != null) {
            for (Music content1 : musicList) {
                for (LikeBean likeBean : likeBeans) {
                    if (content1.getId() == likeBean.getMusicid()) {
                        content1.setLike(true);
                    }
                }
            }

        }

        if(collectBeans != null){
            for (Music content1 : musicList) {
                content1.setCollect(false);
                for (CollectBean collectBean : collectBeans) {
                    if (content1.getId() == collectBean.getMusic_id()) {
                        content1.setCollect(true);
                    }
                }
            }
        }

        for(Music content1:musicList){
            String time = content1.getTime().substring(5,16);
            content1.setTime(time);
        }

        for(Music music1:musicList){
            if(music1.getId() == MusicPlayUtil.MUSIC_IS_PLAY){
                music1.setStop(false);
                music1.setStop(false);
                music1.setStart(1);
            }
        }
        if(musicList != null){
            view.showContentListData(musicList);
        }
    }

    @Override
    public void onLoadContentDataError() {
        view.noData();
    }

    @Override
    public void onSearchDataSuccess(String jsons) {

    }

    @Override
    public void onSearchDataError() {
        view.noData();
    }

    @Override
    public void confirmInitContent(int pageNum, int userId) {
        model.loadContents(this,pageNum,userId);
    }

    @Override
    public void searchData(String flag, int page, int userId) {

    }
}
