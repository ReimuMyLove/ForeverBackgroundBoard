package com.example.shoujiedemo.home.follow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.ArticleViewHodler;
import com.example.shoujiedemo.adapter.HeartViewHodler;
import com.example.shoujiedemo.adapter.MusicViewHodler;
import com.example.shoujiedemo.adapter.PoemViewHodler;
import com.example.shoujiedemo.entity.Content;


import java.util.ArrayList;
import java.util.List;

public class FollowContentAdapter extends RecyclerView.Adapter{

    private List<Content> contentList = new ArrayList<>();
    private Context context;

    //内容类型
    private static final int CONTENT_TYPE_ARTICLE = 0;//文章
    private static final int CONTENT_TYPE_MUSIC = 1;//音乐
    private static final int CONTENT_TYPE_HEAD = 2;//感悟
    private static final int CONTENT_TYPE_POEM = 3;//诗

    public FollowContentAdapter(Context context,List<Content> contents){
        this.contentList = contents;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHodler = null;
        //判断内容类型
        switch (viewType){
            case CONTENT_TYPE_ARTICLE://文章
                View articleView = LayoutInflater.from(context).inflate(R.layout.item_follow_article,parent,false);
                ArticleViewHodler articleViewHodler = new ArticleViewHodler(articleView,context,contentList,this);
                viewHodler = articleViewHodler;
                break;
            case CONTENT_TYPE_HEAD://感悟
                View headView = LayoutInflater.from(context).inflate(R.layout.item_follow_heart,parent,false);
                HeartViewHodler headViewHodler = new HeartViewHodler(headView,context,contentList,this);
                viewHodler = headViewHodler;
                break;
            case CONTENT_TYPE_POEM://诗
                View poemView = LayoutInflater.from(context).inflate(R.layout.item_follow_poem,parent,false);
                PoemViewHodler poemViewHodler = new PoemViewHodler(poemView,context,contentList,this);
                viewHodler = poemViewHodler;
                break;
        }
        return viewHodler;
    }

    @Override
    public int getItemViewType(int position) {
        return contentList.get(position).getTypeid();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        int type = getItemViewType(position);
        switch(type){
            case CONTENT_TYPE_ARTICLE://文章
               ArticleViewHodler articleViewHodler = (ArticleViewHodler)holder;
               articleViewHodler.initData(position);
                break;
            case CONTENT_TYPE_HEAD://感悟
                HeartViewHodler headViewHodler = (HeartViewHodler) holder;
                headViewHodler.initData(position);
                break;
            case CONTENT_TYPE_POEM://诗
                PoemViewHodler poemViewHodler = (PoemViewHodler)holder;
                poemViewHodler.initData(position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }


}
