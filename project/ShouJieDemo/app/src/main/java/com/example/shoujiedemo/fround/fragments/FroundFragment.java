package com.example.shoujiedemo.fround.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.activity.MusicActivity;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.bean.MusicEvent;
import com.example.shoujiedemo.bean.SearchEvent;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.fround.service.NetPlayMusicService;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperatePresenterImpl;
import com.example.shoujiedemo.upload.activity.AriticleUploadActivity;
import com.example.shoujiedemo.upload.activity.HeartUploadActivity;
import com.example.shoujiedemo.upload.activity.MusicUploadActivity;
import com.example.shoujiedemo.upload.activity.PoemUploadActivity;
import com.example.shoujiedemo.util.MusicPlayUtil;
import com.example.shoujiedemo.util.ToastUtils;
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现页面
 */
public class FroundFragment extends Fragment{

    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager2 froundViewPager2;
    private TabLayout froudnTab;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager2 viewPager2;//父Viewpager
    private int currentPosition = 0;    //当前滑动位置
    private int oldPositon = 0;          //上一个滑动位置
    private int currentState = 0;        //记录当前手指按下状态
    private List<Integer> scrolledPixeledList = new ArrayList<>(); //记录手指滑动时的像素坐标记录
    private Context context;
    private HotFragment hotFragment;
    private ArticleFragment articleFragment;
    private MindFragment mindFragment;
    private PoemFragment poemFragment;
    private MusicFragment musicFragment;
    private Button btnSearch;//搜索按钮
    private EditText edSearch;//搜索框
    private View alreadyMusicView;//音乐布局
    private Button btnAlreadyPlay;//播放
    private ImageView alreayMusicCover;//音乐封面
    private TextView alreayMusicSinger;//歌手
    private TextView alreayMusicName;//名字
    private Button getBtnAlreadyDismiss;//关闭
    private Animation animation;
    private Music music;
    private User user;


    public FroundFragment(){

    }


    public FroundFragment(ViewPager2 viewPager2,Context context) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
        this.context = context;

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
        View view = inflater.inflate(R.layout.fragment_fround, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("FroundFragment","onDestroy");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("FroundFragment","onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("FroundFragment","onStart");
        if(!MusicPlayUtil.IS_PAUSE && !MusicPlayUtil.IS_STOP){
            animation = AnimationUtils.loadAnimation(context, R.anim.song_cover_rotate);
            LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
            animation.setInterpolator(lin);
            alreayMusicCover.startAnimation(animation);
        }
    }

    public void initView(View view){
        froundViewPager2 = view.findViewById(R.id.fround_view);
        froudnTab = view.findViewById(R.id.fround_topTab);
        btnSearch = view.findViewById(R.id.btn_search);
        edSearch = view.findViewById(R.id.ed_search);
        alreadyMusicView = view.findViewById(R.id.already_music_view);
        btnAlreadyPlay = view.findViewById(R.id.already_music_view_btn_play);
        alreayMusicCover = view.findViewById(R.id.already_music_view_cover);
        alreayMusicSinger = view.findViewById(R.id.already_music_view_tv_singer);
        alreayMusicName = view.findViewById(R.id.already_music_view_tv_songName);
        getBtnAlreadyDismiss = view.findViewById(R.id.already_music_view_dismiss);

        alreadyMusicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,MusicActivity.class);
                Bundle bundle = new Bundle();
                music.setStart(1);
                bundle.putSerializable("music",music);
                bundle.putSerializable("user",user);
                Log.e("user",user.toString());
                intent.putExtra("bundle",bundle);
                startActivity(intent);

            }
        });

        btnAlreadyPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicPlayUtil.IS_PAUSE){
                    MusicEvent event = new MusicEvent();
                    event.setTag(3);
                    EventBus.getDefault().postSticky(event);
                }else{
                    MusicEvent event = new MusicEvent();
                    event.setTag(1);
                    EventBus.getDefault().postSticky(event);

                }
            }
        });

        getBtnAlreadyDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alreadyMusicView.setVisibility(View.GONE);
                //isPause = false;
                if(MusicPlayUtil.IS_PAUSE){
                    MusicEvent event = new MusicEvent();
                    event.setTag(2);
                    EventBus.getDefault().postSticky(event);
                }
            }
        });

        MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("文章", getResources().getDrawable(R.drawable.article), 15);

        MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("诗", getResources().getDrawable(R.drawable.poem), 65);

        MultiChoicesCircleButton.Item item3 = new MultiChoicesCircleButton.Item("感悟", getResources().getDrawable(R.drawable.heart), 115);

        MultiChoicesCircleButton.Item item4 = new MultiChoicesCircleButton.Item("音乐", getResources().getDrawable(R.drawable.music), 165);


        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();
        buttonItems.add(item1);
        buttonItems.add(item2);
        buttonItems.add(item3);
        buttonItems.add(item4);

        MultiChoicesCircleButton multiChoicesCircleButton = (MultiChoicesCircleButton) view.findViewById(R.id.multiChoicesCircleButton);
        multiChoicesCircleButton.setButtonItems(buttonItems);

        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {
                switch (index){
                    case 0:
                        Intent intent0=new Intent();
                        intent0.setClass(getContext(), AriticleUploadActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1=new Intent();
                        intent1.setClass(getContext(), PoemUploadActivity.class);
                        startActivity(intent1);

                        break;
                    case 2:
                        Intent intent2=new Intent();
                        intent2.setClass(getContext(), HeartUploadActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent();
                        intent3.setClass(getContext(), MusicUploadActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });

        if(hotFragment == null) {
            hotFragment = new HotFragment();
            articleFragment = new ArticleFragment();
            mindFragment = new MindFragment();
            poemFragment = new PoemFragment();
            musicFragment = new MusicFragment();

            fragments.add(hotFragment);
            fragments.add(articleFragment);
            fragments.add(mindFragment);
            fragments.add(poemFragment);
            fragments.add(musicFragment);
        }
        froundViewPager2.setOffscreenPageLimit(1);

        fragmentPagerAdapter = new MyFragmentPagerAdapter((FragmentActivity) context,fragments);
        froundViewPager2.setAdapter(fragmentPagerAdapter);

        new TabLayoutMediator(froudnTab, froundViewPager2, false, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("热门");
                }else if(position == 1){
                    tab.setText("文章");
                }else if(position == 2){
                    tab.setText("感悟");
                }else if(position == 3){
                    tab.setText("诗");
                }else if(position == 4){
                    tab.setText("音乐");
                }
            }
        }).attach();


        froundViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){

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
                            if(viewPager2.getCurrentItem() > 0){
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1);
                            }
                        }else if(currentPosition == fragmentPagerAdapter.getItemCount() - 1){
                            if(viewPager2.getCurrentItem() <  fragmentPagerAdapter.getItemCount() - 1){
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                            }
                        }
                    }
                    oldPositon = currentPosition;
                    scrolledPixeledList.clear();//清空滑动记录
                }
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    SearchEvent event = new SearchEvent();
                    event.setPosition(-1);
                    event.setTag(edSearch.getText().toString());
                    EventBus.getDefault().postSticky(event);


            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(currentPosition){
                    case 0:
                        SearchEvent hot = new SearchEvent();
                        hot.setPosition(currentPosition);
                        hot.setTag(edSearch.getText().toString());
                        EventBus.getDefault().postSticky(hot);
                        break;
                    case 1:
                        SearchEvent article = new SearchEvent();
                        article.setPosition(currentPosition);
                        article.setTag(edSearch.getText().toString());
                        EventBus.getDefault().postSticky(article);
                        break;
                    case 2:
                        SearchEvent heart = new SearchEvent();
                        heart.setPosition(currentPosition);
                        heart.setTag(edSearch.getText().toString());
                        EventBus.getDefault().postSticky(heart);
                        break;
                    case 3:
                        SearchEvent poem = new SearchEvent();
                        poem.setPosition(currentPosition);
                        poem.setTag(edSearch.getText().toString());
                        EventBus.getDefault().postSticky(poem);
                        break;
                    case 4:
                        SearchEvent music = new SearchEvent();
                        music.setPosition(currentPosition);
                        music.setTag(edSearch.getText().toString());
                        EventBus.getDefault().postSticky(music);
                        break;
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED,sticky = true)
    public void onMainPlay(Music music1){
        music = music1;
        if(music.getTag() == 1) {
            if(!MusicPlayUtil.IS_STOP) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.iv_default)
                        .fallback(R.drawable.ouran_default)
                        .centerCrop();
                if (music.getPic() != null) {
                    Glide.with(context)
                            .load(music.getPic())
                            .apply(requestOptions)
                            .into(alreayMusicCover);
                }

                if (!MusicPlayUtil.IS_PAUSE) {
                    animation = AnimationUtils.loadAnimation(context, R.anim.song_cover_rotate);
                    LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
                    animation.setInterpolator(lin);
                    alreayMusicCover.startAnimation(animation);
                    alreayMusicName.setText(music.getName());
                    alreayMusicSinger.setText(music.getSinger());
                    btnAlreadyPlay.setBackgroundResource(R.drawable.pause);
                    alreadyMusicView.setVisibility(View.VISIBLE);
                } else {
                    alreayMusicCover.clearAnimation();
                    btnAlreadyPlay.setBackgroundResource(R.drawable.play);
                }
            }else{
                alreayMusicCover.clearAnimation();
                btnAlreadyPlay.setBackgroundResource(R.drawable.play);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getUser(User user){
        this.user = user;
    }



}
