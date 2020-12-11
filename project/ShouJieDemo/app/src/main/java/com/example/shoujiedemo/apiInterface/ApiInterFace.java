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
     * 搜索
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
     * 取消关注
     */
    @POST("minusFollow")
    Observable<ResponseBody> cancelFollow(@Query("userid") int userID,@Query("followid") int followerID);

    /**
     * 获取关注人列表
     */
    @POST("findUserFollows")
    Observable<ResponseBody> findFollow(@Query("userid") int userID);

    /**
     * 关注
     */
    @GET("addfollow")
    Observable<ResponseBody> loadByTime(@Query("date") String date);


}
