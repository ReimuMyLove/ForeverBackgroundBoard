package com.example.shoujiedemo.myCenter.mySpace.view.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.ImgChangeEvent;
import com.example.shoujiedemo.myCenter.myCenter.presenter.ChangeImagePresenter;
import com.example.shoujiedemo.myCenter.myCenter.presenter.ChangeImagePrsenterImpl;
import com.example.shoujiedemo.myCenter.myCenter.view.inter.ChangeImageView;
import com.example.shoujiedemo.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class AddGroupActivity extends AppCompatActivity implements ChangeImageView {
    ImageView
            mySpace_addGroup_img;       //获取图片
    Button
            mySpace_addGroup_enter,     //上传
            mySpace_addGroup_cancel;    //取消上传
    EditText
            mySpace_addGroup_groupName; //文集名

    private ImageView imageView;
    private Uri uri = null;
    private boolean flag = true;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 裁剪之后
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";//临时文件名
    private File tempFile;
    private Bitmap bitmap;
    private ChangeImagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        presenter=new ChangeImagePrsenterImpl(this);
        //获取view控件
        FindView();
        //设置监听器
        setListener();
    }

    /**
     * 获取view方法
     */
    public void FindView() {
        mySpace_addGroup_img = findViewById(R.id.mySpace_addGroup_img);
        mySpace_addGroup_enter = findViewById(R.id.mySpace_addGroup_enter);
        mySpace_addGroup_cancel = findViewById(R.id.mySpace_addGroup_cancel);
        mySpace_addGroup_groupName = findViewById(R.id.mySpace_addGroup_groupName);
    }

    /**
     * 设置监听器方法
     */
    public void setListener(){
        MyListener listener = new MyListener();
        mySpace_addGroup_img.setOnClickListener(listener);
        mySpace_addGroup_enter.setOnClickListener(listener);
        mySpace_addGroup_cancel.setOnClickListener(listener);
    }

    @Override
    public void loadSuccess() {
        ImgChangeEvent imgChangeEvent = new ImgChangeEvent();
        imgChangeEvent.setImgChangeID(3);
        EventBus.getDefault().postSticky(imgChangeEvent);
        finish();
    }

    @Override
    public void loadError() {

    }

    /**
     * 绑定监听器方法
     */
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mySpace_addGroup_img:
                    gallery();
                    break;
                case R.id.mySpace_addGroup_enter:
                    updata();
                    break;
                case R.id.mySpace_addGroup_cancel:
                    cancelUpdate();
            }
        }
    }

    private void cancelUpdate() {
        Intent intent = new Intent();
        setResult(0,intent);
        finish();
    }

    private void updata() {

        String groupName = mySpace_addGroup_groupName.getText().toString();
        presenter.UploadWenji(uri, UserUtil.USER_ID,groupName);

    }


    private Uri external(String external) {
        String myImageUrl = "content://media" + external;
        Uri uri = Uri.parse(myImageUrl);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        File file = new File(img_path);
        Uri fileUri = Uri.fromFile(file);
        return fileUri;
    }

    public void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);//携带请求码
    }

    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (hasSdcard()) {// 判断存储卡是否可以用，可用进行存储
            tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
            Uri uri = Uri.fromFile(tempFile); // 从文件中创建uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);//携带请求码
    }


    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        Log.e("图片","get图片");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        Log.e("图片","裁剪完成");
        startActivityForResult(intent, PHOTO_REQUEST_CUT); // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
    }

    private boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {// 得到图片的全路径
                Uri uri = data.getData();
                Log.e("图片",uri+"");
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {//从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {//从剪切图片返回的数据
            if (data != null) {
                Log.e("图片","得到图片");
                bitmap = data.getParcelableExtra("data");
                assert bitmap != null;
                Log.e("图片",bitmap.toString());
                mySpace_addGroup_img.setImageBitmap(bitmap);
                //将bitmap转换为Uri
                uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                //对非正确的Uri处理，这类Uri存在手机的external.db中，可以查询_data字段查出对应文件的uri
                if (uri.getPath().contains("external")) {
                    uri = external(uri.getPath());
                }
                //在这可以拿到裁剪后的图片Uri,然后进行你想要的操作
            }
            try {
                tempFile.delete();//将临时文件删除
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}