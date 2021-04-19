package com.example.shoujiedemo.apiInterface;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterFace {

    /**
     * 加载内容
     * @return
     */
    @GET("findFollows")
    Observable<ResponseBody> loadFollowContent(@Query("userid") int userId,@Query("pagenum") int pageNum);

    /**
     * 加载内容
     * @return
     */
    @GET("findByhot")
    Observable<ResponseBody> loadHotContent(@Query("pagenum") int pageNum,@Query("userid") int userId);

    /**
     * 加载内容
     * @return
     */
    @GET("find")
    Observable<ResponseBody> loadContent(@Query("typeid") int typeId,@Query("pagenum") int page,@Query("userid") int userId);

    /**
     * 加载内容
     * @return
     */
    @GET("findByid")
    Observable<ResponseBody> loadArticleContent(@Query("id") int id);

    /**
     * 点赞
     * @return
     */
    @GET("addlike")
    Observable<ResponseBody> like(@Query("userid") int userId,@Query("tuwenid") int contentid);

    /**
     * 取消点赞
     * @return
     */
    @GET("minuslike")
    Observable<ResponseBody> disLike(@Query("userid") int userId,@Query("tuwenid") int contentid);

    /**
     * 收藏
     * @return
     */
    @GET("add")
    Observable<ResponseBody> collect(@Query("wenjiid") int setId,@Query("tuwen_id") int contentId);

    /**
     * 取消收藏
     * @return
     */
    @GET("delete")
    Observable<ResponseBody> disCollect(@Query("wenjiid") int setId,@Query("tuwen_id") int contentId);

    /**
     * 关注
     * @return
     */
    @GET("addFollow")
    Observable<ResponseBody> follow(@Query("userid") int userId,@Query("followerid") int followerId);

    /**
     * 取消关注
     * @return
     */
    @GET("minusFollow")
    Observable<ResponseBody> diFfollow(@Query("userid") int userId,@Query("followerid") int followerId);


    /**
     * 加载评论
     * @return
     */
    @GET("find")
    Observable<ResponseBody> loadComments(@Query("tu_id") int tuId,@Query("pagenum") int pageNum);

    /**
     * 添加评论
     * @return
     */
    @GET("addcheat")
    Observable<ResponseBody> addComment(@Query("user1id") int userId,@Query("tu_id") int contentId,@Query("text") String content);

    /**
     * 添加评论
     * @return
     */
    @GET("deletecheat")
    Observable<ResponseBody> deleteComment(@Query("id") int id);


    /**
     * 加载推荐
     * @return
     */
    @GET("getRecommendTuwen")
    Observable<ResponseBody> loadRecomment();

    /**
     * 搜索
     * @return
     */
    @GET("findByTag")
    Observable<ResponseBody> loadSearchHotData(@Query("tag") String flag,@Query("userid") int userid,@Query("pagenum") int pagenum);

    /**
     * 搜索
     * @return
     */
    @GET("findByTag")
    Observable<ResponseBody> loadSearchData(@Query("tag") String flag,@Query("userid") int userId,@Query("pagenum") int pagenum,@Query("typeid") int typeId);

    /**
     * 加载文集
     * @return
     */
    @GET("findwenjiByUser")
    Observable<ResponseBody> loadSet(@Query("userid") int userId);

    /**
     * 上传内容
     * */
    @Multipart
    @POST("addtuwen")
    Observable<ResponseBody> upload(@Query("title") String title,@Query("text") String text,@Query("userid") int userid,@Query("typeid") int typeid,@Query("writer") String writer,@Query("tag") String tag,@Query("wenjiid")int wenjiid,@Query("isoriginal")int isoriginal,@Query("haspic")int haspic,@Part MultipartBody.Part imgs);


    @GET("addtuwen")
    Observable<ResponseBody> upload(@Query("title") String title,@Query("text") String text,@Query("userid") int userid,@Query("typeid") int typeid,@Query("writer") String writer,@Query("tag") String tag,@Query("wenjiid")int wenjiid,@Query("isoriginal")int isoriginal);

    /**
     * 校验账户
     * @return
     */
    @GET("check")
    Observable<ResponseBody> login(@Query("name") String name, @Query("password") String password);

    /**
     * 注册账户
     */
    @POST("add")
    Observable<ResponseBody> register(@Query("name") String name, @Query("password") String password);

    /**
     * 删除指定文集
     */
    @POST("deletewenji")
    Observable<ResponseBody> delete(@Query("wenjiid") int groupID);

    /**
     * 获取文集
     */
    @GET("findwenjiByUser")
    Observable<ResponseBody> getArticles(@Query("userid") int userID);

    /**
     * 添加文集
     */
    @POST("addwenji")
    Observable<ResponseBody> addGroup(@Query("userid") int userID,@Query("name") String groupName);

    /**
     * 关注
     */
    @POST("addfollow")
    Observable<ResponseBody> addFollow(@Query("userid") int userID,@Query("followid") int followID);

    /**
     * 上传图片
     */
    @Multipart
    @POST("addwenji")
    Observable<ResponseBody> addGroup(@Query("userid") int userID,@Query("name") String groupName, @Part MultipartBody.Part file,@Query("hispic") int picNum);

    /**
     * 获取单个文集
     */
    @GET("findwenji")
    Observable<ResponseBody> getArticlesDetail(@Query("groupid") int groupID);


    /**
     * 获取关注人列表
     */
    @POST("findUserFollows")
    Observable<ResponseBody> findFollow(@Query("userid") int userID);

    /**
     * 穿越
     */
    @GET("recommendbytime")
    Observable<ResponseBody> loadByTime(@Query("time") String date);

    /**
     * 获取空间主人信息
     */
    @GET("find")
    Observable<ResponseBody> getOwnerInfo(@Query("userid") int userID);

    /**
     * 加载音乐
     */
    @GET("getmusic")
    Observable<ResponseBody> loadMusic(@Query("pagenum") int pagenum,@Query("userid") int userid);


    /**
     * 点赞
     * @return
     */
    @GET("addlike")
    Observable<ResponseBody> likeMusic(@Query("userid") int userId,@Query("musicid") int contentid);

    /**
     * 取消点赞
     * @return
     */
    @GET("minuslike")
    Observable<ResponseBody> disLikeMusic(@Query("userid") int userId,@Query("musicid") int contentid);

    /**\
     * 收藏
     * @return
     */
    @GET("add")
    Observable<ResponseBody> collectMusic(@Query("wenjiid") int setId,@Query("music_id") int contentId);

    /**
     * 取消收藏
     * @return
     */
    @GET("delete")
    Observable<ResponseBody> disCollectMusic(@Query("wenjiid") int setId,@Query("music_id") int contentId);

    /**
     * 关注
     * @return
     */
    @GET("addFollow")
    Observable<ResponseBody> followMusic(@Query("userid") int userId,@Query("followerid") int followerId);

    /**
     * 取消关注
     * @return
     */
    @GET("minusFollow")
    Observable<ResponseBody> diFfollowMusic(@Query("userid") int userId,@Query("followerid") int followerId);

    /**
     * 取消关注
     * @return
     */
    @GET("addmusic")
    Observable<ResponseBody> uploadMusic(@Query("userid") int userId,@Query("id") int songId);

    /**
     * 取消关注
     * @return
     */
    @GET("addmusic")
    Observable<ResponseBody> uploadMusic(@Query("userid") int userId,@Query("id") int songId,@Query("text") String text);

    /**
     *得到评论列表内容
     * */
    @POST("getAllCheatByUser")
    Observable<ResponseBody> getMessage(@Query("userid") int userid,@Query("pagenum") int pagenum) ;

    /**
     * 得到粉丝列表内容（谁关注了谁）
     * */
    @POST("findUserFens")
    Observable<ResponseBody> getFens(@Query("userid") int userid) ;


    @POST("changepassward")
    Observable<ResponseBody> changeKey(@Query("id") int userID,@Query("oldpassward") String oldKey, @Query("newpassward") String newKey);

    /**
     * 销毁账户*
     */
    @POST("destroy")
    Observable<ResponseBody> destroyAccountEnter(@Query("userid") int userID);



    /**
     * 获取我的点赞列表
     */
    @GET("finduserlike")
    Observable<ResponseBody> getAgreementList(@Query("userid") int userID);

    /**
     * 获取我的评论列表
     */
    @GET("getUserCheat")
    Observable<ResponseBody> getCommentList(@Query("userid") int userID);

    /**
     *修改用户头像
     * */
    @Multipart
    @POST("changeuserimage")
    Observable<ResponseBody> changeuserimage(@Query("userid") int userID,@Part MultipartBody.Part file);

    /**
     * 修改用户信息
     */
    @POST("update")
    Observable<ResponseBody> changeInfo(@Query("id") int userID,@Query("age") int age,@Query("sign") String sign, @Query("sex") String sex);

    /**
     *修改背景图片
     * */
    @Multipart
    @POST("changeback1image")
    Observable<ResponseBody> changeback1image(@Query("userid") int userID,@Part MultipartBody.Part file);

    /**
     * 添加文集
     * */
    @Multipart
    @POST("addwenji")
    Observable<ResponseBody> uploadwenji(@Query("userid") int userID,@Query("name") String name,@Query("haspic") int haspic,@Part MultipartBody.Part file);


    /**
     * 取消关注
     */
    @POST("minusFollow")
    Observable<ResponseBody> cancelFollow(@Query("userid") int userID,@Query("followerid") int followerID);

    /**
     * 删除
     */
    @POST("delect")
    Observable<ResponseBody> deleteContent(@Query("id") int id);

    /**
     * 获取文集细节内容
     */
    @GET("findwenjiByWenjiid")
    Observable<ResponseBody> getGroupDetail(@Query("wenjiid")int groupID,@Query("userid")int userid);

    /**
     * 获取音乐集内容
     */
    @GET("findByuser")
    Observable<ResponseBody> getMyMusic(@Query("userid") int userID);

    /**
     * 删除音乐
     */
    @GET("delete")
    Observable<ResponseBody> deleteMusic(@Query("musicid") int musicId);

}
