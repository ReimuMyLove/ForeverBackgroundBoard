package com.example.shoujiedemo.myCenter.setting.model;

import android.util.Log;

import com.example.shoujiedemo.apiInterface.ApiInterFace;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.setting.presenter.impl.SettingPresenterListener;
import com.example.shoujiedemo.util.UserUtil;
import com.google.gson.Gson;
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

public class ChangePasswordModelImpl implements ChangePasswordModel{
    private static final String BASE_URL = "http://49.232.217.140:8080/OuranServices/user/";

    /**
     * 修改密码
     */
    @Override
    public void changeKey(int userID, String oldKey, String newKey, final SettingPresenterListener listener) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.changeKey(userID,oldKey,newKey);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String keyState = responseBody.string();
                            if (keyState.equals("true")){
                                listener.changeKeySuccessful();
                            }else if(keyState.equals("false")){
                                listener.changeKeyFailed();
                            }else if(keyState.equals("keyError")){
                                listener.changeKeyError();
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
    public void destroyAccountEnter(int userID, final SettingPresenterListener listener) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.destroyAccountEnter(userID);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String keyState = responseBody.string();
                            if (keyState.equals("true")){
                                listener.destroyAccountSuccessful();
                            }else {
                                listener.destroyAccountFailed();
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
