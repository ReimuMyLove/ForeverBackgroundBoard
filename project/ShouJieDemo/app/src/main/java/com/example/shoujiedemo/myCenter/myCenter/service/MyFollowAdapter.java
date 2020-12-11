package com.example.shoujiedemo.myCenter.myCenter.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.presenter.MyFollowPresenter;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyFollowAdapterView;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.MySpaceActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<User> userList;

    public MyFollowAdapter(Context mContext, List<User> userList) {
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyFollowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.myfollow_list, parent, false);
        return new MyFollowAdapter.ViewHolder(view,mContext);//返回一个holder对象，给下个方法使用
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyFollowAdapter.ViewHolder holder, int position) {
        if (userList!=null){
            User user = userList.get(position);
            holder.userID = user.getId();
            holder.myFollow_userName.setText(user.getName());
            holder.myFollow_userSign.setText(user.getSign());
            holder.MyFollow_userFans.setText(user.getFennum()+"");
            String URL = ConfigUtil.BASE_HEAD_URL+user.getPicname();
            RequestOptions requestOptions = new RequestOptions().centerCrop();
            Glide.with(mContext)
                    .load(URL)
                    .apply(requestOptions)
                    .into(holder.myFollow_img);
        }
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements MyFollowAdapterView{
        TextView
                myFollow_userName,      //关注人用户名
                myFollow_userSign,      //关注人签名
                MyFollow_userFans;      //关注人粉丝数
        Button
                myFollow_cancel;        //取消关注
        CircleImageView
                myFollow_img;           //关注人头像
        Context context;
        MyFollowPresenter
                myFollowPresenter;       //
        int userID = 0;
        ImageView anim;
        public ViewHolder(View view,Context context){
            super(view);
            this.context = context;
            //获取控件
            findView(view);
            //设置监听器
            setListener();
            myFollowPresenter = new MyFollowPresenter(this);
        }

        /**
         * findView方法
         */
        private void findView(View view){
            myFollow_userName = view.findViewById(R.id.myFollow_userName);
            myFollow_userSign = view.findViewById(R.id.myFollow_userSign);
            myFollow_cancel = view.findViewById(R.id.myFollow_cancel);
            myFollow_img = view.findViewById(R.id.myFollow_img);
            MyFollow_userFans = view.findViewById(R.id.myFollow_userFans);
            anim = view.findViewById(R.id.follow_anim);
        }

        public void setListener(){
            MyListener listener = new ViewHolder.MyListener();
            myFollow_cancel.setOnClickListener(listener);
            myFollow_img.setOnClickListener(listener);
        }

        @Override
        public void cancelFailed() {
            String result = "false";
            EventBus.getDefault().postSticky(result);
        }

        @Override
        public void cancelSuccessful(int followerID) {
            EventBus.getDefault().postSticky(new Integer(followerID));
        }

        /**
         * 绑定监听器方法
         */
        private class MyListener implements View.OnClickListener{
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.myFollow_img:
                        visitFollower(userID,context);
                        break;
                    case R.id.myFollow_cancel:
                        cancelFollow(userID);
                        /*anim.setVisibility(View.VISIBLE);
                        myFollow_cancel.setVisibility(View.INVISIBLE);*/
                        break;
                }
            }
        }

        /**
         * 拜访关注人方法
         */
        private void visitFollower(int userID,Context context){
            Intent intent = new Intent(context, MySpaceActivity.class);
            UserUtil.RECENT_USER_ID = userID;
            context.startActivity(intent);
        }

        /**
         * 取消关注方法
         */
        private void cancelFollow(int userID){
            myFollowPresenter.cancelFollower(userID);
        }
    }
}
