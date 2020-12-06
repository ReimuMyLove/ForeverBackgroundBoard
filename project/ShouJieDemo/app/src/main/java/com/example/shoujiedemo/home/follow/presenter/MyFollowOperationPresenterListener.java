package com.example.shoujiedemo.home.follow.presenter;

public interface MyFollowOperationPresenterListener {

    void onCollectSuccess();

    void onCollectError();

    void onCommentSuccess();

    void onCommentError();

    void onShareSuccess();

    void onFavouriteSuccess();

    void onFavouriteError();

    void onUnFollySuccess();

    void onUnFollyError();

    void onFollowSuccess();

    void onFollowError();

    void onReportSuccess();

    void onUnFavouriteError();

    void onUnFavouriteSuccess();

    void onUnCollectSuccess();

    void onUnCollectError();

    void onLoadCommentSuccess(String jsons);

    void onLoadSetSuccess(String jsons);

}
