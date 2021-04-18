package com.example.shoujiedemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.fround.service.NetPlayMusicService;

import org.greenrobot.eventbus.EventBus;

public class MusicActivity extends AppCompatActivity {

    private AppCompatSeekBar seekBar;
    private Button btnPaly;
    private Button btnPause;
    private Button btnStop;
    private boolean isPause = false;
    private int start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        seekBar = findViewById(R.id.seekBar);
        btnPaly = findViewById(R.id.play_music);
        btnPause = findViewById(R.id.pause_music);

        btnPaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(start == 0) {
                    Intent intent = new Intent(MusicActivity.this, NetPlayMusicService.class);
                    String url = "http://m8.music.126.net/20201212174617/53eb0a84c21f4ff6341215990ec89ddc/ymusic/obj/w5zDlMODwrDDiGjCn8Ky/3583896884/7ce1/497f/7fcd/46c20886c3fce93688cf081899534449.mp3";
                    intent.putExtra("url", url);
                    startService(intent);
                    start = 1;
                }
                if(isPause){
                    EventBus.getDefault().postSticky(new Integer(0));
                    isPause = false;
                }

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPause){
                    EventBus.getDefault().postSticky(new Integer(100));
                    isPause = true;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}