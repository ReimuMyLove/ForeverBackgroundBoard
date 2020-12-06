package com.example.shoujiedemo.myCenter.myCenter.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.myCenter.myCenter.presenter.OwnerPresenter;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.HelpCenterActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.MyAgreementActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.MyFollowActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.StoringActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.UserInfoActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.popupWindow.UserImgPopupWindow;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.MySpaceActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.SettingActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.StatusBarUtil;
import com.example.shoujiedemo.util.TrancircleImageView;

/**
 * 个人页面
 */
public class OwnerFragment extends Fragment {
    TrancircleImageView
            myCenter_userImg_background;    //获取头像背景三角
    CircleImageView
            myCenter_userImg;               //获取头像
    ImageView
            myCenter_userBackground;        //获取用户背景
    View
            myCenter_title,                 //获取标题
            myCenter_userImg_top,           //获取头像辅助定位框
            myCenter_card_top,              //获取卡片辅助定位框
            myCenter_mySpace,               //获取我的空间按钮
            myCenter_userInfo,              //获取个人信息按钮
            myCenter_Storing,               //获取收藏按钮
            myCenter_myFollow,              //获取我的关注按钮
            myCenter_myAgreement,           //获取我的点赞按钮
            myCenter_helpCenter,           //获取帮助中心按钮
            myCenter_userInformation;       //获取用户信息栏
    CardView
            myCenter_card       ;           //获取选项卡片
    Button
            myCenter_setting_intent;        //获取设置跳转按钮
    OwnerPresenter ownerPresenter  ;        //获取Presenter
    UserImgPopupWindow
            userImgPopupWindow;             //获取PopupWindow
    View view;

    public OwnerFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mycenter, container, false);
        /* 获取控件 */
        FindView(view);
        /* 设置监听器 */
        SetListener();
        /* 设置Presenter */
        ownerPresenter = new OwnerPresenter();
        /* 配置控件长宽高 */
        SetViewHW();
        userImgPopupWindow = new UserImgPopupWindow(view.getContext());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
    }

    /**
     * 获取控件
     * @param view fragment_mycenter
     */
    private void FindView(View view){
        myCenter_userImg_background = view.findViewById(R.id.myCenter_userImg_background);
        myCenter_userBackground = view.findViewById(R.id.myCenter_userBackGround);
        myCenter_userImg = view.findViewById(R.id.myCenter_userImg);
        myCenter_setting_intent = view.findViewById(R.id.myCenter_setting_intent);
        myCenter_userImg_top = view.findViewById(R.id.myCenter_userImg_top);
        myCenter_card = view.findViewById(R.id.myCenter_card);
        myCenter_card_top = view.findViewById(R.id.myCenter_card_top);
        myCenter_mySpace = view.findViewById(R.id.myCenter_mySpace);
        myCenter_userInfo = view.findViewById(R.id.myCenter_userInfo);
        myCenter_Storing = view.findViewById(R.id.myCenter_Storing);
        myCenter_myFollow = view.findViewById(R.id.myCenter_myFollow);
        myCenter_myAgreement = view.findViewById(R.id.myCenter_myAgreement);
        myCenter_helpCenter = view.findViewById(R.id.myCenter_helpCenter);
        myCenter_userInformation = view.findViewById(R.id.myCenter_userInformation);
    }

    /**
     * 设置监听器方法
     */
    public void SetListener() {
        MyListener listener = new MyListener();
        myCenter_userImg.setOnClickListener(listener);
        myCenter_setting_intent.setOnClickListener(listener);
        myCenter_mySpace.setOnClickListener(listener);
        myCenter_userInfo.setOnClickListener(listener);
        myCenter_Storing.setOnClickListener(listener);
        myCenter_myFollow.setOnClickListener(listener);
        myCenter_myAgreement.setOnClickListener(listener);
        myCenter_helpCenter.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.myCenter_setting_intent:
                    SettingIntent();
                    break;
                case R.id.myCenter_mySpace:
                    MySpaceIntent();
                    break;
                case R.id.myCenter_userInfo:
                    UserInfoIntent();
                    break;
                case R.id.myCenter_Storing:
                    StoringIntent();
                    break;
                case R.id.myCenter_helpCenter:
                    HelpCenterIntent();
                    break;
                case R.id.myCenter_myFollow:
                    MyFollowIntent();
                    break;
                case R.id.myCenter_myAgreement:
                    MyAgreementIntent();
                    break;
                case R.id.myCenter_userImg:
                    UserImg(view);
                    break;
            }
        }
    }


    /**
     * 设置物块长宽高
     */
    private void SetViewHW(){
        /* 获取屏幕宽度 */
        DisplayMetrics wm = getResources().getDisplayMetrics();
        int width = wm.widthPixels;
        /* 获取控件自身高度 */
        ViewGroup.LayoutParams
                mC_uI_bg,       //三角控件
                mC_bg,          //背景图
                mC_uI,          //头像
                mC_uI_top,      //头像定位辅助框
                mC_card;        //卡片
        //获取背景控件
        mC_bg = myCenter_userBackground.getLayoutParams();
        mC_bg.height = (width*5/7); //设置背景图为13:10的长宽比
        //获取三角控件
        mC_uI_bg = myCenter_userImg_background.getLayoutParams();
        mC_uI_bg.height = width*4/5; //设置三角高度为3/5屏幕宽度
        //获取头像定位辅助框
        mC_uI_top = myCenter_userImg_top.getLayoutParams();
        mC_uI_top.height = (width*4/5);
        myCenter_userImg_top.setPadding(0,width*3/7,width/60,0);
        //获取头像控件
        mC_uI = myCenter_userImg.getLayoutParams();
        mC_uI.height = (width/5);
        mC_uI.width = (width/5); //设置头像宽高均为1/5屏幕宽
        //获取卡片定位辅助框
        myCenter_card_top.setPadding(0,width*6/9,0,10);
        //获取卡片控件
        mC_card = myCenter_card.getLayoutParams();
        mC_card.width = (width*17/20); //设置卡片宽度为9/10屏幕宽度
        //获取用户名控件
        myCenter_userInformation.setPadding(0,width*4/9,0,0);
    }

    /**
     * 菜单列表中的各项跳转
     */
    public void SettingIntent(){
        Intent intent = new Intent(this.getContext(), SettingActivity.class);
        startActivity(intent);
    }

    public void MySpaceIntent() {
        Intent intent = new Intent(this.getContext(), MySpaceActivity.class);
        startActivity(intent);
    }

    public void UserInfoIntent() {
        Intent intent = new Intent(this.getContext(), UserInfoActivity.class);
        startActivity(intent);
    }

    public void StoringIntent() {
        Intent intent = new Intent(this.getContext(), StoringActivity.class);
        startActivity(intent);
    }

    public void MyFollowIntent() {
        Intent intent = new Intent(this.getContext(), MyFollowActivity.class);
        startActivity(intent);
    }

    public void MyAgreementIntent() {
        Intent intent = new Intent(this.getContext(), MyAgreementActivity.class);
        startActivity(intent);
    }

    private void HelpCenterIntent() {
        Intent intent = new Intent(this.getContext(), HelpCenterActivity.class);
        startActivity(intent);
    }

    /**
     * 更换头像/背景图方法
     */
    private void UserImg(View view){
        userImgPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
