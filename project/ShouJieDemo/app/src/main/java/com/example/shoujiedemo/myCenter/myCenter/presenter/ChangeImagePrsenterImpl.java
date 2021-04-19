package com.example.shoujiedemo.myCenter.myCenter.presenter;

import android.net.Uri;

import com.example.shoujiedemo.myCenter.myCenter.model.ChangeImageModel;
import com.example.shoujiedemo.myCenter.myCenter.model.ChangeImageModelImpl;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.ChangeImageView;

public class ChangeImagePrsenterImpl implements ChangeImagePresenter ,ChangeImagePresenterListener {
    private ChangeImageModel model;
    private ChangeImageView view;
    public ChangeImagePrsenterImpl(ChangeImageView view){
        model=new ChangeImageModelImpl();
        this.view=view;
    }
    @Override
    public void loadSuccess() {
        view.loadSuccess();
    }

    @Override
    public void loadError() {
        view.loadError();
    }

    @Override
    public void UploadData(Uri uri, int userid) {
        model.changeImage(this,userid,uri);
    }

    @Override
    public void UploadWenji(Uri uri, int userid, String name) {
        model.uploadWenji(this,userid,uri,name);
    }

    @Override
    public void changeBackground(Uri uri, int userid) {
        model.changeBackGround(this,userid,uri);
    }
}
