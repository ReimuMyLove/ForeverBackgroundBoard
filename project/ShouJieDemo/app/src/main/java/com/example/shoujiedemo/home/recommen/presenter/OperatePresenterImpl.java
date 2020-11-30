package com.example.shoujiedemo.home.recommen.presenter;

import com.example.shoujiedemo.home.recommen.model.OperateModel;
import com.example.shoujiedemo.home.recommen.model.OperateModelImpl;
import com.example.shoujiedemo.home.recommen.view.RecommenView;

public class OperatePresenterImpl implements OperatePresenter,onOpreatePresenterListener{

    private RecommenView recommenView;
    private OperateModel operateModel;

    public OperatePresenterImpl(RecommenView recommenView) {
        this.recommenView = recommenView;
        this.operateModel = new OperateModelImpl();
    }

    /**
     * 点赞成功
     */
    @Override
    public void onLikeSuccess() {
        recommenView.changeHeadColor();
    }

    /**
     * 收藏成功
     */
    @Override
    public void onCollectionSucess() {
        recommenView.changCollectionColor();
    }
}
