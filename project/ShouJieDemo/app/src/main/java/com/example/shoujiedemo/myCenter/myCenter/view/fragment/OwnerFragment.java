package com.example.shoujiedemo.myCenter.myCenter.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.ImgChangeEvent;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.HelpCenterActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.MyAgreementActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.MyFollowActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.MyCommentActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.UserInfoActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.popupWindow.UserImgPopupWindow;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.MySpaceActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.SettingActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.TrancircleImageView;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 个人页面
 */
public class OwnerFragment extends Fragment {
    private static int eventBus = 1;
    TrancircleImageView
            myCenter_userImg_background;    //获取头像背景三角
    CircleImageView
            myCenter_userImg;               //获取头像
    ImageView
            myCenter_userBackground;        //获取用户背景
    TextView
            myCenter_userName,              //获取用户名
            myCenter_userFans;              //获取用户粉丝数
    View
            myCenter_userInformation,       //获取用户信息栏
            myCenter_userImg_top,           //获取头像辅助定位框
            myCenter_card_top,              //获取卡片辅助定位框
            myCenter_mySpace,               //获取我的空间按钮
            myCenter_userInfo,              //获取个人信息按钮
            myCenter_Storing,               //获取收藏按钮
            myCenter_myFollow,              //获取我的关注按钮
            myCenter_myAgreement,           //获取我的点赞按钮
            myCenter_helpCenter;            //获取帮助中心按钮
    CardView
            myCenter_card       ;           //获取选项卡片
    Button
            myCenter_setting_intent;        //获取设置跳转按钮
    UserImgPopupWindow
            userImgPopupWindow;             //获取PopupWindow
    View view;
    public OwnerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mycenter, container, false);
        /* 获取控件 */
        FindView(view);
        /* 设置监听器 */
        SetListener();
        /* 配置控件长宽高 */
        SetViewHW();
        //设置粉丝数、用户名
        myCenter_userName.setText(UserUtil.USER_NAME);
        myCenter_userFans.setText("粉丝数："+UserUtil.USER_FANS);
        if(!UserUtil.USER_IMG.equals("")){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.iv_default)
                    .fallback(R.drawable.ouran_default)
                    .centerCrop();
            Glide.with(this)
                    .load(ConfigUtil.BASE_HEAD_URL+UserUtil.USER_IMG)
                    .apply(requestOptions)
                    .into(myCenter_userImg);
        }
        if(!UserUtil.USER_CENTER_BACKGROUND.equals("")){     //用户背景图
            String URL = ConfigUtil.BASE_HEAD_URL+UserUtil.USER_CENTER_BACKGROUND;
            RequestOptions requestOptions = new RequestOptions().centerCrop();
            Glide.with(view.getContext())
                    .load(URL)
                    .apply(requestOptions)
                    .into(myCenter_userBackground);
        }
        userImgPopupWindow = new UserImgPopupWindow(view.getContext());
        return view;
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
        myCenter_userName = view.findViewById(R.id.myCenter_userName);
        myCenter_userFans = view.findViewById(R.id.myCenter_userFans);
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
        myCenter_userName.setOnClickListener(listener);
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
        mC_bg.height = (width*5/6); //设置背景图为13:10的长宽比
        //获取三角控件
        mC_uI_bg = myCenter_userImg_background.getLayoutParams();
        mC_uI_bg.height = width*5/6; //设置三角高度为3/5屏幕宽度
        //获取头像定位辅助框
        mC_uI_top = myCenter_userImg_top.getLayoutParams();
        mC_uI_top.height = (width*4/5);
        myCenter_userImg_top.setPadding(0,width*5/10,width/15,0);
        //获取头像控件
        mC_uI = myCenter_userImg.getLayoutParams();
        mC_uI.height = (width*3/14);
        mC_uI.width = (width*3/14); //设置头像宽高均为1/5屏幕宽
        //获取卡片定位辅助框
        //myCenter_card_top.setPadding(0,width*1/3000,0,0);
        //获取卡片控件
        mC_card = myCenter_card.getLayoutParams();
        mC_card.width = (width*17/20); //设置卡片宽度为9/10屏幕宽度
        //获取用户名控件
        myCenter_userInformation.setPadding(0,width*6/11,width*2/8,0);
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
        Intent intent = new Intent(this.getContext(), MyCommentActivity.class);
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

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void updateUserImg(ImgChangeEvent imgChangeEvent){
        int imgNumber = imgChangeEvent.getImgChangeID();
        if(imgNumber == 1){
            if(!UserUtil.USER_CENTER_BACKGROUND.equals("")){     //用户背景图
                String URL = ConfigUtil.BASE_HEAD_URL+UserUtil.USER_CENTER_BACKGROUND;
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.iv_default)
                        .fallback(R.drawable.ouran_default)
                        .centerCrop();
                Glide.with(view.getContext())
                        .load(URL)
                        .apply(requestOptions)
                        .into(myCenter_userBackground);
            }
        }else if(imgNumber == 2){
            if(!UserUtil.USER_IMG.equals("")){
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.iv_default)
                        .fallback(R.drawable.ouran_default)
                        .centerCrop();
                Glide.with(this)
                        .load(ConfigUtil.BASE_HEAD_URL+UserUtil.USER_IMG)
                        .apply(requestOptions)
                        .into(myCenter_userImg);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
