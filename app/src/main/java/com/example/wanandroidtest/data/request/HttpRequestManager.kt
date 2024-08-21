package com.example.wanandroidtest.data.request

import com.example.jetpackmvvm.network.AppException
import com.example.wanandroidtest.data.bean.ApiPagerRestponse
import com.example.wanandroidtest.data.bean.ApiResponse
import com.example.wanandroidtest.data.bean.HomeBean
import com.example.wanandroidtest.data.bean.LoginBean
import com.example.wanandroidtest.network.apiservice
import com.example.wanandroidtest.util.CacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.request</p>
 * <p>简述:</p>
 * 特殊需求 因为首页列表内容 与 列表置顶文章需同时获取
 * 通过使用协成的
 * @author 张凯涛
 * @date 2024/8/8
 */
//  顶层方法 提供对外的快捷方法
/**
 * 属性委托
 */
val  httpRequestManager:HttpRequestManager by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
    HttpRequestManager()
}
class HttpRequestManager {

    /**
     * 首页文章数据和置顶文章数据
     */
    suspend fun  getHomeData(page:Int):ApiResponse<ApiPagerRestponse<ArrayList<HomeBean>>>{
        return  withContext( Dispatchers.IO){
            val listData= async { apiservice.getArrayList(page) }
            if(CacheUtil.getTopState() && page==0){
                val listTopData= async { apiservice.getHomeTopArticle() }
                //列表数据请求完毕之后 将置顶数据添加早 列表数据的头部
                listData.await().data.datas.addAll(0,listTopData.await().data)
                listData.await()
            }else{
                listData.await()
            }
        }
    }

    /**
     *  注册并登录的接口
     */
    suspend fun  registerAndLogin(username:String,password:String,repassword:String):ApiResponse<LoginBean>{
        val register = apiservice.register(username, password, repassword)
        if(register.isSuccess()){
            return apiservice.login(username, password)
        }else{
            throw AppException(register.errorCode,register.errorMsg)
        }

    }
}