package com.example.shoujiedemo.upload.presenter;

import android.net.Uri;

import com.example.shoujiedemo.entity.Content;

public interface UploadPresenter {
    void UploadData(Content content, int isoriginal);
    void UploadData(Content content, int isoriginal, Uri uri);
}
