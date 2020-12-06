package com.example.shoujiedemo.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

    @Override
    public void onBackPressed() {
        if(!HandleBackUtil.handleBackPress(this)){
            super.onBackPressed();
        }
    }
}
