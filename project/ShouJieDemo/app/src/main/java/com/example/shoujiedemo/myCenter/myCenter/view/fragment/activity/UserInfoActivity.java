package com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.activity.ChangeUserInfoActivity;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.UserUtil;

public class UserInfoActivity extends BaseActivity {
    Button
            myCenter_PersonalInfo_return,
            myCenter_PersonalInfo_change;       //修改信息
    TextView
            myCenter_PersonalInfo_ID,           //用户ID
            myCenter_PersonalInfo_userName,     //用户名
            myCenter_PersonalInfo_userSex,      //用户性别
            myCenter_PersonalInfo_userAge,      //用户年龄
            myCenter_PersonalInfo_userSign;     //用户签名

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        //获取view方法
        FindView();
        //绑定监听器
        SetListener();
        //获取数据
        setInfo();
    }

    /**
     * 获取view方法
     */
    public void FindView() {
        myCenter_PersonalInfo_return = findViewById(R.id.myCenter_PersonalInfo_return);
        myCenter_PersonalInfo_ID = findViewById(R.id.myCenter_PersonalInfo_ID);
        myCenter_PersonalInfo_userName = findViewById(R.id.myCenter_PersonalInfo_userName);
        myCenter_PersonalInfo_userSex = findViewById(R.id.myCenter_PersonalInfo_userSex);
        myCenter_PersonalInfo_userAge = findViewById(R.id.myCenter_PersonalInfo_userAge);
        myCenter_PersonalInfo_change = findViewById(R.id.myCenter_PersonalInfo_change);
        myCenter_PersonalInfo_userSign = findViewById(R.id.myCenter_PersonalInfo_userSign);
    }

    /**
     * 设置监听器方法
     */
    public void SetListener() {
        MyListener listener = new MyListener();
        myCenter_PersonalInfo_return.setOnClickListener(listener);
        myCenter_PersonalInfo_change.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.myCenter_PersonalInfo_return) {
                onBackPressed();
            }else if(view.getId() == R.id.myCenter_PersonalInfo_change){
                changeIntent();
            }
        }
    }

    private void changeIntent() {
        Intent intent = new Intent(this, ChangeUserInfoActivity.class);
        startActivityForResult(intent,1);
    }

    @SuppressLint("SetTextI18n")
    private void setInfo(){
        myCenter_PersonalInfo_ID.setText(UserUtil.USER_ID+"");
        myCenter_PersonalInfo_userName.setText(UserUtil.USER_NAME);
        myCenter_PersonalInfo_userSex.setText(UserUtil.USER_SEX);
        myCenter_PersonalInfo_userAge.setText(UserUtil.USER_AGE+"");
        if(!UserUtil.USER_SIGN.equals("")){
            myCenter_PersonalInfo_userSign.setText(UserUtil.USER_SIGN);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 100) {
            assert data != null;
            User user = (User)data.getSerializableExtra("user");
            assert user != null;
            int userAge = user.getAge();
            myCenter_PersonalInfo_userAge.setText(userAge+"");
            UserUtil.USER_AGE = userAge;
            String userSex = "";
            if(user.getSex()!=null){
                userSex = user.getSex();
                UserUtil.USER_SEX = userSex;
                myCenter_PersonalInfo_userSex.setText(userSex);
            }
            String userSign = "";
            if(user.getSign()!=null){
                userSign = user.getSign();
                UserUtil.USER_SIGN = userSign;
                myCenter_PersonalInfo_userSign.setText(userSign);
            }
        }
    }
}
