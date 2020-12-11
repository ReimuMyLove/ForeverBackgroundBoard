package com.example.shoujiedemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CommentAdapter extends RecyclerView.Adapter{

    private List<Comment> commentList = new ArrayList<>();
    private Context context;
    private boolean isMenu = false;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.userName.setText(commentList.get(position).getUser().getName());
        viewHolder.date.setText(commentList.get(position).getTime());
        viewHolder.text.setText(commentList.get(position).getText());

        if(commentList.get(position).getUser1id() == UserUtil.USER_ID)
            viewHolder.menuBtn.setVisibility(View.VISIBLE);
        else
            viewHolder.menuBtn.setVisibility(View.INVISIBLE);

        viewHolder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMenu) {
                    viewHolder.deleteMenu.setVisibility(View.INVISIBLE);
                    isMenu = false;
                }else{
                    viewHolder.deleteMenu.setVisibility(View.VISIBLE);
                    isMenu = true;
                }
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.menuBtn.setVisibility(View.INVISIBLE);
                viewHolder.deleteMenu.setVisibility(View.INVISIBLE);
                EventBus.getDefault().postSticky(commentList.get(position));
            }
        });

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.iv_default)
                .fallback(R.drawable.ouran_default)
                .centerCrop();
        if(commentList.get(position).getUser().getPicname() !=null) {
            Glide.with(context)
                    .load(ConfigUtil.BASE_HEAD_URL + commentList.get(position).getUser().getPicname())
                    .apply(requestOptions)
                    .into(viewHolder.userImg);
        }

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
        TextView delete;
        View deleteMenu;
        Button menuBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.item_comment_user_img);
            userName = itemView.findViewById(R.id.item_comment_tv_user_name);
            date = itemView.findViewById(R.id.item_comment_date);
            text = itemView.findViewById(R.id.item_comment_text);
            delete = itemView.findViewById(R.id.comment_tv_delete);
            deleteMenu = itemView.findViewById(R.id.comment_pull_menu);
            menuBtn = itemView.findViewById(R.id.btn_menu);

        }
    }
}
