package com.example.shoujiedemo.myCenter.mySpace.view.inter;


import com.example.shoujiedemo.entity.Set;

import java.util.List;

public interface ArticleAdapterView {
    void showSet(List<Set> setList);
    void deleteSuccessful();

    void getGroupFailed();
}
