package com.example.shoujiedemo.upload.presenter;

import android.net.Uri;
import android.util.Log;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.upload.model.UploadModel;
import com.example.shoujiedemo.upload.model.UploadModelImpl;
import com.example.shoujiedemo.upload.view.LoadView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class UploadPresenterImpl implements UploadPresenterListener,UploadPresenter {
    private UploadModel model;
    private LoadView view;
    public UploadPresenterImpl(LoadView view){
        this.view=view;
        model=new UploadModelImpl();
    }
    @Override
    public void UploadData(Content content, int isoriginal,int setId) {
        model.Gointent(this,content,isoriginal,setId);
    }

    @Override
    public void UploadData(Content content, int isoriginal, Uri uri,int setId) {
        model.Gointent(this,content,isoriginal,uri,setId);
    }

    @Override
    public void uploadMusic(int userId, int songId) {
        model.uploadMusic(this,userId,songId);
    }

    @Override
    public void uploadMusic(int userId, int songId, String text) {
        model.uploadMusic(this,userId,songId,text);
    }

    @Override
    public void loadSet(int userId) {
        model.loadSet(this,userId);
    }

    @Override
    public void loadSuccess() {
        view.skipSuccess();
    }

    @Override
    public void loadError() {
        view.skipFailure();
    }

    @Override
    public void loadSuccess(String jsons) {
        Gson gson = new Gson();
        Music music = gson.fromJson(jsons,Music.class);
        view.skipSuccess(music);
    }

    @Override
    public void loadSetSuccess(String jsons) {
        Gson gson = new Gson();
        Object json = null;
        try {
            json = new JSONTokener(jsons).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(json instanceof JSONObject) {
            Set set = gson.fromJson(jsons, Set.class);
            List<Set> setList = new ArrayList<>();
            setList.add(set);
            view.showSet(setList);
        }else if(json instanceof JSONArray){
            List<Set> setList = gson.fromJson(jsons, new TypeToken<List<Set>>() {}.getType());
            view.showSet(setList);
        }
    }

    @Override
    public void loadSetError() {

    }
}
