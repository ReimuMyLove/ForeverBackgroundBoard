package com.example.shoujiedemo.myCenter.mySpace.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.CollectBean;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.mySpace.model.ArticleModel;
import com.example.shoujiedemo.myCenter.mySpace.model.ArticleModelImpl;
import com.example.shoujiedemo.myCenter.mySpace.model.MyArticleModel;
import com.example.shoujiedemo.myCenter.mySpace.model.MyArticleModelImpl;
import com.example.shoujiedemo.myCenter.mySpace.model.MyMusicModel;
import com.example.shoujiedemo.myCenter.mySpace.model.MyMusicModelImpl;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleAdapterView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleFragmentView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MyArticleView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MyMusicView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MySpaceView;
import com.example.shoujiedemo.util.MusicPlayUtil;
import com.example.shoujiedemo.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MySpacePresenter implements MySpacePresenterListener{
    private ArticleModel articleModel;
    private ArticleView articleView;
    private ArticleAdapterView articleAdapterView;

    private MySpaceView mySpaceView;

    private ArticleFragmentView articleFragmentView;

    private MyArticleView myArticleView;
    private MyArticleModel myArticleModel;

    private MyMusicView myMusicView;
    private MyMusicModel myMusicModel;
    /**
     * 构造方法
     */
    public MySpacePresenter(ArticleView articleView) {
        this.articleModel = new ArticleModelImpl();
        this.myMusicModel = new MyMusicModelImpl();
        this.articleView = articleView;
    }

    public MySpacePresenter(MySpaceView mySpaceView) {
        this.articleModel = new ArticleModelImpl();
        this.myMusicModel = new MyMusicModelImpl();
        this.mySpaceView = mySpaceView;
    }

    public MySpacePresenter(ArticleFragmentView articleFragmentView) {
        this.articleFragmentView = articleFragmentView;
        this.myMusicModel = new MyMusicModelImpl();
        this.articleModel = new ArticleModelImpl();
    }

    public MySpacePresenter(MyArticleView myArticleView){
        this.myArticleView = myArticleView;
        this.myMusicModel = new MyMusicModelImpl();
        this.myArticleModel = new MyArticleModelImpl();
    }

    public MySpacePresenter(MyMusicView myMusicView){
        this.myMusicView = myMusicView;
        this.myMusicModel = new MyMusicModelImpl();
        this.myArticleModel = new MyArticleModelImpl();
    }

    /**
     * Article逻辑实现方法
     */

    public void delete(int groupID){
        articleModel.delete(groupID,this);
    }

    //获取Groups数据
    public void getGroups(int userID){
        articleModel.getArticles(userID,this);
    }

    /**
     *  Article结果回调方法
     */
    @Override
    public void deleteSuccessful() {
        articleAdapterView.deleteSuccessful();
    }

    @Override
    public void deleteFailed() {
        articleView.deleteFailed();
    }

    //设置Groups数据
    @Override
    public void setGroup(String jsons) {
        Gson gson = new Gson();
        String sets;
        sets = jsons;
        List<Set> setList = gson.fromJson(sets, new TypeToken<List<Set>>() {}.getType());
        articleFragmentView.getSets(setList);
    }

    @Override
    public void getGroupFailed() {
        articleAdapterView.getGroupFailed();
    }

    /**
     * 添加关注方法
     * @param userID 用户ID
     * @param followID  关注人ID
     */
    public void addFollow(int userID, int followID) {
        articleModel.addFollow(userID,followID,this);
    }

    /**
     * 获取空间主人信息
     * @param ownerID 空间主人UID
     */
    public void getOwnerInfo(int ownerID) {
        articleModel.getOwnerInfo(ownerID,this);
    }


    /**
     * MySpace结果回调方法
     */

    @Override
    public void addGroupSuccessful(String jsons) {
        Gson gson = new Gson();
        Set set = gson.fromJson(jsons,Set.class);
        mySpaceView.addGroupSuccessful(set);
    }

    @Override
    public void addGroupFailed() {
        mySpaceView.addGroupFailed();
    }

    /**
     * 添加关注回调方法
     */

    @Override
    public void addFollowFailed() {
        mySpaceView.addFollowFailed();
    }

    @Override
    public void addFollowSuccessful() {
        mySpaceView.addFollowSuccessful();
    }

    /**
     * 获取空间主人信息回调方法
     */

    @Override
    public void getOwnerInfoFailed() {
        mySpaceView.getOwnerInfoFailed();
    }

    @Override
    public void getOwnerInfoSuccessful(String jsons) {
        Gson gson = new Gson();
        User userInfo = gson.fromJson(jsons,User.class);
        mySpaceView.getOwnerInfoSuccessful(userInfo);
    }


    /**
     * 获取文集细节方法
     * @param groupID   文集ID
     */
    public void getGroupDetail(int groupID,int userId) {
        myArticleModel.getGroupDetail(groupID,userId,this);
    }

    /**
     * 获取文集细节回调方法
     */
    @Override
    public void getGroupDetailFailed() {
        myArticleView.getGroupDetailFailed();
    }

    @Override
    public void getGroupDetailSuccessful(String jsons) {
        List<Content> contents;
        List<User> users;
        List<LikeBean> likeBeans;
        List<User> follows;
        Gson gson = new Gson();
        String user = null;
        String content = null;
        String like = null;
        String follow = null;
        try {
            JSONObject jsonArray = new JSONObject(jsons);
            JSONArray userArray = jsonArray.getJSONArray("userdate");
            user = userArray.toString();
            JSONArray contentArray = jsonArray.getJSONArray("tuwendate");
            content = contentArray.toString();
            JSONArray likeArray = jsonArray.getJSONArray("likedate");
            like = likeArray.toString();
            JSONArray followArry = jsonArray.getJSONArray("followdate");
            follow = followArry.toString();

            Log.i("content",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        contents = gson.fromJson(content, new TypeToken<List<Content>>() {}.getType());
        users = gson.fromJson(user, new TypeToken<List<User>>() {}.getType());
        likeBeans = gson.fromJson(like,new TypeToken<List<LikeBean>>() {}.getType() );
        follows = gson.fromJson(follow,new TypeToken<List<User>>() {}.getType() );

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

        for(Content content1:contents){
            for(User user1 :users){
                if(content1.getUserid() == user1.getId()){
                    content1.setUser(user1);
                }
            }
        }

        if(likeBeans != null) {
            for (Content content1 : contents) {
                for (LikeBean likeBean : likeBeans) {
                    if (content1.getId() == likeBean.getTuwenid()) {
                        content1.setLike(true);
                    }
                }
            }

        }

        for(Content content1:contents){
            String time = content1.getTime().substring(5,16);
            content1.setTime(time);
        }
        myArticleView.getGroupDetailSuccessful(contents);
    }

    @Override
    public void getMusicFailed() {

    }

    @Override
    public void getMusicSuccessful(String jsons) {
        List<Music> musicList = new ArrayList<>();
        List<LikeBean> likeBeans = new ArrayList<>();
        Gson gson = new Gson();
        String music = null;
        String like = null;
        try {
            JSONObject jsonArray = new JSONObject(jsons);
            JSONArray contentArray = jsonArray.getJSONArray("musicdate");
            music = contentArray.toString();
            JSONArray likeArray = jsonArray.getJSONArray("likedate");
            like = likeArray.toString();
            Log.e("lieke",like);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        musicList = gson.fromJson(music, new TypeToken<List<Music>>() {}.getType());
        likeBeans = gson.fromJson(like,new TypeToken<List<LikeBean>>() {}.getType() );


        if(likeBeans != null) {
            for (Music content1 : musicList) {
                for (LikeBean likeBean : likeBeans) {
                    if (content1.getId() == likeBean.getMusicid()) {
                        content1.setLike(true);
                    }
                }
            }
        }
        User user= new User();
        user.setFollow(false);
        user.setPicname(UserUtil.USER_IMG);
        user.setId(UserUtil.USER_ID);
        user.setName(UserUtil.USER_NAME);
        user.setAge(UserUtil.USER_AGE);
        user.setFennum(UserUtil.USER_FANS);
        user.setFollownum(UserUtil.USER_FOLLOW);
        user.setSex(UserUtil.USER_SEX);
        user.setSign(UserUtil.USER_SIGN);
        user.setBackgroundpic1(UserUtil.USER_CENTER_BACKGROUND);
        user.setBackgroundpic2(UserUtil.USER_SPACE_BACKGROUND);

        for(Music content1 : musicList)
            content1.setUser(user);


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

        myMusicView.getMusicListSuccess(musicList);
    }

    public void getMusic(int userID) {
        myMusicModel.getMusic(userID,this);
    }


}
