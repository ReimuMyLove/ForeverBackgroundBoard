package com.example.shoujiedemo.message.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoujiedemo.R;


/**
 * 消息页面
 */
public class MessageFragment extends Fragment  {

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Intent intent =new Intent();
//        intent.setClass(getActivity(),MessageActivity.class);
//        startActivity(intent);
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

}
