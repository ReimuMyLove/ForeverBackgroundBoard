package com.example.shoujiedemo.fround.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.shoujiedemo.bean.MusicEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

public class NetPlayMusicService extends Service implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {

    private int currentTime;
    private int totalTime;
    private MediaPlayer mediaPlayer = null;
    private String musicUrl;
    private boolean isPause;


    public NetPlayMusicService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicUrl = intent.getStringExtra("url");
        Log.e("url",musicUrl);
        try {
            mediaPlayer.setDataSource(musicUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);
            //mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.e("prepar","王城");
        mediaPlayer.start();
        MusicEvent event = new MusicEvent();
        event.setDuration(mediaPlayer.getDuration());
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }

    //播放
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void play(Integer position) {
        if (position == 0){
            mediaPlayer.start();
            currentTime = mediaPlayer.getCurrentPosition();
            totalTime = mediaPlayer.getDuration();
            isPause = false;
        }
        /*MusicEvent event = new MusicEvent(currentTime,totalTime);
         EventBus.getDefault().postSticky(event);*/

    }

    //暂停
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void pause(Integer position){
        if(position == 100){
            if(mediaPlayer != null && mediaPlayer.isPlaying())
                mediaPlayer.pause();
            isPause = true;
        }
    }

    //继续播放
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void resume(Integer position){
        if(position == 200){
            if(isPause)
                mediaPlayer.start();
            isPause = false;
        }
    }

    //停止播放
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void stop(Integer position){
        if(position == 300) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}