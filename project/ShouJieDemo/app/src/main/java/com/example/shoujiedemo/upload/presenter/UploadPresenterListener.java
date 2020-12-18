package com.example.shoujiedemo.upload.presenter;

import com.example.shoujiedemo.entity.Music;

public interface UploadPresenterListener {
    void loadSuccess();

    void loadError();

    void loadSuccess(String jsons);

    void loadSetSuccess(String jsons);

    void loadSetError();
}
