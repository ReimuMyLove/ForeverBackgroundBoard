package com.example.shoujiedemo.home.follow.model;

import com.example.shoujiedemo.home.follow.presenter.MyFollowOperationPresenterListener;


import java.util.List;

public class MyFollowOperateModelImpl implements MyFollowOperateModel {


    public MyFollowOperateModelImpl() {

    }

    @Override
    public void collect(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n>0)
            listener.onCollectSuccess();
        else
            listener.onCollectError();
    }

    @Override
    public void unCollect(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n>0)
            listener.onUnCollectSuccess();
        else
            listener.onUnCollectError();
    }

    @Override
    public void comment(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onCommentSuccess();
        else
            listener.onCommentError();

    }

    @Override
    public void share(MyFollowOperationPresenterListener listener) {

    }

    @Override
    public void favourite(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onFavouriteSuccess();
        else
            listener.onFavouriteError();
    }

    @Override
    public void unFavourite(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onUnFavouriteSuccess();
        else
            listener.onUnFavouriteError();

    }

    @Override
    public void unFolly(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onUnFollySuccess();
        else
            listener.onUnFollyError();
    }

    @Override
    public void report(MyFollowOperationPresenterListener listener) {
        listener.onReportSuccess();
    }

    @Override
    public void follow(MyFollowOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onFollowSuccess();
        else
            listener.onFollowError();
    }

    @Override
    public void loadComment(MyFollowOperationPresenterListener listener) {
        String jsons = "{\"tuwendate\":[{\"tu_id\":18,\"musicid\":null,\"replyid\":null,\"user2id\":null,\"topic_detial_id\":null,\"id\":1,\"user1id\":2,\"text\":\"你好\",\"time\":\"2020-11-25 14:10:31\",\"likes\":0},{\"tu_id\":18,\"musicid\":null,\"replyid\":null,\"user2id\":null,\"topic_detial_id\":null,\"id\":4,\"user1id\":1,\"text\":\"好喜欢三毛\",\"time\":\"2020-11-28 10:05:27\",\"likes\":0}],\"userdate\":[{\"password\":\"wrk\",\"follownum\":3,\"backgroundpic2\":null,\"fennum\":1,\"sex\":\"男\",\"backgroundpic1\":null,\"name\":\"wrk\",\"picname\":\"wrk.jpg\",\"sign\":\"算了\",\"id\":2,\"age\":20},{\"password\":\"ljjy\",\"follownum\":0,\"backgroundpic2\":null,\"fennum\":3,\"sex\":\"男\",\"backgroundpic1\":\"1backpic01.png\",\"name\":\"ljjy\",\"picname\":\"ljjy.jpg\",\"sign\":\"冲冲冲\",\"id\":1,\"age\":22}]}\n";
        if(jsons != null)
            listener.onLoadCommentSuccess(jsons);

    }

    @Override
    public void loadSet(MyFollowOperationPresenterListener listener) {
        listener.onLoadSetSuccess();
    }
}
