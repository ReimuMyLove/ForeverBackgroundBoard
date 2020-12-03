package com.example.shoujiedemo.util;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if(!HandleBackUtil.handleBackPress(this)){
            super.onBackPressed();
        }
    }
}
