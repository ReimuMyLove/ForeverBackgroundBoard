package com.example.shoujiedemo.home.recommen.fragment;


import android.animation.ObjectAnimator;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.ScaleInTransformer;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Day;
import com.example.shoujiedemo.home.recommen.adapter.ContentAdapter;
import com.example.shoujiedemo.home.recommen.presenter.OperatePresenter;
import com.example.shoujiedemo.home.recommen.presenter.OperatePresenterImpl;
import com.example.shoujiedemo.home.recommen.presenter.TimePresenter;
import com.example.shoujiedemo.home.recommen.presenter.TimePresenterImpl;
import com.example.shoujiedemo.home.recommen.view.RecommenView;
import com.example.shoujiedemo.util.BaseFragment;
import com.example.shoujiedemo.util.ConfigUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 推荐页面,实现RecommenView接口
 */
public class RecommenFragment extends BaseFragment implements RecommenView {

    private ViewPager2 viewPager2;
    private ViewPager2 homeView;
    private ContentAdapter contentAdapter;
    private View listTouchInterceptor;//折叠展开时背景点击控制视图
    private View detailsLayout;//详细视图页面
    private ImageView detailsImg;//详细页面封面
    private TextView detailsTitle;//详细页面标题
    private TextView detailsText;//详细页面内容
    private UnfoldableView unfoldableView;//折叠式图控件
    private TextView detailsWriter;//详细页面作者
    private TextView selectDay;
    private TextView selectDate;
    private View currentTime;
    private int month;
    private int year;
    private int day;
    private TimePresenter presenter;
    private View  recyclerView;

    private List<Content> articleList = new ArrayList<>();//内容列表

    private OperatePresenter operatePresenter;


    private int currentPosition = 0;    //当前滑动位置
    private int oldPositon = 0;          //上一个滑动位置
    private int currentState = 0;        //记录当前手指按下状态
    private List<Integer> scrolledPixeledList = new ArrayList<>(); //记录手指滑动时的像素坐标记录

    public RecommenFragment() {

    }

    public RecommenFragment(ViewPager2 homeView) {
        // Required empty public constructor
        this.homeView = homeView;
        operatePresenter = new OperatePresenterImpl(this);
        presenter = new TimePresenterImpl(this);
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

        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        year = calendar.get(Calendar.YEAR);
        //获取当前月
        month = calendar.get(Calendar.MONTH) + 1;
        // 获取当前日
        day = calendar.get(Calendar.DATE);

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

        viewPager2= view.findViewById(R.id.viewpager2_view);
        unfoldableView = view.findViewById(R.id.unfoldable_view);
        detailsLayout = view.findViewById(R.id.details_layout);
        detailsImg = detailsLayout.findViewById(R.id.details_image);
        detailsText = detailsLayout.findViewById(R.id.details_text);
        detailsTitle = detailsLayout.findViewById(R.id.details_title);
        detailsWriter = detailsLayout.findViewById(R.id.details_writer);
        listTouchInterceptor = view.findViewById(R.id.touch_interceptor_view);
        selectDay = view.findViewById(R.id.recommen_selectDay);
        selectDate = view.findViewById(R.id.recommen_selectDate);
        currentTime = view.findViewById(R.id.recommen_currentTime);

        String monthInEngLish = null;
        switch(month) {
            case 1:
                monthInEngLish = "Jan";
                break;
            case 2:
                monthInEngLish = "Feb";
                break;
            case 3:
                monthInEngLish = "Mar";
                break;
            case 4:
                monthInEngLish = "Apr";
                break;
            case 5:
                monthInEngLish = "May";
                break;
            case 6:
                monthInEngLish = "Jun";
                break;
            case 7:
                monthInEngLish = "Jul";
                break;
            case 8:
                monthInEngLish = " Aug";
                break;
            case 9:
                monthInEngLish = "Sept";
                break;
            case 10:
                monthInEngLish = "Oct";
                break;
            case 11:
                monthInEngLish = "Nov";
                break;
            case 12:
                monthInEngLish = "Dec";
                break;
        }
                selectDay.setText(day + "");
                selectDate.setText(monthInEngLish + ". " + year);


        operatePresenter.loadContent();
        //设置滚动方向
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);




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
                                homeView.setCurrentItem(1,true);
                               }
                            //若还有上一个bottom fragment页面则切换
                            if(homeView.getCurrentItem() > 0){

                            }
                            //若还有下一个fragment则切换
                            /*if(homeView.getCurrentItem() <  contentAdapter.getItemCount() - 1){
                                homeView.setCurrentItem(1,true);
                            }*/
                                if(homeView.getCurrentItem() ==  contentAdapter.getItemCount() - 1){
                                    homeView.setCurrentItem(1,true);
                                }
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
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(currentTime, "translationX",
                        0, -280);
                animator1.setDuration(400);
                animator1.start();;
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);

            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(currentTime, "translationX",
                        -280, 0);
                animator1.setDuration(400);
                animator1.start();
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);

            }
        });


    }


    /**
     * 自定义返回按钮监听
     * @return
     */
    @Override
    public boolean onBackPressed() {
        if(unfoldableView.isUnfolded() || unfoldableView.isUnfolding()){
            unfoldableView.foldBack();
            currentTime.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void loadContent(List<Content> contents) {
        articleList = contents;
        //实现一屏多页
        //viewPager2.setOffscreenPageLimit(articleList.size());//设置预加载页
        recyclerView = viewPager2.getChildAt(0);
        if(recyclerView != null && recyclerView instanceof RecyclerView){
            recyclerView.setPadding(70, 0, 70, 0);
            ((RecyclerView) recyclerView).setClipToPadding(false);
        }
        //设置Transformer
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new ScaleInTransformer());
        viewPager2.setPageTransformer(compositePageTransformer);
        contentAdapter = new ContentAdapter(getContext(),articleList,unfoldableView,detailsLayout);
        viewPager2.setAdapter(contentAdapter);

    }

    @Override
    public void loadByTime(List<Content> contents) {
        articleList.clear();
        articleList = contents;
        Log.e("article",articleList.toString());
        contentAdapter = new ContentAdapter(getContext(),articleList,unfoldableView,detailsLayout);
        viewPager2.setAdapter(contentAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onLoadDetailsMain(Content content){
        if(content != null) {
            detailsTitle.setText(content.getTitle());
            detailsWriter.setText(content.getWriter());
            detailsText.setText(content.getText());
            RequestOptions requestOptions = new RequestOptions().centerCrop();
            Glide.with(getActivity())
                    .load(ConfigUtil.BASE_IMG_URL + content.getPic())
                    .apply(requestOptions)
                    .into(detailsImg);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onLoadByTime(Day day){
        if(day.getDay() != 0) {
            selectDate.setText(day.getMonthInEnglish() + ". " + day.getYear());
            selectDay.setText(day.getDay() + "");
            Log.e("day",day.getYear() + ". " + day.getMonth() + day.getDay());
            presenter.loadByTime(day);
        }
    }
}
