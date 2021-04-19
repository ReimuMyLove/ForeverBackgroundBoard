package com.example.shoujiedemo.util;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.activity.MyApplication;

public class ThemeResUtil {
    static boolean darkModel = false;

    public static void setModel(boolean isdarkmodel) {
        darkModel = isdarkmodel;
    }

    public static boolean isDarkModel() {
        return darkModel;
    }

    public static int getColorPrimary() {
        if(darkModel) return MyApplication.getContext().getResources().getColor(R.color.TabandBottomWhite);
        else return MyApplication.getContext().getResources().getColor(R.color.darkTabandBottomWhite);
    }

    public static int getColorPrimaryDark() {
        if(darkModel) return MyApplication.getContext().getResources().getColor(R.color.darkTabandBottomWhite);
        else return MyApplication.getContext().getResources().getColor(R.color.TabandBottomWhite);
    }

    public static int getText_color() {
        if(darkModel) return MyApplication.getContext().getResources().getColor(R.color.darkTitleTextBlack);
        else return MyApplication.getContext().getResources().getColor(R.color.darkarTicleLightBlack);
    }

   /* public static int getIcon_res() {
        if(darkModel) return R.drawable.ic_chevron_right_black_24dp;
        else return R.drawable.ic_chevron_right_white_24dp;
    }*/
}
