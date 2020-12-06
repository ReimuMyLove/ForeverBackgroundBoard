package com.example.shoujiedemo.home.follow.model;

import android.util.Log;

import com.example.shoujiedemo.apiInterface.ApiInterFace;
import com.example.shoujiedemo.home.follow.presenter.MyFollowOperationPresenterListener;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFollowOperateModelImpl implements MyFollowOperateModel {


    public MyFollowOperateModelImpl() {

    }

    @Override
    public void collect(final MyFollowOperationPresenterListener listener, int userId, int contentId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/wenjidetial/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.collect(userId,contentId);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            //Log.e("Song",responseBody.string());
                            String jsons = responseBody.string();
                            if(jsons.equals("true"))
                                listener.onCollectSuccess();
                            else
                                listener.onCollectError();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                        Log.e("Error",e.toString());
                    }

                    @Override
                    public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                    }
                });
    }

    @Override
    public void unCollect(final MyFollowOperationPresenterListener listener, int userId, int contentId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/wenjidetial/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.collect(userId,contentId);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            //Log.e("Song",responseBody.string());
                            String jsons = responseBody.string();
                            if(jsons.equals("true")) {
                                listener.onUnCollectSuccess();
                            }else {
                                listener.onUnCollectError();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                        Log.e("Error",e.toString());
                    }

                    @Override
                    public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                    }
                });
    }

    @Override
    public void comment(MyFollowOperationPresenterListener listener,int userId,int contentId) {
        int n = 1;
        if(n > 0)
            listener.onCommentSuccess();
        else
            listener.onCommentError();

    }

    @Override
    public void share(MyFollowOperationPresenterListener listener,int userId,int contentId) {

    }

    @Override
    public void favourite(final MyFollowOperationPresenterListener listener, int userId, int contentId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/likes/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.like(userId,contentId);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            //Log.e("Song",responseBody.string());
                            String jsons = responseBody.string();
                            if(jsons.equals("true")) {
                                listener.onFavouriteSuccess();
                            }else {
                                listener.onFavouriteError();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                        Log.e("Error",e.toString());
                    }

                    @Override
                    public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                    }
                });
    }

    @Override
    public void unFavourite(final MyFollowOperationPresenterListener listener, int userId, int contentId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/likes/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.disLike(userId,contentId);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String jsons = responseBody.string();
                            if(jsons.equals("true")) {
                                listener.onUnFavouriteSuccess();
                            }else {
                                listener.onUnFavouriteError();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                        Log.e("Error",e.toString());
                    }

                    @Override
                    public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                    }
                });

    }

    @Override
    public void unFolly(final MyFollowOperationPresenterListener listener, int userId, int followerId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/likes/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.diFfollow(userId,followerId);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String jsons = responseBody.string();
                            if(jsons.equals("true")) {
                                listener.onUnFollySuccess();
                            }else {
                                listener.onUnFollyError();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                        Log.e("Error",e.toString());
                    }

                    @Override
                    public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                    }
                });
    }

    @Override
    public void report(MyFollowOperationPresenterListener listener,int userId,int contentId) {
        listener.onReportSuccess();
    }

    @Override
    public void follow(final MyFollowOperationPresenterListener listener, int userId, int followerId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/likes/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.follow(userId,followerId);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String jsons = responseBody.string();
                            if(jsons.equals("true")) {
                                listener.onFollowSuccess();
                            }else {
                                listener.onFollowError();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                        Log.e("Error",e.toString());
                    }

                    @Override
                    public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                    }
                });
    }

    @Override
    public void loadComment(MyFollowOperationPresenterListener listener,int contentId) {
        String jsons = "{\"tuwendate\":[{\"tu_id\":18,\"musicid\":null,\"replyid\":null,\"user2id\":null,\"topic_detial_id\":null,\"id\":1,\"user1id\":2,\"text\":\"你好\",\"time\":\"2020-11-25 14:10:31\",\"likes\":0},{\"tu_id\":18,\"musicid\":null,\"replyid\":null,\"user2id\":null,\"topic_detial_id\":null,\"id\":4,\"user1id\":1,\"text\":\"好喜欢三毛\",\"time\":\"2020-11-28 10:05:27\",\"likes\":0}],\"userdate\":[{\"password\":\"wrk\",\"follownum\":3,\"backgroundpic2\":null,\"fennum\":1,\"sex\":\"男\",\"backgroundpic1\":null,\"name\":\"wrk\",\"picname\":\"wrk.jpg\",\"sign\":\"算了\",\"id\":2,\"age\":20},{\"password\":\"ljjy\",\"follownum\":0,\"backgroundpic2\":null,\"fennum\":3,\"sex\":\"男\",\"backgroundpic1\":\"1backpic01.png\",\"name\":\"ljjy\",\"picname\":\"ljjy.jpg\",\"sign\":\"冲冲冲\",\"id\":1,\"age\":22}]}\n";
        if(jsons != null)
            listener.onLoadCommentSuccess(jsons);

    }

    @Override
    public void loadSet(MyFollowOperationPresenterListener listener,int userId) {
        String jsons = "{\"name\":\"默认文集\",\"id\":2,\"pic\":null,\"userid\":2}";
        if(jsons != null) {
            listener.onLoadSetSuccess(jsons);
        }
    }
}
