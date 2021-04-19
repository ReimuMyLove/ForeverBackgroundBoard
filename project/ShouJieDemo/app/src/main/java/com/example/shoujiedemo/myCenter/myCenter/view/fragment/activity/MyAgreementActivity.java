package com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.presenter.MyAgreementPresenter;
import com.example.shoujiedemo.myCenter.myCenter.service.MyAgreementAdapter;
import com.example.shoujiedemo.myCenter.myCenter.service.MyFollowAdapter;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyAgreementView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.UserUtil;

import java.util.List;

public class MyAgreementActivity extends BaseActivity implements MyAgreementView {
    private RecyclerView
            myAgreementRec;             //列表
    private Button
            myAgreement_return;         //返回按钮
    private MyAgreementAdapter myAgreementAdapter;

    MyAgreementPresenter
            myAgreementPresenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myagreement);
        //获取控件
        findView();
        //设置监听器
        setListener();
        //设置presenter
        myAgreementPresenter = new MyAgreementPresenter(this);
        //获取点赞列表数据
        getAgreementList();
        //适配RecyclerView
    }
    /**
     * 获取控件
     */
    private void findView(){
        myAgreement_return = findViewById(R.id.myAgreement_return);
        myAgreementRec = findViewById(R.id.myAgreementRec);
    }

    /**
     * 设置监听器
     */
    private void setListener(){
        MyListener listener = new MyListener();
        myAgreement_return.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.myAgreement_return) {
                onBackPressed();
            }
        }
    }

    /**
     * 获取数据
     */
    private void getAgreementList(){
        Log.e("点赞信息获取","开始");
        myAgreementPresenter.getAgreement(UserUtil.USER_ID);
    }

    /**
     * 获取数据回调方法
     */

    @Override
    public void getAgreementListSuccessful(List<LikeBean> agreementList, List<Content> writerArticleList, List<User> userList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myAgreementRec.setLayoutManager(linearLayoutManager);
        myAgreementAdapter = new MyAgreementAdapter(this,agreementList,writerArticleList,userList);
        myAgreementRec.setAdapter(myAgreementAdapter);
    }

    @Override
    public void getAgreementListFailed() {
        Toast.makeText(this, "偶然：获取点赞列表失败", Toast.LENGTH_SHORT).show();
    }
}
