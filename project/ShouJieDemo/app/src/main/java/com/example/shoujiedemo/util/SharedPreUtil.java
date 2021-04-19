package com.example.shoujiedemo.util;

import android.content.SharedPreferences;

import com.example.shoujiedemo.activity.MyApplication;


public class SharedPreUtil {
    private SharedPreUtil() {}
    private static SharedPreUtil sharedpreutil;

    public static SharedPreUtil getInstance() {
        if (sharedpreutil == null) {
            synchronized (SharedPreUtil.class) {
                sharedpreutil = new SharedPreUtil();
            }
        }
        return sharedpreutil;
    }

    final String DARKMODEL = "darkmodel";

    public static boolean darkmodel;

    private SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences("them", 0);
    }

    public void load() {
        SharedPreferences prefer = getSharedPreferences();

        darkmodel = prefer.getBoolean(DARKMODEL, darkmodel);
    }

    public void save() {
        SharedPreferences prefer = getSharedPreferences();
        SharedPreferences.Editor editor = prefer.edit();

        editor.putBoolean(DARKMODEL, darkmodel);
    }

    public void clear() {
        SharedPreferences prefer = getSharedPreferences();
        SharedPreferences.Editor editor = prefer.edit();

        editor.remove(DARKMODEL);
    }
}
