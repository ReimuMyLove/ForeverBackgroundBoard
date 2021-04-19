package com.example.shoujiedemo.myCenter.myCenter.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.MsgEvent;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.presenter.MyFollowPresenter;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.MyFollowAdapterView;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.MySpaceActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.ViewHolder> implements MyFollowAdapterView{
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<User> userList;
    private MyFollowPresenter myFollowPresenter;
    private ViewHolder viewHolder = null;

    public MyFollowAdapter(Context mContext, List<User> userList) {
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.userList = userList;
        myFollowPresenter = new MyFollowPresenter(this);
    }

    @NonNull
    @Override
    public MyFollowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.myfollow_list, parent, false);
        return new MyFollowAdapter.ViewHolder(view,mContext);//返回一个holder对象，给下个方法使用
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyFollowAdapter.ViewHolder holder, final int position) {
        if (userList!=null){
            User user = userList.get(position);
            holder.userID = user.getId();
            holder.myFollow_userName.setText(user.getName());
            holder.myFollow_userSign.setText(user.getSign());
            holder.MyFollow_userFans.setText(user.getFennum()+"");
            if ( user.getPicname()!=null){
                if (!user.getPicname().equals("")){
                    String URL = ConfigUtil.BASE_HEAD_URL+user.getPicname();
                    RequestOptions requestOptions = new RequestOptions().centerCrop();
                    Glide.with(mContext)
                            .load(URL)
                            .apply(requestOptions)
                            .into(holder.myFollow_img);
                }
            }
        }
        holder.myFollow_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder = holder;
                if (holder.myFollow_cancel.getText().toString().equals("关注+")){
                    holder.anim.setVisibility(View.VISIBLE);
                    holder.myFollow_cancel.setVisibility(View.INVISIBLE);
                    holder.loadingDrawable = (AnimationDrawable)viewHolder.anim.getDrawable();
                    holder.loadingDrawable.start();
                    addFollow(userList.get(position).getId());
                }else{
                    holder.anim.setVisibility(View.VISIBLE);
                    holder.myFollow_cancel.setVisibility(View.INVISIBLE);
                    holder.loadingDrawable = (AnimationDrawable)viewHolder.anim.getDrawable();
                    holder.loadingDrawable.start();
                    cancelFollow(userList.get(position).getId());
                }
            }
        });

        holder.myFollow_img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewHolder = holder;
                User user = new User();
                user.setId(holder.userID);
                user.setFollow(false);
                UserUtil.RECENT_USER_ID = holder.userID;
                int userFans = Integer.parseInt(holder.MyFollow_userFans.getText().toString());
                user.setFennum(userFans);
                visitFollower(user,holder.context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    /**
     * 拜访关注人方法
     */
    private void visitFollower(User user,Context context){
        Intent intent = new Intent(context, MySpaceActivity.class);
        intent.putExtra("user",user);
        context.startActivity(intent);
    }

    /**
     * 取消关注方法
     */
    private void cancelFollow(int userID){
        myFollowPresenter.cancelFollower(userID);
    }

    /**
     * 添加关注方法
     */
    private void addFollow(int followerID){
        myFollowPresenter.addFollower(followerID);
    }

    /**
     * 删除回调方法
     */
    @Override
    public void cancelSuccessful(int followerID) {
        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getId() == followerID){
                userList.get(i).setFollow(true);
                viewHolder.myFollow_cancel.setText("关注+");
                viewHolder.loadingDrawable.stop();
                viewHolder.anim.setVisibility(View.INVISIBLE);
                viewHolder.myFollow_cancel.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
                MsgEvent msgEvent = new MsgEvent();
                msgEvent.setId(followerID);
                msgEvent.setType("follow");
                msgEvent.setValue(true);
                EventBus.getDefault().postSticky(msgEvent);
                Toast.makeText(mContext,"偶然：取消关注成功",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void cancelFailed() {
        viewHolder.loadingDrawable.stop();
        viewHolder.anim.setVisibility(View.INVISIBLE);
        viewHolder.myFollow_cancel.setVisibility(View.VISIBLE);
        Toast.makeText(mContext,"偶然：取消关注失败",Toast.LENGTH_SHORT).show();
    }


    /**
     * 添加回调方法
     */
    @Override
    public void addSuccessful(int followerID) {
        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getId() == followerID){
                userList.get(i).setFollow(false);
                viewHolder.myFollow_cancel.setText("已关注");
                viewHolder.loadingDrawable.stop();
                viewHolder.anim.setVisibility(View.INVISIBLE);
                viewHolder.myFollow_cancel.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
                MsgEvent msgEvent = new MsgEvent();
                msgEvent.setId(followerID);
                msgEvent.setType("follow");
                msgEvent.setValue(false);
                EventBus.getDefault().postSticky(msgEvent);
                Toast.makeText(mContext,"偶然：添加关注成功",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void addFailed() {
        viewHolder.loadingDrawable.stop();
        viewHolder.anim.setVisibility(View.INVISIBLE);
        viewHolder.myFollow_cancel.setVisibility(View.VISIBLE);
        Toast.makeText(mContext,"偶然：添加关注失败",Toast.LENGTH_SHORT).show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
        myFollow_userName,      //关注人用户名
        myFollow_userSign,      //关注人签名
        MyFollow_userFans;      //关注人粉丝数
        Button
                myFollow_cancel;        //取消关注
        CircleImageView
                myFollow_img;           //关注人头像
        Context context;//
        int userID = 0;
        ImageView anim;
        private AnimationDrawable loadingDrawable;

        public ViewHolder(View view,Context context){
            super(view);
            this.context = context;
            findView(view);
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
    }
}

