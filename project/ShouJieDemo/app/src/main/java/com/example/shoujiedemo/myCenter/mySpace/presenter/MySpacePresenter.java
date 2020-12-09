package com.example.shoujiedemo.myCenter.mySpace.presenter;

import android.util.Log;

import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.myCenter.mySpace.model.ArticleModel;
import com.example.shoujiedemo.myCenter.mySpace.model.ArticleModelImpl;
import com.example.shoujiedemo.myCenter.mySpace.model.IdeaModel;
import com.example.shoujiedemo.myCenter.mySpace.model.IdeaModelImpl;
import com.example.shoujiedemo.myCenter.mySpace.model.PoemModel;
import com.example.shoujiedemo.myCenter.mySpace.model.PoemModelImpl;
import com.example.shoujiedemo.myCenter.mySpace.service.ArticleAdapter;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleAdapterView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleFragmentView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.IdeaView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.MySpaceView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.PoemView;
import com.example.shoujiedemo.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class MySpacePresenter implements MySpacePresenterListener,ArticleInterface,PoemInterface,IdeaInterface,MySpaceInterface{
    private ArticleModel articleModel;
    private ArticleView articleView;
    private ArticleAdapterView articleAdapterView;

    private PoemModel poemModel;
    private PoemView poemView;

    private IdeaModel ideaModel;
    private IdeaView ideaView;

    private MySpaceView mySpaceView;

    private ArticleFragmentView articleFragmentView;
    /**
     * 构造方法
     */
    public MySpacePresenter(ArticleView articleView) {
        this.articleModel = new ArticleModelImpl();
        this.articleView = articleView;
    }

    public MySpacePresenter(PoemView poemView) {
        this.poemModel = new PoemModelImpl();
        this.poemView = poemView;
    }

    public MySpacePresenter(IdeaView ideaView) {
        this.ideaModel = new IdeaModelImpl();
        this.ideaView = ideaView;
    }

    public MySpacePresenter(MySpaceView mySpaceView) {
        this.articleModel = new ArticleModelImpl();
        this.mySpaceView = mySpaceView;
    }

    public MySpacePresenter(ArticleAdapterView articleAdapterView) {
        this.articleAdapterView = articleAdapterView;
        this.articleModel = new ArticleModelImpl();
    }

    public MySpacePresenter(ArticleFragmentView articleFragmentView) {
        this.articleFragmentView = articleFragmentView;
        this.articleModel = new ArticleModelImpl();
    }

    /**
     * Article逻辑实现方法
     */

    public void delete(int groupID){
        articleModel.delete(groupID,this);
    }

    //获取Groups数据
    public void getGroups(int userID){
        Log.e("获取文集","第二步");
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
        try {
            JSONObject jsonObject =new JSONObject(jsons);
            Object json = null;
            json = new JSONTokener(jsonObject.toString()).nextValue();
            if(json instanceof JSONObject) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("wenjidate");
                sets = jsonObject1.toString();
                Set set = gson.fromJson(sets, Set.class);
                List<Set> setList = new ArrayList<>();
                setList.add(set);
                UserUtil.SET_JSON = sets;
                articleFragmentView.getSets(setList);
            }else if(json instanceof JSONArray){
                JSONArray jsonArray = jsonObject.getJSONArray("wenjidate");
                sets = jsonArray.toString();
                UserUtil.SET_JSON = sets;
                List<Set> setList = gson.fromJson(sets, new TypeToken<List<Set>>() {}.getType());
                articleFragmentView.getSets(setList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*Object json = null;
        try {
            json = new JSONTokener(jsons).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(json instanceof JSONObject) {
            Set set = gson.fromJson(jsons, Set.class);
            List<Set> setList = new ArrayList<>();
            setList.add(set);
            Log.e("获取文集","解析完成 "+
                    set.toString());
            articleFragmentView.getSets(setList);
        }else if(json instanceof JSONArray){
            List<Set> setList = gson.fromJson(jsons, new TypeToken<List<Set>>() {}.getType());
            articleFragmentView.getSets(setList);
        }*/
    }

    @Override
    public void getGroupFailed() {
        articleAdapterView.getGroupFailed();
    }

    /**
     * MySpace逻辑实现方法
     */
    public void addGroups(int userID, String groupName){
        articleModel.getGroup(userID,groupName,this);
    }

    /**
     * MySpace结果回调方法
     */

    @Override
    public void addGroupSuccessful(String jsons) {
        Gson gson = new Gson();
        Log.e("返回文集",jsons);
        Set set = gson.fromJson(jsons,Set.class);
        mySpaceView.addGroupSuccessful(set);
    }

    @Override
    public void addGroupFailed() {
        mySpaceView.addGroupFailed();
    }
}
