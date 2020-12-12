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
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.activity.ArticleActivity;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.fround.fragments.FroundFragment;
import com.example.shoujiedemo.upload.presenter.UploadPresenterImpl;
import com.example.shoujiedemo.upload.view.LoadView;

import java.io.File;

public class AriticleUploadActivity extends AppCompatActivity implements LoadView {
    private UploadPresenterImpl presenter;
    private EditText main_text;
    private EditText writer;
    private EditText title;
    private EditText tag;
    private Button commit;
    private RadioButton radio1;
    private RadioButton radio2;
    private Button btn_carma;
    private Button btn_gallery;
    private ImageView imageView;
    private Uri uri = null;
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
        setContentView(R.layout.activity_ariticle_upload);
        //initData();
    }

    @Override
    public void skipSuccess() {

    }

    @Override
    public void skipFailure() {

    }

    /*public void gallery() {
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
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void initData() {
        imageView = findViewById(R.id.image);
        btn_carma = findViewById(R.id.btn_camra);
        btn_gallery = findViewById(R.id.btn_gallery);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        main_text = findViewById(R.id.main_text);
        writer = findViewById(R.id.writer);
        title = findViewById(R.id.title);
        commit = findViewById(R.id.btn_commit);
        tag = findViewById(R.id.tag);
        setOnClikListener();
        presenter = new UploadPresenterImpl(AriticleUploadActivity.this);
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
                Toast.makeText(AriticleUploadActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
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
        finish();
    }

    @Override
    public void skipFailure() {
        Toast.makeText(this, "上传失败", Toast.LENGTH_LONG);
    }

    private void setOnClikListener() {
        MyOnclickLisenter myOnclickLisenter = new MyOnclickLisenter();
        commit.setOnClickListener(myOnclickLisenter);
        radio1.setOnClickListener(myOnclickLisenter);
        radio2.setOnClickListener(myOnclickLisenter);
        btn_carma.setOnClickListener(myOnclickLisenter);
        btn_gallery.setOnClickListener(myOnclickLisenter);
    }

    class MyOnclickLisenter implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_commit:
                    CommitPoem();
                    break;
                case R.id.radio1:
                    changeTag();
                    break;
                case R.id.radio2:
                    changeTag1();
                    break;
                case R.id.btn_camra:
                    camera();
                    imageView.setImageBitmap(bitmap);
                case R.id.btn_gallery:
                    gallery();
                    imageView.setImageBitmap(bitmap);
            }
        }
    }*/

    /**
     * 点击单选框时，只需监控非本单选框是否选中
     * 假如监控本单选框，本来为未选中
     * 你点击的一瞬间改变了他的状态为选中
     * 点击后触发点击事件，方法判断他已被选择，所以又将其恢复成未被选择的状态
     * 故改变本单选框需要不能监控本单选框
     * 要通过监控其它非本单选框来实现
     */
    /*private void changeTag() {
        if (radio2.isChecked()) {
            radio1.setChecked(true);
            radio2.setChecked(false);
        }
    }

    private void changeTag1() {
        if (radio1.isChecked()) {
            radio1.setChecked(false);
            radio2.setChecked(true);
            flag = false;

        }
    }

    private void CommitPoem() {
        Content content = new Content();
        content.setTypeid(0);
        content.setText(main_text.getText().toString());
        content.setTitle(title.getText().toString());
        content.setWriter(writer.getText().toString());
        content.setTag(tag.getText().toString());
        content.setUserid(2);
        if (uri != null) {
            if (radio1.isChecked()) {
                presenter.UploadData(content, 1, uri);
            }else {
                presenter.UploadData(content, 0, uri);
            }
        } else {
            if (radio1.isChecked()) {
                presenter.UploadData(content, 1);
            }else {
                presenter.UploadData(content, 0);
            }
        }
        Log.e("", content.toString());


    }*/
}