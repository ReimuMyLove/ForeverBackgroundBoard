package com.example.shoujiedemo.home.recommen.presenter;

import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.recommen.model.OperateModel;
import com.example.shoujiedemo.home.recommen.model.OperateModelImpl;
import com.example.shoujiedemo.home.recommen.view.RecommenView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class OperatePresenterImpl implements OperatePresenter, OpreatePresenterListener {

    private RecommenView recommenView;
    private OperateModel operateModel;

    public OperatePresenterImpl(RecommenView recommenView) {
        this.recommenView = recommenView;
        this.operateModel = new OperateModelImpl();
    }


    @Override
    public void loadContent() {

        operateModel.loadContent(this);
    }


    @Override
    public void loadContentSuccess(String jsons) {
        List<Content>contents = new ArrayList<>();
        Gson gson = new Gson();
        contents = gson.fromJson(jsons, new TypeToken<List<Content>>() {}.getType());
        recommenView.loadContent(contents);
    }

    @Override
    public void loadContentError() {

    }

}
