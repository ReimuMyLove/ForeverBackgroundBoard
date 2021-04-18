package com.example.shoujiedemo.myCenter.myCenter.view.fragment.popupWindow.imgChange;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.util.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;

public class UserImgChangeActivity extends BaseActivity {
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;


    ImageView
            myCenter_userImgChange_get;     //图片
    View
            myCenter_userImgChange_img;     //图片裁剪框
    Button
            myCenter_userImgChange_take,    //拍照按钮
            myCenter_userImgChange_photo;   //相册按钮
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userimgchange);
        //获取view控件
        findView();
        //设置监听器
        setListener();
        //
    }

    /**
     * 获取view方法
     */
    private void findView(){
        myCenter_userImgChange_img = findViewById(R.id.myCenter_userImgChange_img);
        myCenter_userImgChange_take = findViewById(R.id.myCenter_userImgChange_take);
        myCenter_userImgChange_photo = findViewById(R.id.myCenter_userImgChange_photo);
        myCenter_userImgChange_get = findViewById(R.id.myCenter_userImgChange_get);
    }

    /**
     * 设置监听器方法
     */
    private void setListener() {
        MyListener listener = new MyListener();
        myCenter_userImgChange_take.setOnClickListener(listener);
        myCenter_userImgChange_photo.setOnClickListener(listener);
    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.myCenter_userImgChange_take:
                    takePhoto();
                    break;
                case R.id.myCenter_userImgChange_photo:
                    setPhoto();
                    break;
            }
        }
    }

    private void takePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        //限定图片返回值为Uri
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        // 判断系统中是否有处理该 Intent 的 Activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        } else {
            Toast.makeText(this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
        }
    }

    private void setPhoto(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 回调成功
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    Log.e("tag","3.5");
                    if (data != null) {
                        Log.e("tag","4");
                        setPicToView(data);
                    }
                    break;

                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        Log.e("tag","1");
                        assert data != null;
                        startSmallPhotoZoom(data.getData());//拿到选取图片的Uri
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
    public void startSmallPhotoZoom(Uri uri) {
        //跳转到安卓图片裁剪
        Log.e("tag","2");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);//返回bitmap对象
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

    /**
     * 小图模式中，保存图片后，设置到视图中
     */
    private void setPicToView(Intent data) {
        Log.e("tag","5");
        Bundle extras = data.getExtras();
        assert extras != null;
        Log.e("tag", extras.toString());
        Bitmap photo = extras.getParcelable("data");
        // 直接获得内存中保存的 bitmap
        // 创建 smallIcon 文件夹
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dirFile = new File(getExternalFilesDir(null) + "/userImg");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败2");
                } else {
                    Log.e("TAG", "文件夹创建成功2");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            // 保存图片（以bitmap形式返回）
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                if (photo != null) {
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                }
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        myCenter_userImgChange_get.setImageBitmap(photo);
        Log.e("tag", myCenter_userImgChange_get.toString());
    }
}
