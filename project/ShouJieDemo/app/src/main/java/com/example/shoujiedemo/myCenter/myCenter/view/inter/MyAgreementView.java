package com.example.shoujiedemo.myCenter.myCenter.view.inter;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;

import java.util.List;

public interface MyAgreementView {
    void getAgreementListSuccessful(List<LikeBean> agreementList, List<Content> writerArticleList, List<User> userList);
    void getAgreementListFailed();
}
