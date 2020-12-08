package com.example.shoujiedemo.upload.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.upload.presenter.UploadPresenterImpl;
import com.example.shoujiedemo.upload.view.LoadView;

public class PoemUploadActivity extends AppCompatActivity implements LoadView {
    private UploadPresenterImpl presenter;
    private EditText main_text;
    private EditText writer;
    private EditText title;
    private EditText tag;
    private Button commit;
    private RadioButton radio1;
    private RadioButton radio2;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_upload);
        initData();
    }

    private void initData() {
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        main_text = findViewById(R.id.main_text);
        writer = findViewById(R.id.writer);
        title = findViewById(R.id.title);
        commit = findViewById(R.id.btn_commit);
        tag = findViewById(R.id.tag);
        setOnClikListener();
        presenter = new UploadPresenterImpl(PoemUploadActivity.this);
    }

    @Override
    public void skipSuccess() {
        Toast.makeText(this, "上传成功", Toast.LENGTH_LONG);
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
     * */
    private void changeTag() {
        if (radio2.isChecked()) {
            Log.e("wrk", 2 + "");
            radio1.setChecked(true);
            radio2.setChecked(false);
            Log.e("wrk", radio1.isChecked() + "");
        }
    }

    private void changeTag1() {
        if (radio1.isChecked()) {

            Log.e("wrk", 1 + "");
            radio1.setChecked(false);
            radio2.setChecked(true);
            Log.e("wrk", radio1.isChecked() + "");
            flag=false;

        }
    }

    private void CommitPoem() {
        Content content = new Content();
        content.setTypeid(3);
        content.setText(main_text.getText().toString());
        content.setTitle(title.getText().toString());
        content.setWriter(writer.getText().toString());
        content.setTag(tag.getText().toString());
        content.setUserid(2);
        if (radio1.isChecked()) {
            presenter.UploadData(content, 1);
        } else {
            presenter.UploadData(content, 0);
        }
        Log.e("", content.toString());


    }
}