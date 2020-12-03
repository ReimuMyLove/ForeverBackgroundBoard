package com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.myCenter.setting.presenter.impl.FeedbackPresenter;
import com.example.shoujiedemo.myCenter.setting.view.inter.FeedbackView;
import com.example.shoujiedemo.util.BaseActivity;

public class FeedbackViewActivity extends BaseActivity implements FeedbackView {
    Button
            myCenter_setting_feedback_return;       //返回按钮
    FeedbackPresenter feedbackPresenter;            //绑定Presenter
    Context context;                                //获取当前上下文
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        context = this.getBaseContext();
        //获取控件
        FindView();
        //绑定监听器
        SetListener();
        //绑定Presenter
        feedbackPresenter = new FeedbackPresenter();
    }

    /**
     * 设置监听器方法
     */
    @Override
    public void SetListener(){
        MyListener listener = new MyListener();
        myCenter_setting_feedback_return.setOnClickListener(listener);
    }

    /**
     * 获取view方法
     */
    @Override
    public void FindView() {
        myCenter_setting_feedback_return = findViewById(R.id.myCenter_setting_feedback_return);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.myCenter_setting_feedback_return:
                    feedbackPresenter.Return(context);
                    break;
            }
        }
    }
}
