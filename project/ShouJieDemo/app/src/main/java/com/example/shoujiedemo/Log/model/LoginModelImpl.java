package com.example.shoujiedemo.Log.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.shoujiedemo.Log.presenter.LoginPresenterListener;
import com.example.shoujiedemo.apiInterface.ApiInterFace;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.util.DBHelper;
import com.example.shoujiedemo.util.UserUtil;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.text.MessageFormat;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginModelImpl implements LoginModel{
    private static final String BASE_URL = "http://49.232.217.140:8080/OuranServices/user/";
    @Override
    public void login(String name, String password, final Context context, final LoginPresenterListener listener) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava适配器，绑定RxJava
                .build();
        ApiInterFace apiInterface = retrofit.create(ApiInterFace.class);
        Observable<ResponseBody> observable = apiInterface.login(name,password);
        observable.subscribeOn(Schedulers.io())//在io线程中请求
                .observeOn(AndroidSchedulers.mainThread())//返回在主线程中执行
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {//参数是Disposable，用于解除队列

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String userInfo = responseBody.string();
                            if(!userInfo.equals("false")){
                                Gson gson = new Gson();
                                User user;
                                user = gson.fromJson(userInfo,User.class);
                                UserUtil.USER_NAME = user.getName();
                                UserUtil.USER_ID = user.getId();
                                UserUtil.USER_FANS = user.getFennum();
                                UserUtil.RECENT_USER_ID = user.getId();
                                UserUtil.USER_AGE = user.getAge();
                                UserUtil.USER_SEX = user.getSex();
                                UserUtil.USER_SIGN = user.getSign();
                                if (user.getPicname()!=null){
                                    UserUtil.USER_IMG = user.getPicname();
                                }
                                if (user.getBackgroundpic1()!=null){
                                    UserUtil.USER_CENTER_BACKGROUND = user.getBackgroundpic1();
                                }
                                if (user.getBackgroundpic2()!=null){
                                    UserUtil.USER_SPACE_BACKGROUND = user.getBackgroundpic2();
                                }
                                userInfoSet(user,context);
                                listener.OnLoginSuccessful();
                            }else{
                                listener.OnLoginFailed();
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

    /**
     * 将用户数据装入本地Database
     * @param user 用户数据
     * @param context 当前上下文
     */
    private void userInfoSet(User user, Context context){
        /**
         * 登录前清空当前用户数据表中的全部内容 以填写新的内容
         */
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from UserInfo";
        db.execSQL(sql);
        /**
         * 内容清空完成 开始写入新的用户信息
         */
        int ID = user.getId();
        String name = user.getName();
        String password = user.getPassword();
        String picname = user.getPicname();
        int age = user.getAge();
        String sex = user.getSex();
        String sign = user.getSign();
        int fennum = user.getFennum();
        int follownum = user.getFollownum();
        //编写sql语句
        String sql1 =
                MessageFormat.format("insert into userInfo(userID,userName,userPassword,userSex,picName,userAge,userFans,userFollow,userSign)values(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''{6}'',''{7}'',''{8}'')", ID, name, password, sex, picname, age, fennum, follownum, sign);
        db.execSQL(sql);
        db.close();
    }
}
