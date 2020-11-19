package com.example.shoujiedemo.home.recommen.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Content;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends BaseAdapter{

    private Context context;
    private List<Content> articleList = new ArrayList<>();
    private UnfoldableView unfoldableView;//折叠视图控件
    private View detailsLayout;//详细页面

    public ContentAdapter(Context context, List<Content> articleList, UnfoldableView unfoldableView, View detailsLayout){
        this.articleList = articleList;
        this.context = context;
        this.unfoldableView = unfoldableView;
        this.detailsLayout = detailsLayout;
    }
    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int i) {
        return articleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHodler viewHodler;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_content,viewGroup,false);
            viewHodler = new ViewHodler();
            viewHodler.img = view.findViewById(R.id.iv_item);
            viewHodler.title = view.findViewById(R.id.tv_item_title);
            view.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler)view.getTag();
        }
        viewHodler.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("imgView",view.toString());
                //折叠展开
                unfoldableView.unfold(view,detailsLayout);
            }
        });


        return view;
    }

    static class ViewHodler{
        TextView title;
        ImageView img;
    }

}
