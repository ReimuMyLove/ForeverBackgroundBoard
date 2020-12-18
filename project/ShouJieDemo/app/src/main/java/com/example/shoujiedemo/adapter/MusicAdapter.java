package com.example.shoujiedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter{

    private List<Music> musicList = new ArrayList<>();
    private Context context;

    public MusicAdapter(List<Music> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHodler = null;
        View musicView = LayoutInflater.from(context).inflate(R.layout.item_follow_music,parent,false);
        MusicViewHodler musicViewHodler = new MusicViewHodler(musicView,context,musicList,this);
        viewHodler = musicViewHodler;
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MusicViewHodler musicViewHodler = (MusicViewHodler) holder;
        musicViewHodler.initData(position);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
