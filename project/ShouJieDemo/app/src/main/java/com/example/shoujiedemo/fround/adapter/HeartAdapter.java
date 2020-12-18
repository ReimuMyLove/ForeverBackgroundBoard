package com.example.shoujiedemo.fround.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.HeartViewHodler;
import com.example.shoujiedemo.entity.Content;

import java.util.ArrayList;
import java.util.List;

public class HeartAdapter extends RecyclerView.Adapter{

    private List<Content> hearts = new ArrayList<>();
    private Context context;

    public HeartAdapter(List<Content> hearts, Context context) {
        this.hearts = hearts;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHodler = null;
        View headView = LayoutInflater.from(context).inflate(R.layout.item_follow_heart,parent,false);
        HeartViewHodler headViewHodler = new HeartViewHodler(headView,context,hearts,this);
        viewHodler = headViewHodler;
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.setIsRecyclable(false);
        com.example.shoujiedemo.adapter.HeartViewHodler headViewHodler = (com.example.shoujiedemo.adapter.HeartViewHodler) holder;
        headViewHodler.initData(position);
    }

    @Override
    public int getItemCount() {
        return hearts.size();
    }

}
