package com.example.shoujiedemo.apiInterface;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
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
    Observable<ResponseBody> loadHotContent(@Query("pagenum") int pageNum);

    /**
     * 加载内容
     * @return
     */
    @GET("find")
    Observable<ResponseBody> loadContent(@Query("typeid") int typeId,@Query("pagenum") int page);

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
     * 评论
     * @return
     */
    @GET("findFollows")
    Observable<ResponseBody> comment(@Query("userid") int userId,@Query("pagenum") int pageNum);

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
    @GET("addFollow")
    Observable<ResponseBody> diFfollow(@Query("userid") int userId,@Query("followerid") int followerId);


    /**
     * 上传内容
     * */
    @GET("add")
    Observable<ResponseBody> upload(@Query("title") String title,@Query("text") String text,@Query("userid") int userid,@Query("typeid") int typeid,@Query("writer") String writer,@Query("tag") String tag,@Query("wenjiid")int wenjiid,@Query("isoriginal")int isoriginal);



}
