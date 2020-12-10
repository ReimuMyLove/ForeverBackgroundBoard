package com.example.shoujiedemo.home.recommen.presenter;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.recommen.model.ItemModel;
import com.example.shoujiedemo.home.recommen.model.ItemModelImpl;
import com.example.shoujiedemo.home.recommen.view.RecommenItemView;
import com.google.gson.Gson;

public class ItemPresenterImpl implements ItemPresenter,ItemPresenterListener{

    private RecommenItemView view;
    private ItemModel model;

    public ItemPresenterImpl(RecommenItemView view) {
        this.view = view;
        model = new ItemModelImpl();
    }

    @Override
    public void loadContentDetailsSuccess(String jsons) {
        Gson gson = new Gson();
        Content content = gson.fromJson(jsons, Content.class);
        view.loadContentDetails(content);
    }

    @Override
    public void loadContentDetailsError() {

    }

    @Override
    public void loadContentDetails(int id) {
        model.loadContentDetails(this,id);
    }
}
