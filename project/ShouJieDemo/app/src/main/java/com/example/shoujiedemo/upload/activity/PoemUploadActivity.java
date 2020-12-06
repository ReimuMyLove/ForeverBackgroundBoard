package com.example.shoujiedemo.upload.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.upload.presenter.UploadPresenterImpl;
import com.example.shoujiedemo.upload.view.LoadView;

public class PoemUploadActivity extends AppCompatActivity implements LoadView {
    private UploadPresenterImpl presenter;
    private EditText main_text;
    private EditText writer;
    private EditText title;
    private EditText tag;
    private Button commit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_upload);
        initData();
    }

    private void initData()
    {
        main_text=findViewById(R.id.main_text);
        writer=findViewById(R.id.writer);
        title=findViewById(R.id.title);
        commit=findViewById(R.id.btn_commit);
        tag=findViewById(R.id.tag);
        setOnClikListener();
        presenter=new UploadPresenterImpl(PoemUploadActivity.this);
    }

    @Override
    public void skipSuccess() {
        Toast.makeText(this,"上传成功",Toast.LENGTH_LONG);
    }

    @Override
    public void skipFailure() {
        Toast.makeText(this,"上传失败",Toast.LENGTH_LONG);
    }
    private void setOnClikListener(){
        MyOnclickLisenter myOnclickLisenter=new MyOnclickLisenter();
        commit.setOnClickListener(myOnclickLisenter);
    }

    class MyOnclickLisenter implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_commit:
                    CommitPoem();
                    break;
            }
        }
    }

    private void CommitPoem() {
        Content content=new Content();
        content.setText(main_text.getText().toString());
        content.setTitle(title.getText().toString());
        content.setWriter(writer.getText().toString());
        content.setTag(tag.getText().toString());
        content.setUserid(2);
        Log.e("",content.toString());
        presenter.UploadData(content,1);
    }
}