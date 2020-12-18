package com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.shoujiedemo.Log.activity.LoginActivity;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.myCenter.setting.presenter.impl.SettingPresenter;
import com.example.shoujiedemo.myCenter.setting.view.inter.DestroyAccountView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.SwitchButton;
import com.example.shoujiedemo.util.UserUtil;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

public class DestroyActivity extends BaseActivity implements DestroyAccountView {
    SwitchButton
            destroyAccount_switch;          //开启销毁账户按钮
    TextView
            destroyAccount_userID,          //用户ID
            destroyAccount_userName;        //用户名
    Button
            destroyAccount_enter,           //确认销毁
            destroyAccount_return;          //返回按钮

    SettingPresenter
            settingPresenter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destroy);
        //获取控件
        findView();
        //设置监听器
        setListener();
        //绑定Presenter
        settingPresenter = new SettingPresenter(this);
        destroyAccount_userID.setText("用户ID："+UserUtil.USER_ID);
        destroyAccount_userName.setText("用户名："+UserUtil.USER_NAME);
    }

    /**
     * 获取控件方法
     */
    private void findView(){
        destroyAccount_enter = findViewById(R.id.destroyAccount_enter);
        destroyAccount_switch = findViewById(R.id.destroyAccount_switch);
        destroyAccount_userID = findViewById(R.id.destroyAccount_userID);
        destroyAccount_userName = findViewById(R.id.destroyAccount_userName);
        destroyAccount_enter = findViewById(R.id.destroyAccount_enter);
        destroyAccount_return = findViewById(R.id.destroyAccount_return);
    }

    /**
     * 设置监听器方法
     */
    public void setListener(){
        MyListener listener = new MyListener();
        destroyAccount_switch.setOnClickListener(listener);
        destroyAccount_return.setOnClickListener(listener);
        destroyAccount_enter.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.destroyAccount_return:
                    onBackPressed();
                    break;
                case R.id.destroyAccount_enter:
                    destroyAccountEnter();
                    break;
                case R.id.destroyAccount_switch:
                    changeEnter();
            }
        }
    }

    //改变确认按钮点击状态
    private void changeEnter(){
        if(destroyAccount_switch.isChecked()){
            destroyAccount_enter.setClickable(true);
        }else{
            destroyAccount_enter.setClickable(false);
        }
        destroyAccount_switch.startAnimate();
    }

    private void destroyAccountEnter(){
        int userID = UserUtil.USER_ID;
        settingPresenter.destroyAccountEnter(userID);
    }

    @Override
    public void destroyAccountSuccessful() {
        Toast.makeText(this, "偶然：销毁账户成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void destroyAccountFailed() {
        Toast.makeText(this, "偶然：销毁账户失败", Toast.LENGTH_SHORT).show();
    }
}
