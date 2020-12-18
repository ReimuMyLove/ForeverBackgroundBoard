package com.example.shoujiedemo.fround.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.shoujiedemo.bean.MusicEvent;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.util.MusicPlayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class NetPlayMusicService extends Service implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {

    private int currentTime;
    private int totalTime;
    private MediaPlayer mediaPlayer = null;
    private String musicUrl;
    //private boolean isPause;
    private boolean isSeek = false;
    private ExecutorService executorService;
    private ProcessSever processSever = new ProcessSever();
    private Music music;
    private boolean next;


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
        executorService= Executors.newSingleThreadExecutor();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getBundleExtra("bundle");
        Music music1 = (Music)bundle.getSerializable("music");
        if(music == null) {
            next =false;
            music = music1;
            Log.e("misucId", music.getId() + "");
            music.setTag(1);
            music.setStart(1);
            musicUrl = music.getPath();
            Log.e("url", musicUrl);
            try {
                mediaPlayer.setDataSource(musicUrl);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            next = true;
            music = music1;
            mediaPlayer.stop();
            mediaPlayer.reset();
            Log.e("misucId", music.getId() + "");
            music.setTag(1);
            music.setStart(1);
            musicUrl = music.getPath();
            Log.e("url", musicUrl);
            try {
                mediaPlayer.setDataSource(musicUrl);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        Log.e("结束","结束");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            this.mediaPlayer = null;
            music.setStop(true);
            music.setPause(true);
            music.setStart(0);
            music.setTag(1);
            MusicPlayUtil.MUSIC_IS_PLAY = -1;
            MusicPlayUtil.IS_START = false;
            MusicPlayUtil.IS_STOP = true;
            MusicPlayUtil.IS_PAUSE = true;
            EventBus.getDefault().postSticky(music);
        }
    }

    @Override
    public void onPrepared(final MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        music.setPause(false);
        music.setStop(false);
        music.setStart(1);
        music.setTag(1);
        MusicPlayUtil.IS_STOP = false;
        MusicPlayUtil.IS_PAUSE = false;
        MusicPlayUtil.IS_START = true;
        MusicPlayUtil.MUSIC_IS_PLAY = music.getId();
        EventBus.getDefault().postSticky(music);
        if(!next) {
            processSever.start();
        }else{
            next = false;
            processSever = new ProcessSever();
            processSever.start();
        }
    }

    @Override
    public void onSeekComplete(final MediaPlayer mediaPlayer) {

       /* isSeek = false;
        Log.e("sekk","seekk");*/
    }


    //暂停
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void pause(MusicEvent event){
        if(event.getTag() == 1){
            if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                music.setPause(true);
                music.setStop(false);
                music.setTag(1);
                music.setStart(1);
                MusicPlayUtil.IS_STOP = false;
                MusicPlayUtil.IS_PAUSE = true;
                MusicPlayUtil.IS_START = true;
                MusicPlayUtil.MUSIC_IS_PLAY = music.getId();
                Log.e("id",MusicPlayUtil.MUSIC_IS_PLAY + "");
                EventBus.getDefault().postSticky(music);
            }
        }
    }

    //继续播放
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void resume(MusicEvent event){
        if(event.getTag() == 3){
            if(music.isPause()) {
                mediaPlayer.start();
                music.setPause(false);
                music.setStop(false);
                music.setTag(1);
                music.setStart(1);
                MusicPlayUtil.IS_STOP = false;
                MusicPlayUtil.IS_PAUSE = false;
                MusicPlayUtil.IS_START = true;
                MusicPlayUtil.MUSIC_IS_PLAY = music.getId();
                EventBus.getDefault().postSticky(music);
                processSever = new ProcessSever();
                processSever.start();
            }
        }
    }

    //停止播放
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void stop(MusicEvent event){
        if(event.getTag() == 2) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                music.setStop(true);
                music.setPause(true);
                music.setTag(1);
                music.setStart(0);
                MusicPlayUtil.IS_STOP = true;
                MusicPlayUtil.IS_PAUSE = true;
                MusicPlayUtil.IS_START = false;
                MusicPlayUtil.MUSIC_IS_PLAY = -1;
                EventBus.getDefault().postSticky(music);
            }
        }
    }

    private int getCurrentPosition(){
        if(mediaPlayer!=null){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

   private int getDuration(){
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void changeSeekTo(MusicEvent event){
        if(event.getTag() == 4) {
            mediaPlayer.seekTo(event.getCurrentTime() * 1000);
        }
    }

    public class ProcessSever extends Thread{

        @Override
        public void run() {

            while (mediaPlayer.isPlaying() || MusicPlayUtil.IS_PAUSE){
                    int currentPosition = getCurrentPosition();
                    int durationPosition = getDuration();
                    music.setCurrenttime(currentPosition / 1000);
                    music.setTotaltime(durationPosition / 1000);
                    MusicEvent event = new MusicEvent();
                    event.setTag(5);
                    event.setDuration(durationPosition / 1000);
                    event.setCurrentTime(currentPosition / 1000);
                    EventBus.getDefault().postSticky(event);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                if(next || MusicPlayUtil.IS_STOP){
                    break;
                }
                continue;
            }

        }
    }






}