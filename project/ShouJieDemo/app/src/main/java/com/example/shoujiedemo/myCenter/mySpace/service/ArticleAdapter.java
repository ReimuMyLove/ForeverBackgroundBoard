package com.example.shoujiedemo.myCenter.mySpace.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.bean.DeleteGroupEvent;
import com.example.shoujiedemo.entity.Set;
import com.example.shoujiedemo.myCenter.mySpace.presenter.MySpacePresenter;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.activity.ChangeGroupImg;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.fragment.activity.MyArticleActivity;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleAdapterView;
import com.example.shoujiedemo.myCenter.mySpace.view.inter.ArticleView;
import com.example.shoujiedemo.util.ConfigUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements ArticleAdapterView{
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<Set> setList;
    int userID;

    public ArticleAdapter(Context context,List<Set> setList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.setList = setList;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.article_list, parent, false);
        return new ViewHolder(view,mContext,userID);//返回一个holder对象，给下个方法使用
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        if(setList!=null){
            Set set = setList.get(position);
            holder.article_groupID.setText(set.getId()+"");
            holder.article_title.setText(set.getName());
            holder.article_number.setText(set.getTuwen_num()+"");
            if (set.getPic() != null){
                if (!set.getPic().equals("")){
                    String URL = ConfigUtil.BASE_WENJI_URL+set.getPic();
                    RequestOptions requestOptions = new RequestOptions().centerCrop();
                    Glide.with(mContext)
                            .load(URL)
                            .apply(requestOptions)
                            .into(holder.article_image);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return setList == null ? 0 : setList.size();
    }

    @Override
    public void showSet(List<Set> setList) {
        this.setList = setList;
        notifyDataSetChanged();
    }

    @Override
    public void getGroupFailed() {
        Toast.makeText(mContext, "偶然：获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccessful() {
        Toast.makeText(mContext, "偶然：删除文集成功", Toast.LENGTH_SHORT).show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements ArticleView{
        private TextView
                article_title,          //标题
                article_number,         //描述
                article_groupID,        //文集ID
                article_delete,         //删除按钮
                article_changeImg;      //更换封面
        private  Button
                article_choose;         //点选按钮
        private ImageView
                article_image;          //图片
        private RelativeLayout
                article_layout;         //整个layout布局
        private Context context;
        private  MySpacePresenter
                mySpacePresenter;       //mySpacePresenter
        private CardView article_menu;  //点击菜单

        int userID;
        public ViewHolder(View view,Context context,int userID){
            super(view);
            this.context = context;
            //这个是用于整个子项的控制的控件
            mySpacePresenter = new MySpacePresenter(this);
            findView(view);
            setListener();
            setViewHW();
            this.userID = userID;
        }

        public void findView(View view){
            article_title = view.findViewById(R.id.article_title);
            article_delete = view.findViewById(R.id.article_delete);
            article_number = view.findViewById(R.id.article_number);
            article_choose = view.findViewById(R.id.article_choose);
            article_layout = view.findViewById(R.id.article_layout);
            article_groupID = view.findViewById(R.id.article_groupID);
            article_image = view.findViewById(R.id.article_image);
            article_delete = view.findViewById(R.id.article_delete);
            article_changeImg = view.findViewById(R.id.mySpace_changeBackGround);
            article_menu = view.findViewById(R.id.article_menu);
        }

        public void setListener(){
            MyListener listener = new MyListener();
            article_title.setOnClickListener(listener);
            article_delete.setOnClickListener(listener);
            article_choose.setOnClickListener(listener);
            article_image.setOnClickListener(listener);
            article_changeImg.setOnClickListener(listener);
        }

        @Override
        public void deleteFailed() {
            Toast.makeText(context, "偶然：删除失败", Toast.LENGTH_SHORT).show();
        }

        /**
         * 绑定监听器方法
         */
        private class MyListener implements View.OnClickListener{

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.article_image:
                    case R.id.article_layout:
                        articleIntent(context);
                        break;
                    case R.id.article_choose:
                        listSet();
                        break;
                    case R.id.article_delete:
                        delete();
                        break;
                    case R.id.mySpace_changeBackGround:
                        changeGroupImg();
                        break;
                }
            }
        }

        private void setViewHW(){
            /* 获取屏幕宽度 */
            DisplayMetrics wm = context.getResources().getDisplayMetrics();
            int width = wm.widthPixels;
            /* 获取控件自身高度 */
            ViewGroup.LayoutParams
                    layout,
                    choose;
            //修改整体layout布局高宽
            layout = article_layout.getLayoutParams();
            layout.width  = (width/2);
            layout.height = (width/2);
            //修改按钮大小
            choose = article_choose.getLayoutParams();
            choose.height = layout.height/8;
            choose.width = layout.height/8;
        }

        /**
         * 按钮显示方法
         */
        private void listSet(){
            if (article_menu.getVisibility() == View.VISIBLE){
                article_menu.setVisibility(View.INVISIBLE);
                article_menu.setClickable(false);
            }else{
                article_menu.setVisibility(View.VISIBLE);
                article_menu.setClickable(true);
            }
        }

        /**
         *  跳转到个人文集页面
         */
        private void articleIntent(Context context){
            Intent intent = new Intent(context, MyArticleActivity.class);
            Bundle bundle = new Bundle();
            int groupID = Integer.parseInt(article_groupID.getText().toString());
            Log.e("groupId",groupID + "");
            bundle.putInt("groupID",groupID);
            intent.putExtra("bundle",bundle);
            context.startActivity(intent);
        }

        /**
         * 删除方法
         */
        private void delete(){
            int groupID = Integer.parseInt(article_groupID.getText().toString());
            DeleteGroupEvent deleteGroupEvent = new DeleteGroupEvent();
            deleteGroupEvent.setGroupID(groupID);
            article_menu.setVisibility(View.INVISIBLE);
            mySpacePresenter.delete(groupID);
            EventBus.getDefault().postSticky(deleteGroupEvent);
        }

        /**
         * 更换文集信息
         */
        private void changeGroupImg(){
            int groupID = Integer.parseInt(article_groupID.getText().toString());
            Intent intent = new Intent(context, ChangeGroupImg.class);
            intent.putExtra("groupID",groupID);
            context.startActivity(intent);
        }
    }
}
