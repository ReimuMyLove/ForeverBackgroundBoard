package com.example.shoujiedemo.myCenter.myCenter.presenter;

import android.net.Uri;

public interface ChangeImagePresenter {
    void UploadData(Uri uri , int userid);
    void UploadWenji(Uri uri,int userid, String name);
    void changeBackground(Uri uri , int userid);
}
