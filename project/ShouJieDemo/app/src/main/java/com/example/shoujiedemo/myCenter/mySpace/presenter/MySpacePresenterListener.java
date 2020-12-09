package com.example.shoujiedemo.myCenter.mySpace.presenter;

public interface MySpacePresenterListener {
    void deleteSuccessful();
    void deleteFailed();

    void setGroup(String jsons);
    void getGroupFailed();

    void addGroupSuccessful(String jsons);
    void addGroupFailed();
}
