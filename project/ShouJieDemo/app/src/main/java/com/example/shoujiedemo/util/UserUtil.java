package com.example.shoujiedemo.util;

import com.example.shoujiedemo.entity.Set;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {

    public static int USER_ID = 8;             //用户ID
    public static String USER_NAME = "用户名";   //用户名
    public static String USER_SEX = "无";       //用户性别
    public static int USER_AGE = 0;             //用户年龄
    public static String USER_SIGN;             //用户个性签名(弃用 个性签名太长 转入本地数据库)
    public static String USER_IMG = "";         //用户头像
    public static String USER_BACKGROUND = "";  //用户背景图
    public static int USER_FOLLOW = 0;          //用户关注数
    public static int USER_FANS = 0;            //用户粉丝数目

    public static int RECENT_USER_ID = 0;       //当前适用用户ID(跳转)

    public static String SET_JSON = "";         //文集JSON字符串

    public static List<Set> sets = new ArrayList<>();

}
