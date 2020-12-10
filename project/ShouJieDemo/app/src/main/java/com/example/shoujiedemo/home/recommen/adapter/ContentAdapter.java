package com.example.shoujiedemo.home.recommen.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.Article;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.recommen.presenter.ItemPresenter;
import com.example.shoujiedemo.home.recommen.presenter.ItemPresenterImpl;
import com.example.shoujiedemo.home.recommen.view.RecommenItemView;
import com.example.shoujiedemo.util.ConfigUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> implements RecommenItemView {

    private Context context;
    private List<Content> articleList = new ArrayList<>();
    private UnfoldableView unfoldableView;//折叠视图控件
    private View detailsLayout;//详细页面
    private ItemPresenter presenter;
    private int position;

    public ContentAdapter(Context context, List<Content> articleList, UnfoldableView unfoldableView,View detailsLayout){
        this.articleList = articleList;
        this.context = context;
        this.unfoldableView = unfoldableView;
        this.detailsLayout = detailsLayout;
        presenter = new ItemPresenterImpl(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommen_content,parent,false);
        unfoldableView.setGesturesEnabled(false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        this.position = position;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/heather script two.ttf");
        holder.itemDate.setTypeface(typeface);
        Content article =  articleList.get(position);
        holder.itemTitle.setText("《" + article.getTitle() + "》");
        holder.itemDate.setText(article.getTime());
        holder.itemCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送点击事件
                presenter.loadContentDetails(articleList.get(position).getId());
                unfoldableView.unfold(holder.itemCover,detailsLayout);
            }
        });

        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(context)
                .load(ConfigUtil.BASE_IMG_URL + articleList.get(position).getPic())
                .apply(requestOptions)
                .into(holder.itemCover);

    }


    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public void loadContentDetails(Content content) {
        EventBus.getDefault().postSticky(content);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
         ImageView itemCover;
         TextView itemTitle;
         TextView itemDate;
         View item;

        public ViewHolder(@NonNull final View itemView, final Context context) {
            super(itemView);
            itemCover = itemView.findViewById(R.id.iv_item);
            itemTitle = itemView.findViewById(R.id.tv_item_title);
            itemDate = itemView.findViewById(R.id.recommen_item_date);
            itemDate.setText("November 25, 2020");
            item = itemView.findViewById(R.id.item_view);

        }
    }

}
