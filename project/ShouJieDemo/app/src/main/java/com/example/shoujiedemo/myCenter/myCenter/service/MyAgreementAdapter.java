package com.example.shoujiedemo.myCenter.myCenter.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.LikeBean;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.myCenter.presenter.MyAgreementPresenter;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.MySpaceActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;

import java.util.List;

public class MyAgreementAdapter extends RecyclerView.Adapter<MyAgreementAdapter.ViewHolder>{
    private final LayoutInflater mLayoutInflater;
    private final Context context;
    private List<LikeBean> agreementList;
    private List<Content> writerArticleList;
    private List<User> userList;

    public MyAgreementAdapter(Context context, List<LikeBean> agreementList,List<Content> writerArticleList,List<User> userList){
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.agreementList = agreementList;
        this.writerArticleList = writerArticleList;
        this.userList = userList;
    }
    @NonNull
    @Override
    public MyAgreementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.myagreement_list, parent, false);
        return new MyAgreementAdapter.ViewHolder(view,context);//返回一个holder对象，给下个方法使用
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAgreementAdapter.ViewHolder holder, int position) {
        if(agreementList != null){//判断点赞信息是否为空
            //获取单个点赞信息
            LikeBean agreement = agreementList.get(position);
            //点赞时间
            holder.myAgreement_time.setText(agreement.getTime());//收藏时间
            //设置用户名
            holder.myAgreement_userName.setText(UserUtil.USER_NAME);
            //用户自身头像
            if (!UserUtil.USER_IMG.equals("")){
                String URL = ConfigUtil.BASE_HEAD_URL+UserUtil.USER_IMG;
                RequestOptions requestOptions = new RequestOptions().centerCrop();
                Glide.with(context)
                        .load(URL)
                        .apply(requestOptions)
                        .into(holder.myAgreement_Img);
            }
            //获取文章ID
            int articleID = agreement.getTuwenid();
            //开始设置writerArticle内容
            for(int i = 0; i < writerArticleList.size(); i++){
                if (writerArticleList.get(i).getId() == articleID
                        && writerArticleList.get(i)!=null) {//判断点赞的文章信息是否为空且是否等于当前文章ID
                    //获取当前文章信息
                    Content writerArticle = writerArticleList.get(i);
                    //文章标题
                    holder.myAgreement_articleTitle.setText(writerArticle.getTitle());
                    //封面图片
                    if ( writerArticle.getPic() != null){
                        if (!writerArticle.getPic().equals("")){
                            String articleURL = ConfigUtil.BASE_IMG_URL + writerArticle.getPic();
                            RequestOptions articleOptions = new RequestOptions().centerCrop();
                            Glide.with(context)
                                    .load(articleURL)
                                    .apply(articleOptions)
                                    .into(holder.myAgreement_articleImg);
                        }
                    }else {
                        ViewGroup.LayoutParams writerArticleImg;
                        writerArticleImg = holder.myAgreement_articleImg.getLayoutParams();
                        writerArticleImg.height = 0;
                    }
                    //比较user信息
                    for(int j = 0; j < userList.size(); j++){
                        if (writerArticle.getUserid() == userList.get(j).getId()){
                            //发布者姓名
                            User user = userList.get(j);
                            holder.myAgreement_writerID.setText(user.getId()+"");
                            holder.myAgreement_writerName.setText(user.getName());
                            //发布者头像
                            if ( user.getPicname() != null){
                                if (!user.getPicname().equals("")){
                                    String writerURL = ConfigUtil.BASE_HEAD_URL + user.getPicname();
                                    RequestOptions writerOptions = new RequestOptions().centerCrop();
                                    Glide.with(context)
                                            .load(writerURL)
                                            .apply(writerOptions)
                                            .into(holder.myAgreement_writerImg);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return agreementList == null ? 0 : agreementList.size();
    }

    /**
     * 生成列表数据
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        Context context;
        CircleImageView
                myAgreement_Img,                //头像
                myAgreement_writerImg;          //作者头像
        TextView
                myAgreement_userName,           //用户名
                myAgreement_writerName,         //作者名
                myAgreement_articleTitle,       //文章标题
                myAgreement_time,               //点赞时间
                myAgreement_writerID;           //发布者ID
        ImageView
                myAgreement_articleImg;
        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            this.context = context;
            //获取控件
            findView(itemView);
            //
            setListener();
        }

        /**
         * 获取控件
         */
        private void findView(View view){
            myAgreement_articleImg = view.findViewById(R.id.myAgreement_articleImg);
            myAgreement_articleTitle = view.findViewById(R.id.myAgreement_articleTitle);
            myAgreement_writerImg = view.findViewById(R.id.myAgreement_writerImg);
            myAgreement_userName = view.findViewById(R.id.myAgreement_userName);
            myAgreement_writerName = view.findViewById(R.id.myAgreement_writerName);
            myAgreement_Img = view.findViewById(R.id.myAgreement_Img);
            myAgreement_time = view.findViewById(R.id.myAgreement_time);
            myAgreement_writerID = view.findViewById(R.id.myAgreement_writerID);
        }

        /**
         * 设置监听器
         */
        private void setListener(){
            MyListener listener = new MyListener();
            myAgreement_userName.setOnClickListener(listener);
            myAgreement_writerName.setOnClickListener(listener);
            myAgreement_articleImg.setOnClickListener(listener);
            myAgreement_articleTitle.setOnClickListener(listener);
            myAgreement_writerImg.setOnClickListener(listener);
            myAgreement_Img.setOnClickListener(listener);
        }

        /**
         * 绑定监听器方法
         */
        private class MyListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.myAgreement_articleImg:
                    case R.id.myAgreement_articleTitle:
                        articleIntent();
                        break;
                    case R.id.myAgreement_userName:
                    case R.id.myAgreement_Img:
                        mySpaceIntent();
                        break;
                    case R.id.myAgreement_writerName:
                    case R.id.myAgreement_writerImg:
                        othersSpaceIntent();
                        break;
                }
            }
        }

        //进入文章列表
        private void articleIntent(){

        }

        //进入我的空间
        private void mySpaceIntent(){
            Intent intent = new Intent(context, MySpaceActivity.class);
            context.startActivity(intent);
        }

        //跳转其他人空间
        private void othersSpaceIntent(){
            User user = new User();
            int userID = Integer.parseInt(myAgreement_writerID.getText().toString());
            user.setId(userID);
            Intent intent = new Intent(context, MySpaceActivity.class);
            intent.putExtra("user",user);
            context.startActivity(intent);
        }
    }
}
