package com.example.shoujiedemo.myCenter.myCenter.view.fragment.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.util.BaseActivity;
import com.example.shoujiedemo.util.DBHelper;
import com.example.shoujiedemo.util.UserUtil;

public class UserInfoActivity extends BaseActivity {
    Button
            myCenter_PersonalInfo_return;
    TextView
            myCenter_PersonalInfo_ID,           //用户ID
            myCenter_PersonalInfo_userName,     //用户名
            myCenter_PersonalInfo_userSex,      //用户性别
            myCenter_PersonalInfo_userAge,      //用户年龄
            myCenter_PersonalInfo_userSign;     //用户签名

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        //获取view方法
        FindView();
        //绑定监听器
        SetListener();
        //获取数据
        setInfo();
    }

    /**
     * 获取view方法
     */
    public void FindView() {
        myCenter_PersonalInfo_return = findViewById(R.id.myCenter_PersonalInfo_return);
        myCenter_PersonalInfo_ID = findViewById(R.id.myCenter_PersonalInfo_ID);
        myCenter_PersonalInfo_userName = findViewById(R.id.myCenter_PersonalInfo_userName);
        myCenter_PersonalInfo_userSex = findViewById(R.id.myCenter_PersonalInfo_userSex);
        myCenter_PersonalInfo_userAge = findViewById(R.id.myCenter_PersonalInfo_userAge);
        myCenter_PersonalInfo_userSign = findViewById(R.id.myCenter_PersonalInfo_userSign);
    }

    /**
     * 设置监听器方法
     */
    public void SetListener() {
        MyListener listener = new MyListener();
        myCenter_PersonalInfo_return.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.myCenter_PersonalInfo_return) {
                onBackPressed();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setInfo(){
        myCenter_PersonalInfo_ID.setText(UserUtil.USER_ID+"");
        myCenter_PersonalInfo_userName.setText(UserUtil.USER_NAME);
        myCenter_PersonalInfo_userSex.setText(UserUtil.USER_SEX);
        myCenter_PersonalInfo_userAge.setText(UserUtil.USER_AGE+"");
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("userInfo",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String sign = cursor.getString(cursor.getColumnIndex("sign"));
                if (!sign.equals("")){
                    myCenter_PersonalInfo_userSign.setText(sign);
                }
            }while(cursor.moveToNext());
        }
        db.close();
    }
}
