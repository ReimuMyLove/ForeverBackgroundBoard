package com.example.shoujiedemo.upload.presenter;

import android.net.Uri;

import com.example.shoujiedemo.entity.Content;

public interface UploadPresenter {
    void UploadData(Content content, int isoriginal,int setId);
    void UploadData(Content content, int isoriginal, Uri uri,int setId);

    void uploadMusic(int userId,int songId);

    void uploadMusic(int userId,int songId,String text);

    void loadSet(int userId);//
}
