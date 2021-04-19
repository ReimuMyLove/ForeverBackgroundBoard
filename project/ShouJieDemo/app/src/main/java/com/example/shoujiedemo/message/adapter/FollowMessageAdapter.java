package com.example.shoujiedemo.message.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.FollowBean;
import com.example.shoujiedemo.util.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class FollowMessageAdapter extends BaseAdapter {

    private List<FollowBean> followBeans = new ArrayList<>();
    private Context context;

    public FollowMessageAdapter(List<FollowBean> followBeans, Context context) {
        this.followBeans = followBeans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return followBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return followBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_message_follow,viewGroup,false);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
            inindata(view,viewHolder,i);
        }else
            viewHolder = (ViewHolder)view.getTag();
            inindata(view,viewHolder,i);
        return view;
    }

    private void inindata(View view1, ViewHolder viewHolder, int i) {
        viewHolder.head=view1.findViewById(R.id.item_message_follow_user_head);
        Glide.with(context).load(ConfigUtil.BASE_HEAD_URL+followBeans.get(i).getUser().getPicname()).into(viewHolder.head);
        Log.e("lll",ConfigUtil.BASE_HEAD_URL+followBeans.get(i).getUser().getPicname());
        viewHolder.userName=view1.findViewById(R.id.item_message_follow_user_name);
        viewHolder.userName.setText(followBeans.get(i).getUser()
        .getName());
        viewHolder.time=view1.findViewById(R.id.item_message_follow_time);
        viewHolder.time.setText(followBeans.get(i).getTime());
    }

    class ViewHolder{
        ImageView head;//头像
        TextView userName;//关注你的用户名
        TextView time;//关注日期
    }
}
