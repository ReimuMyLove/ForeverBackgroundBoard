package com.example.shoujiedemo.myCenter.myCenter.view.fragment.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.shoujiedemo.R;

public class UserImgPopupWindow extends PopupWindow {
    private TextView
            myCenter_UserImg_wallpaper,         //获取壁纸按钮
            myCenter_UserImg_img,               //获取头像按钮
            myCenter_UserImg_changeUser,        //获取切换用户按钮
            myCenter_UserImg_cancel;            //获取取消按钮
    private View
            userImgPopupWindow;                 //当前弹出窗口
    private LinearLayout
            myCenter_UserImg_pop;               //获取当前窗口幕布

    public UserImgPopupWindow(Context context){
        super(context);
        //获取当前PopupWindow
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userImgPopupWindow = inflater.inflate(R.layout.popup_userimgfunction, null);
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
     * @param view
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
                    UserChange();
                    dismiss();
                    break;
                case R.id.myCenter_userImg_cancel:
                case R.id.myCenter_userImg_pop:
                    dismiss();
                    break;
            }
        }
    }

    private void UserChange() {
    }

    private void ImgChange() {
    }

    private void WallpaperChange() {
    }
}
