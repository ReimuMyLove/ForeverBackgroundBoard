package com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.service.ArticleAdapter;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleFragmentView;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章页面
 */
public class MySpaceArticleFragment extends Fragment implements ArticleFragmentView {
    View contentView;
    RecyclerView
            mySpace_articleRec;       //文集列表
    int userID;
    private MySpacePresenter
        mySpacePresenter;
    private List<Set> setList = new ArrayList<>();  //
    private ArticleAdapter articleAdapter;
    public MySpaceArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_myspace_article, container, false);
        //这里用线性宫格显示 类似于grid view
        findView(contentView);
        userID = UserUtil.RECENT_USER_ID;
        mySpacePresenter = new MySpacePresenter(this);
        getData(userID);
        return contentView;
    }

    private void findView(View view){
        mySpace_articleRec = view.findViewById(R.id.mySpace_articleRec);
    }

    /**
     * 获取数据方法
     */
    private void getData(int userID){
        mySpacePresenter.getGroups(userID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpDateSet(Set set){
        setList.add(set);
        articleAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void deleteDateSet(Integer groupID){
       for(int i=0;i<setList.size();i++){
           if(setList.get(i).getId() == groupID) {
               setList.remove(i);
               break;
           }
           articleAdapter.notifyDataSetChanged();
       }
    }

    @Override
    public void getGroupFailed() {
        Toast.makeText(this.getContext(), "偶然：获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSets(List<Set> setLists) {
        setList = setLists;
        GridLayoutManager gridManager = new GridLayoutManager(this.getActivity(), 2);
        mySpace_articleRec.setLayoutManager(gridManager);
        articleAdapter = new ArticleAdapter(this.getContext(),setList);
        mySpace_articleRec.setAdapter(articleAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
