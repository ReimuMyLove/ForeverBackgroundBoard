package com.example.shoujiedemo.fround.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.adapter.MyFragmentPagerAdapter;
import com.example.shoujiedemo.bean.SearchEvent;
import com.example.shoujiedemo.util.ToastUtils;
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;

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
    private Button btnSearch;
    private EditText edSearch;




    public FroundFragment(ViewPager2 viewPager2,Context context) {
        // Required empty public constructor
        this.viewPager2 = viewPager2;
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fround, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        froundViewPager2 = view.findViewById(R.id.fround_view);
        froudnTab = view.findViewById(R.id.fround_topTab);
        btnSearch = view.findViewById(R.id.btn_search);
        edSearch = view.findViewById(R.id.ed_search);

        MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("文章", getResources().getDrawable(R.drawable.article), 30);

        MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("诗", getResources().getDrawable(R.drawable.poem), 90);

        MultiChoicesCircleButton.Item item3 = new MultiChoicesCircleButton.Item("感悟", getResources().getDrawable(R.drawable.heart), 150);

        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();
        buttonItems.add(item1);
        buttonItems.add(item2);
        buttonItems.add(item3);

        MultiChoicesCircleButton multiChoicesCircleButton = (MultiChoicesCircleButton) view.findViewById(R.id.multiChoicesCircleButton);
        multiChoicesCircleButton.setButtonItems(buttonItems);

        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {
                Toast.makeText(getActivity(),item.getText(),Toast.LENGTH_SHORT).show();
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

}
