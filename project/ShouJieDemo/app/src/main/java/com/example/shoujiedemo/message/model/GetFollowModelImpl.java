package com.example.shoujiedemo.message.model;

import android.util.Log;

import com.example.shoujiedemo.apiInterface.ApiInterFace;
import com.example.shoujiedemo.message.presenter.GetFollowPresenterListener;
import com.example.shoujiedemo.message.presenter.GetmessagePresenterListener;
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

public class GetFollowModelImpl implements GetFollowModel {

    @Override
    public void getFollow(final GetFollowPresenterListener listener, int userid) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.232.217.140:8080/OuranServices/follow/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.getFens(userid);
        try {
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
                                if (jsons != null && !jsons.equals("")) {
                                    listener.LoadSuccess(jsons);
                                    Log.e("wrk", jsons.toString());
                                    listener.LoadSuccess(jsons);
                                } else {
                                    listener.LoadFail(jsons);
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
}
