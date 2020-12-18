package com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.ChangePasswordActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.DestroyActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.SetEmailActivity;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.SetSafeQuestionActivity;
import com.example.shoujiedemo.myCenter.setting.view.inter.SafeView;
import com.example.shoujiedemo.util.BaseActivity;

public class SafeActivity extends BaseActivity implements SafeView {
    Button  myCenter_setting_safe_return;           //返回按钮
    View
            myCenter_setting_safe_changePassword,   //修改密码
            myCenter_setting_safe_setSafeQuestion,  //安全问题
            myCenter_setting_safe_setEmail,         //设置邮箱
            myCenter_setting_safe_destroyAccount;   //销毁账户
    Context context;                                //获取当前上下文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        context = this.getBaseContext();
        //获取控件
        FindView();
        //绑定监听器
        SetListener();
    }

    /**
     * 获取view方法
     */
    @Override
    public void FindView() {
        myCenter_setting_safe_return = findViewById(R.id.myCenter_setting_safe_return);
        myCenter_setting_safe_changePassword = findViewById(R.id.myCenter_setting_safe_changePassword);
        myCenter_setting_safe_setSafeQuestion = findViewById(R.id.myCenter_setting_safe_setSafeQuestion);
        myCenter_setting_safe_setEmail = findViewById(R.id.myCenter_setting_safe_setEmail);
        myCenter_setting_safe_destroyAccount = findViewById(R.id.myCenter_setting_safe_destroyAccount);
    }

    /**
     * 设置监听器方法
     */
    @Override
    public void SetListener(){
        MyListener listener = new MyListener();
        myCenter_setting_safe_return.setOnClickListener(listener);
        myCenter_setting_safe_changePassword.setOnClickListener(listener);
        myCenter_setting_safe_setSafeQuestion.setOnClickListener(listener);
        myCenter_setting_safe_setEmail.setOnClickListener(listener);
        myCenter_setting_safe_destroyAccount.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.myCenter_setting_safe_return:
                    onBackPressed();
                    break;
                case R.id.myCenter_setting_safe_changePassword:
                    ChangePassword();
                    break;
                case R.id.myCenter_setting_safe_setSafeQuestion:
                    SetSafeQuestion();
                    break;
                case R.id.myCenter_setting_safe_setEmail:
                    SetEmail();
                    break;
                case R.id.myCenter_setting_safe_destroyAccount:
                    DestroyAccount();
                    break;
            }
        }
    }


    /* 返回设置页面 */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /* 修改密码 */
    public void ChangePassword() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /* 设置安全问题 */
    public void SetSafeQuestion() {
        Intent intent = new Intent(this, SetSafeQuestionActivity.class);
        startActivity(intent);
    }

    /* 设置邮箱 */
    public void SetEmail() {
        Intent intent = new Intent(this, SetEmailActivity.class);
        startActivity(intent);
    }

    /* 销毁账户 */
    public void DestroyAccount() {
        Intent intent = new Intent(this, DestroyActivity.class);
        startActivity(intent);
    }
}
