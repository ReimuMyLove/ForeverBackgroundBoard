package com.example.shoujiedemo.message.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.shoujiedemo.message.adapter.CommentMessageAdapter;
import com.example.shoujiedemo.message.presenter.GetMessagePresenter;
import com.example.shoujiedemo.message.presenter.GetMessagePresenterImpl;
import com.example.shoujiedemo.message.view.MessageView;
import com.example.shoujiedemo.util.UserUtil;

import java.util.ArrayList;
import java.util.List;


public class CommentMessageFragment extends Fragment implements MessageView {

    private ListView commentMessageView;
    private GetMessagePresenter presenter;
    public CommentMessageFragment() {
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
        View view = inflater.inflate(R.layout.fragment_comment_message, container, false);
        commentMessageView = view.findViewById(R.id.comment_list__message_view);
        initdata();



        return view;
    }

    private void initdata() {
        presenter=new GetMessagePresenterImpl(this);
        presenter.GetMessage(UserUtil.USER_ID);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment());
        commentList.add(new Comment());
        commentList.add(new Comment());
        commentList.add(new Comment());

    }

    @Override
    public void Showmessage(List<Comment> comments, List<User> users, List<Content> contents, List<LikeBean> likes) {
        CommentMessageAdapter commentMessageAdapter = new CommentMessageAdapter(comments,getActivity(),contents,users.get(users.size()-1),likes);
        for(Comment comment:comments){
            for (User user :users){
                if(user.getId()==comment.getUser1id()){
                    comment.setUser(user);
                }
            }
        }
        commentMessageView.setAdapter(commentMessageAdapter);
    }

    @Override
    public void Showfollow(List<User> users, List<FollowBean> followBeans) {

    }
}