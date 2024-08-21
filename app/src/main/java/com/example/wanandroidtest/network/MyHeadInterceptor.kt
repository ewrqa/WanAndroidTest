package com.example.wanandroidtest.network

import com.example.wanandroidtest.util.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.network</p>
 * <p>简述自定义头参 拦截器 登录会用</p>
 *
 * @author 张凯涛
 * @date 2024/8/5
 */
class MyHeadInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBody = chain.request().newBuilder()
        newBody.addHeader("token", "token123456").build()
        newBody.addHeader("device", "Android").build()
        newBody.addHeader("isLogin", CacheUtil.isLogin().toString()).build()
        return  chain.proceed(newBody.build())
    }
}