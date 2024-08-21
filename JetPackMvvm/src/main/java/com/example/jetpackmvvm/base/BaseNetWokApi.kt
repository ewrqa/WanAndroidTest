package com.example.jetpackmvvm.base

import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.base</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/5
 */
abstract  class BaseNetWokApi {

        fun <T> getApi(serviceClass:Class<T>,baseUrl:String):T{
            val client = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
            return  setRetrofitBuilder(client).build().create(serviceClass)
        }

    abstract  fun  setHttpClientBuilder(bulider:OkHttpClient.Builder):OkHttpClient.Builder

    abstract  fun  setRetrofitBuilder(bulider:Retrofit.Builder):Retrofit.Builder

    /**
     * 配置http
     */
    private val okHttpClient: OkHttpClient
        get() {
            var builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
            builder = setHttpClientBuilder(builder)
            return builder.build()
        }

}