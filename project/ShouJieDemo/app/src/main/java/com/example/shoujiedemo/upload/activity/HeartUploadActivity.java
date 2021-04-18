package com.example.shoujiedemo.upload.activity;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.upload.presenter.UploadPresenterImpl;
import com.example.shoujiedemo.upload.view.LoadView;
import com.example.shoujiedemo.util.SwitchButton;

import java.io.File;

public class HeartUploadActivity extends AppCompatActivity implements LoadView {
    private UploadPresenterImpl presenter;
    private EditText main_text;
    private EditText writer;
    private EditText title;
    private EditText tag;
    private Button commit;
    private SwitchButton radio1;
    private Button btn_carma;
    private ImageView btn_gallery;
    private ImageView imageView;
    private Uri uri;
    private boolean flag = true;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 裁剪之后
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";//临时文件名
    private File tempFile;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_upload);
        initData();
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
        startActivityForResult(intent, PHOTO_REQUEST_CUT); // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
    }

    private boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void initData() {
//        btn_carma=findViewById(R.id.upload_poem_cover);
        imageView = findViewById(R.id.upload_heart_cover);
        radio1 = findViewById(R.id.isOriginal);
        main_text = findViewById(R.id.ed_heart_content);
        writer = findViewById(R.id.follow_heart_ed_comment);
        title = findViewById(R.id.title);
        commit = findViewById(R.id.upload_btn_heart_commit);
        tag = findViewById(R.id.custom_tag);
        setOnClikListener();
        presenter = new UploadPresenterImpl(HeartUploadActivity.this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {// 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {//从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(HeartUploadActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {//从剪切图片返回的数据
            if (data != null) {
                bitmap = data.getParcelableExtra("data");
                imageView.setImageBitmap(bitmap);
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

    @Override
    public void skipSuccess() {
        Toast.makeText(HeartUploadActivity.this, "上传失败", Toast.LENGTH_LONG);
        finish();
    }

    @Override
    public void skipFailure() {
        Toast.makeText(this, "上传失败", Toast.LENGTH_LONG);
    }

    private void setOnClikListener() {
        MyOnclickLisenter myOnclickLisenter = new MyOnclickLisenter();
        imageView.setOnClickListener(myOnclickLisenter);
//        radio1.setOnClickListener(myOnclickLisenter);
        commit.setOnClickListener(myOnclickLisenter);
    }

    class MyOnclickLisenter implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.upload_btn_heart_commit:
                    CommitPoem();
                    break;
                case R.id.isOriginal:
                    changeTag();
                    break;
                case R.id.upload_heart_cover:
                    gallery();
            }
        }
    }

    /**
     * 点击单选框时，只需监控非本单选框是否选中
     * 假如监控本单选框，本来为未选中
     * 你点击的一瞬间改变了他的状态为选中
     * 点击后触发点击事件，方法判断他已被选择，所以又将其恢复成未被选择的状态
     * 故改变本单选框需要不能监控本单选框
     * 要通过监控其它非本单选框来实现
     */
    private void changeTag() {

        Log.e("wrk", 2 + "");
        radio1.setChecked(flag);
        flag = !flag;
        Log.e("wrk", radio1.isChecked() + "");

    }


    private void CommitPoem() {
        Content content = new Content();
        content.setTypeid(2);
        content.setText(main_text.getText().toString());
//        content.setTitle(title.getText().toString());
//        content.setWriter(writer.getText().toString());
        content.setTag(tag.getText().toString());
        content.setUserid(2);
        if (uri != null) {
            presenter.UploadData(content, 1, uri);
        } else {
            presenter.UploadData(content, 1);
        }
        Log.e("", content.toString());


    }
}