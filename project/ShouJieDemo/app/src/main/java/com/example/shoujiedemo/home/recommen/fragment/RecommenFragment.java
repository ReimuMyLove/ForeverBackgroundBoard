package com.example.shoujiedemo.home.recommen.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Article;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.recommen.adapter.ContentAdapter;
import com.example.shoujiedemo.home.recommen.presenter.OperatePresenter;
import com.example.shoujiedemo.home.recommen.view.RecommenView;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐页面,实现RecommenView接口
 */
public class RecommenFragment extends Fragment implements RecommenView {

    private ListView listView;
    private View listTouchInterceptor;
    private View detailsLayout;//详细视图页面
    private UnfoldableView unfoldableView;//折叠式图控件
    private List<Content> articleList = new ArrayList<>();//内容列表
    private OperatePresenter operatePresenter;

    public RecommenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommen,container,false);
        //初始化视图
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initView(View view) {
        List<Content> articleList = new ArrayList<>();
        articleList.add(new Article());
        articleList.add(new Article());
        articleList.add(new Article());
        articleList.add(new Article());
        listView = view.findViewById(R.id.list_view);
        unfoldableView = view.findViewById(R.id.unfoldable_view);
        detailsLayout = view.findViewById(R.id.details_layout);
        listView.setAdapter(new ContentAdapter(getContext(), articleList,unfoldableView,detailsLayout));
        listTouchInterceptor = view.findViewById(R.id.touch_interceptor_view);

        detailsLayout.setVisibility(View.INVISIBLE);

        //折叠事件监听
        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
            }
        });
        ImageView iv = view.findViewById(R.id.details_image);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //折叠返回
                unfoldableView.foldBack();
            }
        });
    }

    /**
     * 点赞按钮变色
     */
    @Override
    public void changeHeadColor() {

    }

    /**
     * 收藏按钮变色
     */
    @Override
    public void changCollectionColor() {

    }
}
