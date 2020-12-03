package com.example.shoujiedemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CommentAdapter extends RecyclerView.Adapter{

    private List<Comment> commentList = new ArrayList<>();
    private Context context;

    public CommentAdapter(List<Comment> commentList,Context context){
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false);
        ViewHolder viewHold = new ViewHolder(view);

        return viewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.userName.setText(commentList.get(position).getUser().getName());
        viewHolder.date.setText(commentList.get(position).getTime());
        viewHolder.text.setText(commentList.get(position).getText());
        /*Glide.with(context)
                .load(commentList.get(position).getUser().getPicname())
                .into(viewHolder.userImg);
        viewHolder.userImg.setScaleType(ImageView.ScaleType.FIT_XY);*/
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView userImg;
        TextView userName;
        TextView date;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.item_comment_user_img);
            userName = itemView.findViewById(R.id.item_comment_tv_user_name);
            date = itemView.findViewById(R.id.item_comment_date);
            text = itemView.findViewById(R.id.item_comment_text);
        }
    }
}
