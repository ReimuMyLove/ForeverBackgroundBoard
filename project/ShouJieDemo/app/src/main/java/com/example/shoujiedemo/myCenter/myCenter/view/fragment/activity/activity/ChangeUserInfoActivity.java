package com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.presenter.UserInfoPresenter;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.ChangeUserInfoView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.UserUtil;

public class ChangeUserInfoActivity extends BaseActivity implements ChangeUserInfoView {
    EditText
            userInfoChange_age,
            userInfoChange_sign;
    RadioGroup
            userInfoChange_sex;
    RadioButton
            userInfoChange_man,
            userInfoChange_woman;
    Button
            userInfoChange_enter;
    UserInfoPresenter userInfoPresenter;

    private static int userAge = 0;
    private static String userSign = "";
    private static String userSex = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfochange);
        findView();
        setListener();
        userInfoPresenter = new UserInfoPresenter(this);
        setViewState();
    }

    private void findView(){
        userInfoChange_age = findViewById(R.id.userInfoChange_age);
        userInfoChange_sign = findViewById(R.id.userInfoChange_sign);
        userInfoChange_sex = findViewById(R.id.userInfoChange_sex);
        userInfoChange_man = findViewById(R.id.userInfoChange_man);
        userInfoChange_woman = findViewById(R.id.userInfoChange_woman);
        userInfoChange_enter = findViewById(R.id.userInfoChange_enter);

    }

    @SuppressLint("SetTextI18n")
    private void setViewState(){
        userInfoChange_age.setText(UserUtil.USER_AGE+"");
        userInfoChange_sign.setText(UserUtil.USER_SIGN);
        //设置编辑框属性
        userInfoChange_sign.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        userInfoChange_sign.setSingleLine(false);
        userInfoChange_sign.setHorizontallyScrolling(false);
    }

    private void setListener(){
        MyListener listener = new MyListener();
        userInfoChange_enter.setOnClickListener(listener);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.userInfoChange_enter:
                    userInfoChange();
                    break;
            }
        }
    }
    private void userInfoChange(){
        int userID = UserUtil.USER_ID;
        userAge = 0;
        if (!userInfoChange_age.getText().toString().equals("")){
            userAge = Integer.parseInt(userInfoChange_age.getText().toString());
        }else{
            Toast.makeText(this, "偶然：年龄未填", Toast.LENGTH_SHORT).show();
        }
        userSign = userInfoChange_sign.getText().toString();
        setSex();
        if(userSex.equals("")){
            Toast.makeText(this, "偶然：未选择性别", Toast.LENGTH_SHORT).show();
        }
        else{
            userInfoPresenter.changeInfo(userID,userAge,userSex,userSign);
        }
    }

    private void setSex(){
        int count = userInfoChange_sex.getChildCount();
        for(int i = 0 ;i < count;i++){
            RadioButton rb = (RadioButton)userInfoChange_sex.getChildAt(i);
            if(rb.isChecked()){
                int tag = Integer.parseInt(rb.getTag().toString());
                if(tag == 0 ){
                    userSex = "男";
                }else if(tag ==1){
                    userSex = "女";
                }
                break;
            }
        }
    }

    @Override
    public void changeInfoFailed() {
        Toast.makeText(this, "偶然：修改失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeInfoSuccessful() {
        Toast.makeText(this, "偶然：修改成功", Toast.LENGTH_SHORT).show();
        User user = new User();
        user.setAge(userAge);
        user.setSex(userSex);
        user.setSign(userSign);
        Intent intent = new Intent();
        intent.putExtra("user",user);
        setResult(100,intent);
        finish();
    }
}
