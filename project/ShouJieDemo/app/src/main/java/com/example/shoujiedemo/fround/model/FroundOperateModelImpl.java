package com.example.shoujiedemo.fround.model;

import com.example.shoujiedemo.fround.presenter.FroundOperationPresenterListener;

public class FroundOperateModelImpl implements FroundOperateModel {


    public FroundOperateModelImpl() {

    }

    @Override
    public void collect(FroundOperationPresenterListener listener) {
        int n = 1;
        if(n>0)
            listener.onCollectSuccess();
        else
            listener.onCollectError();
    }

    @Override
    public void unCollect(FroundOperationPresenterListener listener) {
        int n = 1;
        if(n>0)
            listener.onUnCollectSuccess();
        else
            listener.onUnCollectError();
    }

    @Override
    public void comment(FroundOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onCommentSuccess();
        else
            listener.onCommentError();

    }

    @Override
    public void share(FroundOperationPresenterListener listener) {

    }

    @Override
    public void favourite(FroundOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onFavouriteSuccess();
        else
            listener.onFavouriteError();
    }

    @Override
    public void unFavourite(FroundOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onUnFavouriteSuccess();
        else
            listener.onUnFavouriteError();

    }

    @Override
    public void unFolly(FroundOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onUnFollySuccess();
        else
            listener.onUnFollyError();
    }

    @Override
    public void report(FroundOperationPresenterListener listener) {
        listener.onReportSuccess();
    }

    @Override
    public void follow(FroundOperationPresenterListener listener) {
        int n = 1;
        if(n > 0)
            listener.onFollowSuccess();
        else
            listener.onFollowError();
    }

    @Override
    public void loadComment(FroundOperationPresenterListener listener) {
        String jsons = "{\"tuwendate\":[{\"tu_id\":18,\"musicid\":null,\"replyid\":null,\"user2id\":null,\"topic_detial_id\":null,\"id\":1,\"user1id\":2,\"text\":\"你好\",\"time\":\"2020-11-25 14:10:31\",\"likes\":0},{\"tu_id\":18,\"musicid\":null,\"replyid\":null,\"user2id\":null,\"topic_detial_id\":null,\"id\":4,\"user1id\":1,\"text\":\"好喜欢三毛\",\"time\":\"2020-11-28 10:05:27\",\"likes\":0}],\"userdate\":[{\"password\":\"wrk\",\"follownum\":3,\"backgroundpic2\":null,\"fennum\":1,\"sex\":\"男\",\"backgroundpic1\":null,\"name\":\"wrk\",\"picname\":\"wrk.jpg\",\"sign\":\"算了\",\"id\":2,\"age\":20},{\"password\":\"ljjy\",\"follownum\":0,\"backgroundpic2\":null,\"fennum\":3,\"sex\":\"男\",\"backgroundpic1\":\"1backpic01.png\",\"name\":\"ljjy\",\"picname\":\"ljjy.jpg\",\"sign\":\"冲冲冲\",\"id\":1,\"age\":22}]}\n";
        if(jsons != null)
            listener.onLoadCommentSuccess(jsons);

    }



}
