package com.example.shoujiedemo.myCenter.setting.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.shoujiedemo.Log.activity.LoginActivity;
import com.example.shoujiedemo.myCenter.setting.model.ChangePasswordModel;
import com.example.shoujiedemo.myCenter.setting.model.ChangePasswordModelImpl;
import com.example.shoujiedemo.myCenter.setting.service.CookiesClear;
import com.example.shoujiedemo.myCenter.setting.view.activity.settingActivity.safeActivity.ChangePasswordActivity;
import com.example.shoujiedemo.myCenter.setting.view.inter.ChangePasswordView;
import com.example.shoujiedemo.myCenter.setting.view.inter.DestroyAccountView;
import com.example.shoujiedemo.util.DBHelper;
import com.example.shoujiedemo.util.UserUtil;

public class SettingPresenter implements SettingPresenterListener{
    CookiesClear cookiesClear = new CookiesClear();

    private ChangePasswordView changePassword;
    private ChangePasswordModel changePasswordModel;
    private DestroyAccountView destroyAccount;

    public SettingPresenter(ChangePasswordView changePassword){
        this.changePassword = changePassword;
        this.changePasswordModel = new ChangePasswordModelImpl();
    }

    public SettingPresenter(DestroyAccountView destroyAccount){
        this.destroyAccount = destroyAccount;
        this.changePasswordModel = new ChangePasswordModelImpl();
    }

    public SettingPresenter(){

    }

    /**
     * 逻辑实现方法
     */
    public void Logout(Context context){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "DELETE FROM userInfo ";
        db.execSQL(sql);
        db.close();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void ClearCookie(Context context) throws Exception {
        cookiesClear.clearAllCache(context);
    }

    /**
     * 修改密码
     */
    public void changeKey(String oldKey, String newKey, String repeatKey) {
        if (newKey != repeatKey){
            changePassword.keyRepeat();
        }else{
            int userID = UserUtil.USER_ID;
            changePasswordModel.changeKey(userID,oldKey,newKey,this);
        }
    }

    /**
     * 修改密码回调方法
     */
    @Override
    public void changeKeySuccessful() {
        changePassword.changeKeySuccessful();
    }

    @Override
    public void changeKeyFailed() {
        changePassword.changeKeyFailed();
    }

    @Override
    public void changeKeyError() {
        changePassword.changeKeyError();
    }

    /**
     * 销毁账户方法
     */

    public void destroyAccountEnter(int userID) {
        changePasswordModel.destroyAccountEnter(userID,this);
    }

    /**
     * 销毁账户回调方法
     */
    @Override
    public void destroyAccountSuccessful() {
        destroyAccount.destroyAccountSuccessful();
    }

    @Override
    public void destroyAccountFailed() {
        destroyAccount.destroyAccountFailed();
    }
}
