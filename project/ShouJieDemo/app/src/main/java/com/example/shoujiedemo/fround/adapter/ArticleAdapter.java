package com.example.shoujiedemo.fround.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.activity.ArticleActivity;
import com.example.shoujiedemo.adapter.ArticleViewHodler;
import com.example.shoujiedemo.entity.Article;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.view.ContentView;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter{

    private List<Content> articles = new ArrayList<>();
    private Context context;

    public ArticleAdapter(List<Content> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHodler = null;
        View articleView = LayoutInflater.from(context).inflate(R.layout.item_follow_article,parent,false);
        ArticleViewHodler articleViewHodler = new ArticleViewHodler(articleView,context,articles);
        viewHodler = articleViewHodler;
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ArticleViewHodler articleViewHodler = (ArticleViewHodler)holder;
        articleViewHodler.initData(position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


}
