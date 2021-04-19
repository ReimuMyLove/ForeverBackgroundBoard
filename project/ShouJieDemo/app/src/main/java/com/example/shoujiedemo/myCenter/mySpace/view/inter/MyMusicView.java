package com.example.shoujiedemo.myCenter.mySpace.view.inter;

import com.example.shoujiedemo.entity.Music;

import java.util.List;

public interface MyMusicView {

    void getMusicListSuccess(List<Music> musicList);

    void getMusicListError();
}
