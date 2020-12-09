package com.example.shoujiedemo.Log.model;

import java.util.List;

/**
 * 数组实体类
 */

public class UserData<T> extends Response {
    private List<T> userData;//返回一个数组

    public List<T> getUserData() {
        return userData;
    }
    public void setUserData(List<T> userData) {
        this.userData = userData;
    }
}