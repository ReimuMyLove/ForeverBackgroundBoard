package com.example.shoujiedemo.upload.view;

import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.entity.Set;

import java.util.List;

public interface LoadView {
    void skipSuccess();

    void skipFailure();

    void skipSuccess(Music music);//音乐上传成功

    void showSet(List<Set> sets);//显示文集
}
