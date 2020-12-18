package com.example.shoujiedemo.message.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.FollowBean;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.message.adapter.FollowMessageAdapter;
import com.example.shoujiedemo.message.presenter.GetFollowPresenerImpl;
import com.example.shoujiedemo.message.presenter.GetFollowPresenter;
import com.example.shoujiedemo.message.view.MessageView;
import com.example.shoujiedemo.util.UserUtil;

import java.util.ArrayList;
import java.util.List;


public class FollowMessageFragment extends Fragment implements MessageView {

    private ListView followMessageView;
    private GetFollowPresenter presenter;
    public FollowMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow_message, container, false);
        List<FollowBean> followBeans = new ArrayList<>();
        followBeans.add(new FollowBean());
        followBeans.add(new FollowBean());
        followBeans.add(new FollowBean());
        followMessageView = view.findViewById(R.id.follow_list_message_view);
        initdata();

        return view;
    }

    private void initdata() {
        presenter=new GetFollowPresenerImpl(this);
        presenter.getFollow(UserUtil.USER_ID);
    }

    @Override
    public void Showmessage(List<Comment> comments, List<User> users, List<Content> contents, List<LikeBean> likes) {

    }

    @Override
    public void Showfollow(List<User> users, List<FollowBean> followBeans) {
        for (User user:users){
            for (FollowBean followBean:followBeans){
                if (user.getId()==followBean.getUserid()){
                    followBean.setUser(user);
                }
            }
        }
        FollowMessageAdapter followMessageAdapter = new FollowMessageAdapter(followBeans,getActivity());
        followMessageView.setAdapter(followMessageAdapter);
    }
}