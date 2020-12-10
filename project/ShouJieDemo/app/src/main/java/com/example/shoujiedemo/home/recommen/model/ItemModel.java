package com.example.shoujiedemo.home.recommen.model;

import com.example.shoujiedemo.home.recommen.presenter.ItemPresenterListener;
import com.example.shoujiedemo.home.recommen.presenter.OpreatePresenterListener;

public interface ItemModel {

    void loadContentDetails(ItemPresenterListener listener, int id);
}

