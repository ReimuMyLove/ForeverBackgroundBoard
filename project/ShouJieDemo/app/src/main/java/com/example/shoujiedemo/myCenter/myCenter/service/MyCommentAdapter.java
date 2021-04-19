package com.example.shoujiedemo.myCenter.myCenter.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.shoujiedemo.entity.Comment;
import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.User;
import com.example.shoujiedemo.myCenter.mySpace.view.activity.MySpaceActivity;
import com.example.shoujiedemo.util.CircleImageView;
import com.example.shoujiedemo.util.ConfigUtil;
import com.example.shoujiedemo.util.UserUtil;

import java.util.List;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder>{
    private final LayoutInflater mLayoutInflater;
    private final Context context;
    private List<Comment> commentList;
    private List<Content> writerArticleList;
    private List<User> userList;

    public MyCommentAdapter(Context context, List<Comment> commentList, List<Content> writerArticleList, List<User> userList){
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.commentList = commentList;
        this.writerArticleList = writerArticleList;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.mycomment_list, parent, false);
        return new MyCommentAdapter.ViewHolder(view,context);//返回一个holder对象，给下个方法使用
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyCommentAdapter.ViewHolder holder, int position) {
        if(commentList != null){//判断点赞信息是否为空
            //获取单个数据
            Comment comment = commentList.get(position);
            //设置用户名
            holder.myComment_userName.setText(UserUtil.USER_NAME);
            holder.myComment_myComment.setText(comment.getText());
            //收藏时间
            holder.myComment_time.setText(comment.getTime());
            //用户自身头像
            if (!UserUtil.USER_IMG.equals("")){
                String URL = ConfigUtil.BASE_HEAD_URL+UserUtil.USER_IMG;
                RequestOptions requestOptions = new RequestOptions().centerCrop();
                Glide.with(context)
                        .load(URL)
                        .apply(requestOptions)
                        .into(holder.myComment_userImg);
            }
            //获取文章ID
            int articleID = comment.getTu_id();
            //开始设置writerArticle内容
            for(int i = 0; i < writerArticleList.size(); i++){
                if (writerArticleList.get(i).getId() == articleID
                        && writerArticleList.get(i)!=null) {//判断点赞的文章信息是否为空且是否等于当前文章ID
                    //获取当前文章信息
                    Content writerArticle = writerArticleList.get(i);
                    //文章标题
                    holder.myComment_articleTitle.setText(writerArticle.getTitle());
                    //封面图片
                    if ( writerArticle.getPic() != null){
                        if (!writerArticle.getPic().equals("")){
                            String articleURL = ConfigUtil.BASE_IMG_URL + writerArticle.getPic();
                            RequestOptions articleOptions = new RequestOptions().centerCrop();
                            Glide.with(context)
                                    .load(articleURL)
                                    .apply(articleOptions)
                                    .into(holder.myComment_articleImg);
                        }
                    }else{
                        ViewGroup.LayoutParams writerArticleImg;
                        writerArticleImg = holder.myComment_writerImg.getLayoutParams();
                        writerArticleImg.height = 0;
                    }
                    //比较user信息
                    for(int j = 0; j < userList.size(); j++){
                        if (writerArticle.getUserid() == userList.get(j).getId()){
                            //发布者姓名
                            User user = userList.get(j);
                            holder.myComment_writerID.setText(user.getId()+"");
                            holder.myComment_writerName.setText(user.getName());
                            //发布者头像
                            if(user.getPicname()!= null){
                                if (!user.getPicname().equals("")){
                                    String writerURL = ConfigUtil.BASE_HEAD_URL + user.getPicname();
                                    RequestOptions writerOptions = new RequestOptions().centerCrop();
                                    Glide.with(context)
                                            .load(writerURL)
                                            .apply(writerOptions)
                                            .into(holder.myComment_writerImg);
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                myComment_userName,         //用户名
                myComment_time,             //评论时间
                myComment_myComment,        //评论内容
                myComment_writerName,       //发布者名
                myComment_articleTitle,     //文章标题
                myComment_writerID;         //发布者ID
        CircleImageView
                myComment_userImg,          //用户头像
                myComment_writerImg;        //发布者头像
        ImageView
                myComment_articleImg;       //文章图片

        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            //获取控件
            findView(itemView);
            //设置监听器
            setListener();
        }

        /**
         * 获取控件方法
         */
        private void findView(View view){
            myComment_userName = view.findViewById(R.id.myComment_userName);
            myComment_time = view.findViewById(R.id.myComment_time);
            myComment_myComment = view.findViewById(R.id.myComment_myComment);
            myComment_writerName = view.findViewById(R.id.myComment_writerName);
            myComment_articleTitle = view.findViewById(R.id.myComment_articleTitle);
            myComment_userImg = view.findViewById(R.id.myComment_userImg);
            myComment_writerImg = view.findViewById(R.id.myComment_writerImg);
            myComment_articleImg = view.findViewById(R.id.myComment_articleImg);
            myComment_writerID = view.findViewById(R.id.myComment_writerID);
        }

        /**
         * 设置监听器
         */
        private void setListener(){
            MyListener listener = new MyListener();
            myComment_userName.setOnClickListener(listener);
            myComment_writerName.setOnClickListener(listener);
            myComment_articleImg.setOnClickListener(listener);
            myComment_articleTitle.setOnClickListener(listener);
            myComment_userImg.setOnClickListener(listener);
            myComment_writerImg.setOnClickListener(listener);
        }

        /**
         * 绑定监听器方法
         */
        private class MyListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.myComment_articleImg:
                    case R.id.myComment_articleTitle:
                        articleIntent();
                        break;
                    case R.id.myComment_userName:
                    case R.id.myComment_userImg:
                        mySpaceIntent();
                        break;
                    case R.id.myComment_writerImg:
                    case R.id.myComment_writerName:
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
            int userID = Integer.parseInt(myComment_writerID.getText().toString());
            user.setId(userID);
            Intent intent = new Intent(context, MySpaceActivity.class);
            intent.putExtra("user",user);
            context.startActivity(intent);
        }
    }
}
