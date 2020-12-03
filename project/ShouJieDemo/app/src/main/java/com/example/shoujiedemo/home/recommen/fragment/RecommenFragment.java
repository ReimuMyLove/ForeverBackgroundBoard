package com.example.shoujiedemo.home.recommen.fragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.bean.ScaleInTransformer;
import com.example.shoujiedemo.entity.Article;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.follow.presenter.MyFollowAtriclePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenter;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.home.follow.presenter.MyFollowUserPresenter;
import com.example.shoujiedemo.home.follow.view.FollowView;
import com.example.shoujiedemo.home.recommen.adapter.ContentAdapter;
import com.example.shoujiedemo.home.recommen.presenter.OperatePresenter;
import com.example.shoujiedemo.util.BaseFragment;
import com.example.shoujiedemo.util.HandleBackInterface;
import com.google.android.material.tabs.TabLayout;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * 推荐页面,实现RecommenView接口
 */
public class RecommenFragment extends BaseFragment{

    private ViewPager2 viewPager2;
    private ViewPager2 homeView;
    private ContentAdapter contentAdapter;
    private View listTouchInterceptor;//折叠展开时背景点击控制视图
    private View detailsLayout;//详细视图页面
    private ImageView detailsImg;//详细页面封面
    private TextView detailsTitle;//详细页面标题
    private TextView detailsText;//详细页面内容
    private UnfoldableView unfoldableView;//折叠式图控件

    private List<Content> articleList = new ArrayList<>();//内容列表

    private OperatePresenter operatePresenter;
    private MyFollowAtriclePresenter atriclePresenter;
    private MyFollowUserPresenter userPresenter;

    private int currentPosition = 0;    //当前滑动位置
    private int oldPositon = 0;          //上一个滑动位置
    private int currentState = 0;        //记录当前手指按下状态
    private List<Integer> scrolledPixeledList = new ArrayList<>(); //记录手指滑动时的像素坐标记录

    public RecommenFragment(ViewPager2 homeView) {
        // Required empty public constructor
        this.homeView = homeView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initView(View view) {
        articleList = new ArrayList<>();
        Content a1 = new Content();

        //a1.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg02));
        Content a2 = new Content();
        //a2.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg03));
        Content a3 = new Content();
        //a3.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg04));
        Content a4 = new Content();
        //a4.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg05));
        articleList.add(a1);
        articleList.add(a2);
        articleList.add(a3);
        articleList.add(a4);

        viewPager2= view.findViewById(R.id.viewpager2_view);
        unfoldableView = view.findViewById(R.id.unfoldable_view);
        detailsLayout = view.findViewById(R.id.details_layout);
        detailsImg = detailsLayout.findViewById(R.id.details_image);
        detailsText = detailsLayout.findViewById(R.id.details_text);
        detailsTitle = detailsLayout.findViewById(R.id.details_title);
        listTouchInterceptor = view.findViewById(R.id.touch_interceptor_view);

        contentAdapter = new ContentAdapter(getContext(),articleList,unfoldableView,detailsLayout);
        viewPager2.setAdapter(contentAdapter);
        //设置滚动方向
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


        //实现一屏多页
        viewPager2.setOffscreenPageLimit(articleList.size());//设置预加载页
        View recyclerView = viewPager2.getChildAt(0);
        if(recyclerView != null && recyclerView instanceof RecyclerView){
            recyclerView.setPadding(100, 0, 100, 0);
            ((RecyclerView) recyclerView).setClipToPadding(false);
        }
        //设置Transformer
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new ScaleInTransformer());
        viewPager2.setPageTransformer(compositePageTransformer);

        detailsLayout.setVisibility(View.INVISIBLE);
        //页面滑动监听
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                if(currentState == 1){
                    scrolledPixeledList.add(positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentState = state;
                if(state ==0){
                    if(currentPosition == oldPositon){
                        Log.d("position", oldPositon + "");
                        if(currentPosition == 0){
                            if (scrolledPixeledList.size() > 1 && scrolledPixeledList.get(scrolledPixeledList.size() - 1) == 0) {
                                   //有可能出现滑到一半放弃的情况也是可以出现currentPosition == oldPositon=0，则先判断是否是往右滑时放弃
                                    return;
                               }
                            //若还有上一个bottom fragment页面则切换
                            /*if(homeView.getCurrentItem() > 0){

                            }*/
                            //若还有下一个fragment则切换
                        }else if(currentPosition == contentAdapter.getItemCount() - 1){
                            if(homeView.getCurrentItem() <  contentAdapter.getItemCount() - 1){
                                homeView.setCurrentItem(1,true);
                            }
                        }
                    }
                    oldPositon = currentPosition;
                    scrolledPixeledList.clear();//清空滑动记录
                }
            }
        });

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


    }

    /**
     * adapter点击事件回调
     * @param position item位置
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMainEventThread(Integer position){
        Log.d("position",position+ "");
        /*ImageView imageView = detailsLayout.findViewById(R.id.details_image);
        Content article = articleList.get(position);
        imageView.setImageBitmap(article.getBitmap());*/
    }

    /**
     * 自定义返回按钮监听
     * @return
     */
    @Override
    public boolean onBackPressed() {
        if(unfoldableView.isUnfolded() || unfoldableView.isUnfolding()){
            unfoldableView.foldBack();
            return true;
        }
        return super.onBackPressed();
    }

}
