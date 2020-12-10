package com.example.shoujiedemo.upload.presenter;

import android.net.Uri;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.upload.model.UploadModel;
import com.example.shoujiedemo.upload.model.UploadModelImpl;
import com.example.shoujiedemo.upload.view.LoadView;

public class UploadPresenterImpl implements UploadPresenterListener,UploadPresenter {
    private UploadModel model;
    private LoadView view;
    public UploadPresenterImpl(LoadView view){
        this.view=view;
        model=new UploadModelImpl();
    }
    @Override
    public void UploadData(Content content, int isoriginal) {
        model.Gointent(this,content,isoriginal);
    }

    @Override
    public void UploadData(Content content, int isoriginal, Uri uri) {
        model.Gointent(this,content,isoriginal,uri);
    }

    @Override
    public void loadSuccess() {
        view.skipSuccess();
    }

    @Override
    public void loadError() {
        view.skipFailure();
    }
}
