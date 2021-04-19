package com.example.shoujiedemo.fround.model;

import android.util.Log;

import com.example.shoujiedemo.apiInterface.ApiInterFace;
import com.example.shoujiedemo.fround.presenter.FroundLoadDataPresenterLisenter;
import com.example.shoujiedemo.fround.presenter.FroundOperationPresenterListener;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HeartLoadDataModelImpl implements FroundLoadDataModel{


    @Override
    public void loadContents(final FroundLoadDataPresenterLisenter listener, int typeId, int page,int userId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/tuwen/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.loadContent(typeId,page,userId);
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
                            if(jsons !=null && !jsons.equals("")) {
                                listener.onLoadContentDataSuccess(jsons);
                                Log.i("success", jsons + "");
                            }else {
                                listener.onLoadContentDataError();
                                Log.i("error", "无数据");
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
    public void search(final FroundLoadDataPresenterLisenter listener, String flag, int typeid, int page, int userId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/tuwen/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.loadSearchData(flag,userId,page,typeid);
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
                            if(jsons !=null && !jsons.equals("")) {
                                listener.onSearchDataSuccess(jsons);
                            }else {
                                listener.onSearchDataError();
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
}
