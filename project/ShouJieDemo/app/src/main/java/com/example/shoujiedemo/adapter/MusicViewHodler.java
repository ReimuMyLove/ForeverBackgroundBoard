package com.example.shoujiedemo.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.home.follow.view.ContentView;
import com.example.shoujiedemo.home.follow.view.FollowView;

import java.util.List;

public class MusicViewHodler  extends RecyclerView.ViewHolder implements ContentView {


    private Context context;

    private boolean isLike = false;
    private boolean isCollect = false;
    private boolean isFollow = false;
    private boolean isPull = false;

    public MusicViewHodler(@NonNull View itemView,Context context) {
        super(itemView);
        this.context = context;
        initView();
    }

    private void initView() {

    }


    @Override
    public void setUnFolly() {

    }

    @Override
    public void setUnFollyError() {

    }

    @Override
    public void setFollow() {

    }

    @Override
    public void setFollowError() {

    }

    @Override
    public void changeLikeError() {

    }

    @Override
    public void changeCollectionError() {

    }

    @Override
    public void report() {

    }

    @Override
    public void share() {

    }

    @Override
    public void comment(Comment comment) {

    }

    @Override
    public void loadComment(List<Comment> commentList) {

    }

    @Override
    public void deleteComment() {

    }


    @Override
    public void showSet(List<Set> sets) {

    }

    @Override
    public void collect() {
        /*collected.setBackgroundResource(R.drawable.collectionselected);//收藏
        int collectionNums = Integer.parseInt(collectionNum.getText().toString()) + 1;
        contents.get(position).setCollectnum(collectionNums);
        collectionNum.setText(collectionNums + "");
        contents.get(position).setCollect(true);
        Toast.makeText(context,"收藏成功" + set1.getName(),Toast.LENGTH_SHORT).show();*/
    }
}
