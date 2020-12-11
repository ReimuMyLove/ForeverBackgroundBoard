package com.example.shoujiedemo.fround.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.PoemViewHodler;
import com.example.shoujiedemo.entity.Content;

import java.util.ArrayList;
import java.util.List;

public class PoemAdapter extends RecyclerView.Adapter {

    private List<Content> poems = new ArrayList<>();
    private Context context;

    public PoemAdapter(List<Content> poems, Context context) {
        this.poems = poems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHodler = null;
        View poemView = LayoutInflater.from(context).inflate(R.layout.item_follow_poem,parent,false);
        PoemViewHodler poemViewHodler = new PoemViewHodler(poemView,context,poems);
        viewHodler = poemViewHodler;
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.setIsRecyclable(false);
        PoemViewHodler poemViewHodler = (PoemViewHodler)holder;
        poemViewHodler.initData(position);
    }

    @Override
    public int getItemCount() {
        return poems.size();
    }


}
