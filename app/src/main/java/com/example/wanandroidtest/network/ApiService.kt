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
    ):ApiResponse<LoginBean>
    /**
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
     *  banner
     */
    @GET("banner/json")
    suspend fun banner():ApiResponse<ArrayList<BannerBean>>
    /**
     * 首页文章列表
     */
    @GET("article/list/0/json")
    suspend fun  getArrayList(@Path("page")page:Int):ApiResponse<ApiPagerRestponse<ArrayList<HomeBean>>>
    /**
     * 首页置顶文章
     */
    @GET("article/top/json")
    suspend fun  getHomeTopArticle():ApiResponse<ArrayList<HomeBean>>
}