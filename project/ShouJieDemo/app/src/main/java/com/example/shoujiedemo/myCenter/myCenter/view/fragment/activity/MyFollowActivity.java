package com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.presenter.MyFollowPresenter;
import com.example.shoujiedemo.myCenter.myCenter.service.MyFollowAdapter;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyFollowView;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MyFollowActivity extends BaseActivity implements MyFollowView {
    private List<User> userList;
    private MyFollowPresenter myFollowPresenter;
    RecyclerView
            myFollow_articleRec;
    ImageView
            myFollow_return;
    MyFollowAdapter
            myFollowAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfollow);
        //获取控件
        findView();
        setListener();
        myFollowPresenter = new MyFollowPresenter(this);
        getFollower();
        EventBus.getDefault().register(this);
    }

    private void findView(){
        myFollow_articleRec = findViewById(R.id.myFollow_articleRec);
        myFollow_return = findViewById(R.id.myFollow_return);
    }

    private void setListener(){
        MyListener listener = new MyListener();
        myFollow_return.setOnClickListener(listener);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.myFollow_return){
                onBackPressed();
            }
        }
    }

    private void getFollower(){
        int userID = UserUtil.USER_ID;
        myFollowPresenter.findFollower(userID);
    }

    @Override
    public void getFollows(List<User> userList) {
        this.userList = userList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myFollow_articleRec.setLayoutManager(linearLayoutManager);
        myFollowAdapter = new MyFollowAdapter(this,userList);
        myFollow_articleRec.setAdapter(myFollowAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void removeFollow(Integer followerID){
        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getId() == followerID){
                userList.remove(i);
                break;
            }
        }
        Toast.makeText(this, "偶然：取消关注成功", Toast.LENGTH_SHORT).show();
        myFollowAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void cancelFollowFailed(String result){
        if(result.equals("false")){
            Toast.makeText(this, "偶然：取消关注失败", Toast.LENGTH_SHORT).show();
        }
    }
}
