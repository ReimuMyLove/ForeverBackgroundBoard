package com.example.shoujiedemo.myCenter.mySpace.presenter;

public interface MySpacePresenterListener {
    void deleteSuccessful();
    void deleteFailed();


    void setGroup(String jsons);
    void getGroupFailed();


    void addGroupSuccessful(String jsons);
    void addGroupFailed();


    void addFollowFailed();
    void addFollowSuccessful();


    void getOwnerInfoFailed();
    void getOwnerInfoSuccessful(String jsons);


    void getGroupDetailFailed();
    void getGroupDetailSuccessful(String jsons);

    void getMusicFailed();
    void getMusicSuccessful(String jsons);
}
