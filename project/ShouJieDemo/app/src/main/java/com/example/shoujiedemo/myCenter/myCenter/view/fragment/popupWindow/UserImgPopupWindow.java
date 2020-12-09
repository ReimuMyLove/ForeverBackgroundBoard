package com.example.shoujiedemo.myCenter.myCenter.view.fragment.popupWindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.shoujiedemo.Log.activity.LoginActivity;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.home.recommen.activity.MainActivity;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.popupWindow.imgChange.UserImgChangeActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.SettingActivity;

public class UserImgPopupWindow extends PopupWindow {
    private TextView
            myCenter_UserImg_wallpaper,         //获取壁纸按钮
            myCenter_UserImg_img,               //获取头像按钮
            myCenter_UserImg_changeUser,        //获取切换用户按钮
            myCenter_UserImg_cancel;            //获取取消按钮
    private LinearLayout
            myCenter_UserImg_pop;               //获取当前窗口幕布
    Context context;
    @SuppressLint("InflateParams")
    public UserImgPopupWindow(Context context){
        super(context);
        this.context = context;
        //获取当前PopupWindow
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //当前弹出窗口
        View userImgPopupWindow = inflater.inflate(R.layout.popup_userimgfunction, null);
        //设置PopupWindow
        this.setContentView(userImgPopupWindow);
        //设置PopupWindow长宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //获取view
        FindView(userImgPopupWindow);
        //设置监听器
        SetListener();
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
    }

    /**
     * 获取view
     * @param view :获取view控件
     */
    private void FindView(View view){
        myCenter_UserImg_wallpaper = view.findViewById(R.id.myCenter_userImg_wallpaper);
        myCenter_UserImg_img = view.findViewById(R.id.myCenter_userImg_img);
        myCenter_UserImg_changeUser = view.findViewById(R.id.myCenter_userImg_changeUser);
        myCenter_UserImg_cancel = view.findViewById(R.id.myCenter_userImg_cancel);
        myCenter_UserImg_pop = view.findViewById(R.id.myCenter_userImg_pop);
    }

    /**
     * 设置监听器
     */
    private void SetListener(){
        MyListener listener = new MyListener();
        myCenter_UserImg_wallpaper.setOnClickListener(listener);
        myCenter_UserImg_img.setOnClickListener(listener);
        myCenter_UserImg_changeUser.setOnClickListener(listener);
        myCenter_UserImg_cancel.setOnClickListener(listener);
        myCenter_UserImg_pop.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.myCenter_userImg_wallpaper:
                    WallpaperChange();
                    dismiss();
                    break;
                case R.id.myCenter_userImg_img:
                    ImgChange();
                    dismiss();
                    break;
                case R.id.myCenter_userImg_changeUser:
                    UserChange(context);
                    dismiss();
                    break;
                case R.id.myCenter_userImg_cancel:
                case R.id.myCenter_userImg_pop:
                    dismiss();
                    break;
            }
        }
    }

    private void UserChange(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private void ImgChange() {
        Intent intent = new Intent(context, UserImgChangeActivity.class);
        context.startActivity(intent);
    }

    private void WallpaperChange() {

    }
}
