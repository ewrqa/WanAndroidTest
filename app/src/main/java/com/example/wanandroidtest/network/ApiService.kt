package com.example.wanandroidtest.network

import com.example.wanandroidtest.data.bean.*
import retrofit2.http.*

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.network</p>
 * <p>简述:接口内容荣</p>
 *
 * @author 张凯涛
 * @date 2024/8/5
 */
interface ApiService {
    companion object{
        //url  编译时  常量  不可被修改
        const val URL="https://www.wanandroid.com/"
    }
    /**
     *  登录接口
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username")username:String,
        @Field("password")password:String
    ):ApiResponse<UserInfoBean>
    /**·
     *  注册接口
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username")username: String,
        @Field("password")password: String,
        @Field("repassword")repassword: String,
    ):ApiResponse<LoginBean>
    /**
     *  个人中心积分与排名
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getIntegral(): ApiResponse<IntegralBean>

    /**
     *  banner
     */
    @GET("banner/json")
    suspend fun banner(): ApiResponse<ArrayList<BannerBean>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getArrayList(@Path("page") page: Int): ApiResponse<ApiPagerRestponse<ArrayList<HomeBean>>>

    /**
     * 首页置顶文章
     */
    @GET("article/top/json")
    suspend fun getHomeTopArticle(): ApiResponse<ArrayList<HomeBean>>

    /**X
     *  获取收藏文章列表数据
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): ApiResponse<ApiPageBean<ArrayList<CollectBean>>>

    /**
     * 获取收藏网址列表数据
     */
    @GET("/lg/collect/usertools/json")
    suspend fun getCollectUrl(): ApiResponse<ArrayList<CollectUrlBean>>

    /**
     * 收藏 与取消收藏
     * 文章与网址
     */
    @POST("lg/collect/{id}/json")
    suspend fun setCollect(@Path("id") id: Int): ApiResponse<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun setUnCollect(@Path("id") id: Int): ApiResponse<Any?>

    //收藏网址
    @POST("lg/collect/addtool/json")
    suspend fun setUrlCollect(
        @Path("name") name: String,
        @Path("link") link: String,
    ): ApiResponse<CollectUrlResponse>

    //取消收藏网址
    @POST("lg/collect/deletetool/json")
    suspend fun setUrlUnCollect(@Path("id") id: Int): ApiResponse<Any?>

    /**
     * 获取热门搜索数据
     */
    @GET("hotkey/json")
    suspend fun getSearchData(): ApiResponse<ArrayList<HotSelectBean>>

    /**
     * 搜索相关内容
     */
    @POST("article/query/{page}/json")
    suspend fun getSearchDataByKey(
        @Path("page") page: Int,
        @Query("k") key: String,
    ): ApiResponse<ApiPageBean<ArrayList<HomeBean>>>
}