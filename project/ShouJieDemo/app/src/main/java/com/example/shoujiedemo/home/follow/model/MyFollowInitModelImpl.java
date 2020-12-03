package com.example.shoujiedemo.home.follow.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.home.follow.presenter.MyFollowInitPresenterListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyFollowInitModelImpl implements MyFollowInitModel{


    /**
     * 从服务端加载数据
     * @param listener
     */
    @Override
    public void loadContents(MyFollowInitPresenterListener listener) {
        String jsons = "{\"date2\":[{\"password\":\"wrk\",\"follownum\":3,\"backgroundpic2\":null,\"fennum\":1,\"sex\":\"男\",\"backgroundpic1\":null,\"name\":\"wrk\",\"picname\":\"wrk.jpg\",\"sign\":\"算了\",\"id\":2,\"age\":20},{\"password\":\"xs\",\"follownum\":3,\"backgroundpic2\":null,\"fennum\":2,\"sex\":\"男\",\"backgroundpic1\":null,\"name\":\"xs\",\"picname\":\"xs.jpg\",\"sign\":\"冲\",\"id\":3,\"age\":20},{\"password\":\"ljjy\",\"follownum\":0,\"backgroundpic2\":null,\"fennum\":3,\"sex\":\"男\",\"backgroundpic1\":\"1backpic01.png\",\"name\":\"ljjy\",\"picname\":\"ljjy.jpg\",\"sign\":\"冲冲冲\",\"id\":1,\"age\":22}],\"date1\":[{\"collectnum\":1,\"pic\":null,\"title\":\"如果有来生\",\"userid\":2,\"cheatnum\":3,\"isoriginal\":0,\"forwardnum\":4,\"typeid\":3,\"id\":18,\"writer\":\"三毛\",\"time\":\"2020-11-28 12:14:46\",\"text\":\"如果有来生，要做一棵树，\\r\\n站成永恒。没有悲欢的姿势，\\r\\n一半在尘土里安详，\\r\\n一半在风里飞扬；\\r\\n一半洒落荫凉，\\r\\n一半沐浴阳光。\\r\\n非常沉默、非常骄傲。\\r\\n从不依靠、从不寻找。\\r\\n如果有来生，要化成一阵风，\\r\\n一瞬间也能成为永恒。\\r\\n没有善感的情怀，没有多情的眼睛。\\r\\n一半在雨里洒脱，\\r\\n一半在春光里旅行；\\r\\n寂寞了，孤自去远行，\\r\\n把淡淡的思念统统带走，\\r\\n从不思念、从不爱恋；\\r\\n如果有来生，要做一只鸟，\\r\\n飞越永恒，没有迷途的苦恼。\\r\\n东方有火红的希望，\\r\\n南方有温暖的巢床，\\r\\n向西逐退残阳，向北唤醒芬芳。\\r\\n如果有来生，\\r\\n希望每次相遇，\\r\\n都能化为永恒。\\t\\t\",\"tag\":\"三毛诗选\",\"likes\":2},{\"collectnum\":0,\"pic\":null,\"title\":\"沙漠\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":20,\"writer\":\"三毛\",\"time\":\"2020-11-26 11:34:28\",\"text\":\"前世的乡愁 ? ?铺展在眼前\\r\\n\\r\\n啊 ? 黄沙万丈的布\\r\\n\\r\\n当我 ?当我\\r\\n\\r\\n被这天地玄黄牢牢捆住\\r\\n\\r\\n漂流的心 ? ?在这里慢慢\\r\\n\\r\\n慢慢一同落尘\\r\\n\\r\\n呼啸长空的风\\r\\n\\r\\n卷去了不回的路\\r\\n\\r\\n大地就这么交出了它的秘密\\r\\n\\r\\n那时 ? 沙漠便不再只是沙漠\\r\\n\\r\\n沙漠化为一口水井 ?井里面\\r\\n\\r\\n一双水的眼睛\\r\\n\\r\\n荡出一抹微笑\",\"tag\":\"三毛诗选\",\"likes\":0},{\"collectnum\":0,\"pic\":null,\"title\":\"橄榄树\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":19,\"writer\":\"三毛\",\"time\":\"2020-11-26 11:34:08\",\"text\":\"不要问我从bai哪里来du\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪zhi\\r\\n\\r\\n流浪远方\\r\\n\\r\\n流浪\\r\\n\\r\\n为了dao天空飞翔的小鸟\\r\\n\\r\\n为了山间轻流的小溪\\r\\n\\r\\n为了宽阔的草原\\r\\n\\r\\n流浪远方 ?流浪\\r\\n\\r\\n还有 ?还有\\r\\n\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n为什么流浪远方\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n流浪远方\",\"tag\":\"三毛诗选\",\"likes\":0},{\"collectnum\":0,\"pic\":null,\"title\":\"橄榄树\",\"userid\":3,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":0,\"id\":21,\"writer\":\"三毛\",\"time\":\"2020-11-28 16:38:20\",\"text\":\"不要问我从bai哪里来du\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪zhi\\r\\n\\r\\n流浪远方\\r\\n\\r\\n流浪\\r\\n\\r\\n为了dao天空飞翔的小鸟\\r\\n\\r\\n为了山间轻流的小溪\\r\\n\\r\\n为了宽阔的草原\\r\\n\\r\\n流浪远方 ?流浪\\r\\n\\r\\n还有 ?还有\\r\\n\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n为什么流浪远方\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n流浪远方\",\"tag\":null,\"likes\":0},{\"collectnum\":0,\"pic\":null,\"title\":\"日记01\",\"userid\":1,\"cheatnum\":0,\"isoriginal\":1,\"forwardnum\":0,\"typeid\":2,\"id\":22,\"writer\":\"李炬宇\",\"time\":\"2020-11-27 17:06:13\",\"text\":\"啊好累啊，我死了\\t\\t\",\"tag\":null,\"likes\":0},{\"collectnum\":0,\"pic\":null,\"title\":\"爱你就像爱生命\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":23,\"writer\":\"王小波\",\"time\":\"2020-11-27 18:04:18\",\"text\":\"今天我感到非常烦闷\\r\\n我想念你\\r\\n我想起夜幕降临的时候\\r\\n和你踏着星光走去\\r\\n想起了灯光照着树叶的时候\\r\\n踏着婆娑的灯影走去\\r\\n想起了欲语又塞的时候\\r\\n和你在一起\\r\\n你是我的战友\\r\\n因此我想念你\\r\\n当我跨过沉沦的一切\\r\\n向着永恒开战的时候\\r\\n你是我的军旗\\t\\t\",\"tag\":null,\"likes\":0},{\"collectnum\":0,\"pic\":null,\"title\":\"绿毛水怪\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":25,\"writer\":\"王小波\",\"time\":\"2020-11-27 18:15:08\",\"text\":\"你看，那水银灯的灯光\\r\\n像大团的蒲公英\\r\\n浮在街道的河流上\\r\\n吞吐着柔软的针一样的光\\r\\n我们在人行道上\\r\\n这昏黄的路灯\\r\\n它把昏黄的灯光\\r\\n隔着蒙蒙的雾气投向地面\\r\\n我们好像在池塘的水底\\r\\n从月亮走向另一个月亮\",\"tag\":null,\"likes\":0},{\"collectnum\":0,\"pic\":null,\"title\":\"月亮与六便士\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":26,\"writer\":\"毛姆\",\"time\":\"2020-11-28 08:19:48\",\"text\":\"在爱情的事上如果你考虑起自尊心来，那只能有一个原因：实际上你还是最爱自己\\t\\t\",\"tag\":null,\"likes\":0}]}";
        //String jsons = "[{\"collectnum\":1,\"pic\":\"http://49.232.217.140:8080/OuranServices/imgs/user/xs.png\",\"title\":\"如果有来生\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":18,\"writer\":\"三毛\",\"time\":\"2020-11-26 11:32:46\",\"text\":\"如果有来生，要做一棵树，\\r\\n站成永恒。没有悲欢的姿势，\\r\\n一半在尘土里安详，\\r\\n一半在风里飞扬；\\r\\n一半洒落荫凉，\\r\\n一半沐浴阳光。\\r\\n非常沉默、非常骄傲。\\r\\n从不依靠、从不寻找。\\r\\n如果有来生，要化成一阵风，\\r\\n一瞬间也能成为永恒。\\r\\n没有善感的情怀，没有多情的眼睛。\\r\\n一半在雨里洒脱，\\r\\n一半在春光里旅行；\\r\\n寂寞了，孤自去远行，\\r\\n把淡淡的思念统统带走，\\r\\n从不思念、从不爱恋；\\r\\n如果有来生，要做一只鸟，\\r\\n飞越永恒，没有迷途的苦恼。\\r\\n东方有火红的希望，\\r\\n南方有温暖的巢床，\\r\\n向西逐退残阳，向北唤醒芬芳。\\r\\n如果有来生，\\r\\n希望每次相遇，\\r\\n都能化为永恒。\\t\\t\",\"tag\":\"三毛诗选\",\"likes\":1},{\"collectnum\":0,\"pic\":null,\"title\":\"沙漠\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":20,\"writer\":\"三毛\",\"time\":\"2020-11-26 11:34:28\",\"text\":\"前世的乡愁 ? ?铺展在眼前\\r\\n\\r\\n啊 ? 黄沙万丈的布\\r\\n\\r\\n当我 ?当我\\r\\n\\r\\n被这天地玄黄牢牢捆住\\r\\n\\r\\n漂流的心 ? ?在这里慢慢\\r\\n\\r\\n慢慢一同落尘\\r\\n\\r\\n呼啸长空的风\\r\\n\\r\\n卷去了不回的路\\r\\n\\r\\n大地就这么交出了它的秘密\\r\\n\\r\\n那时 ? 沙漠便不再只是沙漠\\r\\n\\r\\n沙漠化为一口水井 ?井里面\\r\\n\\r\\n一双水的眼睛\\r\\n\\r\\n荡出一抹微笑\",\"tag\":\"三毛诗选\",\"likes\":0},{\"collectnum\":0,\"pic\":\"http://49.232.217.140:8080/OuranServices/imgs/user/xs.png\",\"title\":\"橄榄树\",\"userid\":2,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":3,\"id\":19,\"writer\":\"三毛\",\"time\":\"2020-11-26 11:34:08\",\"text\":\"不要问我从bai哪里来du\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪zhi\\r\\n\\r\\n流浪远方\\r\\n\\r\\n流浪\\r\\n\\r\\n为了dao天空飞翔的小鸟\\r\\n\\r\\n为了山间轻流的小溪\\r\\n\\r\\n为了宽阔的草原\\r\\n\\r\\n流浪远方 ?流浪\\r\\n\\r\\n还有 ?还有\\r\\n\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n为什么流浪远方\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n流浪远方\",\"tag\":\"三毛诗选\",\"likes\":0},{\"collectnum\":0,\"pic\":\"http://49.232.217.140:8080/OuranServices/imgs/user/xs.png\",\"title\":\"橄榄树\",\"userid\":0,\"cheatnum\":0,\"isoriginal\":0,\"forwardnum\":0,\"typeid\":0,\"id\":21,\"writer\":\"三毛\",\"time\":\"2020-11-27 16:44:44\",\"text\":\"不要问我从bai哪里来du\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪zhi\\r\\n\\r\\n流浪远方\\r\\n\\r\\n流浪\\r\\n\\r\\n为了dao天空飞翔的小鸟\\r\\n\\r\\n为了山间轻流的小溪\\r\\n\\r\\n为了宽阔的草原\\r\\n\\r\\n流浪远方 ?流浪\\r\\n\\r\\n还有 ?还有\\r\\n\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n为什么流浪远方\\r\\n为了梦中的橄榄树\\r\\n\\r\\n不要问我从哪里来\\r\\n\\r\\n我的故乡在远方\\r\\n\\r\\n为什么流浪\\r\\n\\r\\n流浪远方\",\"tag\":null,\"likes\":0},{\"collectnum\":0,\"pic\":null,\"title\":\"日记01\",\"userid\":1,\"cheatnum\":0,\"isoriginal\":1,\"forwardnum\":0,\"typeid\":2,\"id\":22,\"writer\":\"李炬宇\",\"time\":\"2020-11-27 17:06:13\",\"text\":\"啊好累啊，我死了\\t\\t\",\"tag\":null,\"likes\":0}]";
        if(jsons !=null) {
            listener.onLoadContentDataSuccess(jsons);
        }
    }



}
