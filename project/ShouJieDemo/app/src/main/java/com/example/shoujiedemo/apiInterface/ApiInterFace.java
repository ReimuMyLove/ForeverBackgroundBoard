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


}
