package com.example.shoujiedemo.myCenter.myCenter.model;

import android.net.Uri;
import android.util.Log;

import com.example.shoujiedemo.apiInterface.ApiInterFace;
import com.example.shoujiedemo.myCenter.myCenter.presenter.ChangeImagePresenterListener;
import com.example.shoujiedemo.util.UserUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeImageModelImpl implements ChangeImageModel{
    @Override
    public void changeImage(final ChangeImagePresenterListener listener, int userid, Uri uri) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert file != null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.changeuserimage(userid,body);

        try {
            observable.subscribeOn(Schedulers.io())//在io线程中请求
                    .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                    .subscribe(new Observer<ResponseBody>()
                    {
                        @Override
                        public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String jsons = responseBody.string();
                                if (jsons != null && !jsons.equals("")) {
                                    UserUtil.USER_IMG = jsons;
                                    listener.loadSuccess();
                                } else {
                                    listener.loadError();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                            Log.e("Error", e.toString());
                        }

                        @Override
                        public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadWenji(final ChangeImagePresenterListener listener, int userid, Uri uri, String name) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert file != null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/wenji/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Log.e("wrk", file + "" + body);
        Observable<ResponseBody> observable = apiInterface.uploadwenji(userid,name,1,body);

        try {
            observable.subscribeOn(Schedulers.io())//在io线程中请求
                    .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                    .subscribe(new Observer<ResponseBody>()
                    {
                        @Override
                        public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                //Log.e("Song",responseBody.string());
                                String jsons = responseBody.string();
                                if (jsons != null && !jsons.equals("")) {
                                    listener.loadSuccess();
                                } else {
                                    listener.loadError();
                                    Log.i("error", "无数据");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                            Log.e("Error", e.toString());
                        }

                        @Override
                        public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeBackGround(final ChangeImagePresenterListener listener, int userid, Uri uri) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert file != null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Log.e("wrk", file + "" + body);
        Observable<ResponseBody> observable = apiInterface.changeback1image(userid,body);

        try {
            observable.subscribeOn(Schedulers.io())//在io线程中请求
                    .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                    .subscribe(new Observer<ResponseBody>()
                    {
                        @Override
                        public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                //Log.e("Song",responseBody.string());
                                String jsons = responseBody.string();
                                if (jsons != null && !jsons.equals("")) {
                                    UserUtil.USER_SPACE_BACKGROUND = jsons;
                                    UserUtil.USER_CENTER_BACKGROUND = jsons;
                                    listener.loadSuccess();
                                } else {
                                    listener.loadError();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {//事件队列发生异常的时候调用，事件队列终止，observable不再发送队列
                            Log.e("Error", e.toString());
                        }

                        @Override
                        public void onComplete() {//事件队列完结的时候调用，rxjava把事件看成一个队列

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
