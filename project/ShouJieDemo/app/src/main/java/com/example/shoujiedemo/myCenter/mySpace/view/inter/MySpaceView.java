package com.example.shoujiedemo.myCenter.mySpace.view.inter;

import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.entity.User;

import java.util.List;

public interface MySpaceView {
    void addGroupFailed();

    void addGroupSuccessful(Set set);

    void addFollowFailed();

    void addFollowSuccessful();

    void getOwnerInfoFailed();

    void getOwnerInfoSuccessful(User userInfo);
}
