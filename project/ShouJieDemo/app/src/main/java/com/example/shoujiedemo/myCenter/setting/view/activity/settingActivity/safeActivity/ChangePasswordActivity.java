package com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.myCenter.setting.presenter.impl.SettingPresenter;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.SafeActivity;
import com.example.shoujiedemo.myCenter.setting.view.inter.ChangePasswordView;
import com.example.shoujiedemo.util.BaseActivity;

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordView {
    EditText
            changePassword_oldKey,          //旧密码
            changePassword_newKey,          //新密码
            changePassword_repeatKey;       //重复密码
    Button
            changePassword_return,          //返回按钮
            changePassword_enter;           //确认按钮

    SettingPresenter
            settingPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        //获取控件
        findView();
        //设置监听器
        setListener();
        settingPresenter = new SettingPresenter(this);
    }

    /**
     * 获取控件方法
     */
    private void findView(){
        changePassword_oldKey = findViewById(R.id.changePassword_oldKey);
        changePassword_newKey = findViewById(R.id.changePassword_newKey);
        changePassword_repeatKey = findViewById(R.id.changePassword_repeatKey);
        changePassword_return = findViewById(R.id.changePassword_return);
        changePassword_enter = findViewById(R.id.changePassword_enter);
    }

    /**
     * 设置监听器方法
     */
    public void setListener(){
        MyListener listener = new MyListener();
        changePassword_return.setOnClickListener(listener);
        changePassword_enter.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.changePassword_return:
                    onBackPressed();
                    break;
                case R.id.changePassword_enter:
                    changeKey();
                    break;
            }
        }
    }

    /**
     * 修改密码
     */
    private void changeKey(){
        String oldKey = changePassword_oldKey.getText().toString();
        String newKey = changePassword_newKey.getText().toString();
        String repeatKey = changePassword_repeatKey.getText().toString();
        settingPresenter.changeKey(oldKey,newKey,repeatKey);
    }

    /**
     * 修改密码回调方法
     */
    @Override
    public void keyRepeat() {
        Toast.makeText(this, "偶然：新密码不相同", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeKeyFailed() {
        Toast.makeText(this, "偶然：修改失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeKeySuccessful() {
        Toast.makeText(this, "偶然：修改成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeKeyError() {
        Toast.makeText(this, "偶然：原密码输入有误", Toast.LENGTH_SHORT).show();
    }
}

