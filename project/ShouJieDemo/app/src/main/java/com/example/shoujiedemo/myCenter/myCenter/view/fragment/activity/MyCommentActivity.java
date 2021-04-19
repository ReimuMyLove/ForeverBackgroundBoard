package com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.presenter.MyCommentPresenter;
import com.example.shoujiedemo.myCenter.myCenter.service.MyAgreementAdapter;
import com.example.shoujiedemo.myCenter.myCenter.service.MyCommentAdapter;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyCommentView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.UserUtil;

import java.util.List;

public class MyCommentActivity extends BaseActivity implements MyCommentView {
    RecyclerView
            myComment_Rec;
    Button
            myComment_return;
    MyCommentPresenter myCommentPresenter;
    MyCommentAdapter myCommentAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storing);
        findView();
        setListener();
        myCommentPresenter = new MyCommentPresenter(this);
        //获取数据
        getCommentList();
    }
    private void findView(){
        myComment_Rec = findViewById(R.id.myComment_Rec);
        myComment_return = findViewById(R.id.myComment_return);
    }

    /**
     * 设置监听器
     */
    private void setListener(){
        MyListener listener = new MyListener();
        myComment_return.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.myComment_return) {
                onBackPressed();
            }
        }
    }

    /**
     * 获取数据
     */
    private void getCommentList(){
        myCommentPresenter.getCommentList(UserUtil.USER_ID);
    }

    /**
     * 获取数据回调方法
     */

    @Override
    public void getCommentListSuccessful(List<Comment> commentList, List<Content> writerArticleList, List<User> userList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myComment_Rec.setLayoutManager(linearLayoutManager);
        myCommentAdapter = new MyCommentAdapter(this,commentList,writerArticleList,userList);
        myComment_Rec.setAdapter(myCommentAdapter);
    }

    @Override
    public void getCommentListFailed() {
        Toast.makeText(this, "偶然：获取评论列表失败", Toast.LENGTH_SHORT).show();
    }
}
