package com.example.shoujiedemo.upload.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.UploadBean;
import com.example.shoujiedemo.entity.Music;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.upload.presenter.UploadPresenter;
import com.example.shoujiedemo.upload.presenter.UploadPresenterImpl;
import com.example.shoujiedemo.upload.view.LoadView;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MusicUploadActivity extends AppCompatActivity implements LoadView {

    private View uploadMusicInfo;//显示上传音乐的信息
    private ImageView musicCover;//音乐封面
    private TextView songName;//歌曲名字
    private TextView singer;//歌手
    private EditText edText;//想法
    private Button back;//返回按钮
    private Music music;
    private View confirmUpload;//上传弹出的视图
    private UploadPresenter presenter;
    private Button btnCommit;//上传按钮
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Button confirmBtnUpload;//确定按钮
    private Button confirmBtnCancel;//取消按钮
    private EditText edLink;//粘贴链接的地方
    private MyOnClikListener myOnClikListener;
    private String songId = null;
    private Animation animation;
    private ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_upload);
        presenter = new UploadPresenterImpl(this);
        builder = new AlertDialog.Builder(this);
        alert = builder.create();
        initView();
        setOnclikListener();

    }

    private void setOnclikListener() {
        myOnClikListener = new MyOnClikListener();
        uploadMusicInfo.setOnClickListener(myOnClikListener);
        back.setOnClickListener(myOnClikListener);
        confirmBtnUpload.setOnClickListener(myOnClikListener);
        confirmBtnCancel.setOnClickListener(myOnClikListener);
        btnCommit.setOnClickListener(myOnClikListener);
    }

    private void initView() {
        uploadMusicInfo = findViewById(R.id.upload_music_info);
        edText = findViewById(R.id.upload_music_ed_mind);
        singer = findViewById(R.id.upload_music_singer);
        songName = findViewById(R.id.upload_music_song_name);
        back = findViewById(R.id.upload_music_back);
        musicCover = findViewById(R.id.upload_music_cover);
        btnCommit = findViewById(R.id.upload_btn_music_commit);
        loading = findViewById(R.id.upload_music_loading);
        confirmUpload = LayoutInflater.from(this).inflate(R.layout.confirm_upload_music_view,null,false);

        confirmBtnUpload = confirmUpload.findViewById(R.id.upload_song_item_btn_commit);
        confirmBtnCancel = confirmUpload.findViewById(R.id.upload_song_item_btn_dismiss);
        edLink = confirmUpload.findViewById(R.id.upload_song_ed_link);



    }


    @Override
    public void skipSuccess() {

    }

    @Override
    public void skipFailure() {

    }


    @Override
    public void skipSuccess(final Music music) {
        if(music != null){
            loading.clearAnimation();
            songName.setVisibility(View.VISIBLE);
            loading.setVisibility(View.INVISIBLE);
            songName.setText(music.getName());
            singer.setText(music.getSinger());
            if(music.getPic() != null){
                RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.iv_default)
                                .fallback(R.drawable.ouran_default)
                                .centerCrop();

                Glide.with(getApplicationContext())
                                .load(music.getPic())
                                .apply(requestOptions)
                                .into(musicCover);

            }
            EventBus.getDefault().postSticky(new UploadBean(1));
        }
    }

    @Override
    public void showSet(List<Set> sets) {

    }

    class  MyOnClikListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.upload_music_info:
                    alert.show();
                    Window window = alert.getWindow();
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.setContentView(confirmUpload);
                    window.setLayout(1000,950);
                    alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    break;
                case R.id.upload_music_back:
                    finish();
                    break;
                case R.id.upload_btn_music_commit:
                    if(edText.getText().toString() != null)
                        presenter.uploadMusic(UserUtil.USER_ID,Integer.parseInt(songId),edText.getText().toString());
                    else
                        presenter.uploadMusic(UserUtil.USER_ID,Integer.parseInt(songId));
                    finish();
                    break;
                case R.id.upload_song_item_btn_commit:
                    songName.setVisibility(View.INVISIBLE);
                    String url = edLink.getText().toString().trim();
                    String[] urlSpilts = url.split("song");
                    String urlSplit2 = urlSpilts[1];
                    Log.e("id1",urlSplit2+"");
                    if(urlSplit2.charAt(0) =='/'){
                        songId = urlSplit2.split("/")[1];
                        loading.setVisibility(View.VISIBLE);
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading_music_anim_rotate);
                        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
                        animation.setInterpolator(lin);
                        loading.startAnimation(animation);
                        presenter.uploadMusic(-1,Integer.parseInt(songId));
                        alert.dismiss();
                        Log.e("id",songId+"");
                    }else{
                        String str1 =  url.substring(url.indexOf("?id="));
                        songId = str1.substring(4,str1.indexOf("&"));
                        loading.setVisibility(View.VISIBLE);
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.loading_music_anim_rotate);
                        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
                        animation.setInterpolator(lin);
                        loading.startAnimation(animation);
                        presenter.uploadMusic(-1,Integer.parseInt(songId));
                        alert.dismiss();
                        Log.e("id",songId+"");
                    }
                    break;
                case R.id.upload_song_item_btn_dismiss:
                    alert.dismiss();
                    break;
            }
        }
    }
}