package com.example.wanandroidtest.network
import com.example.jetpackmvvm.base.BaseNetWokApi
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import me.hgj.jetpackmvvm.base.appContext
import me.hgj.jetpackmvvm.network.interceptor.CacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.network</p>
 * <p>简述:网络工具类的请求</p>
 *
 * @author 张凯涛
 * @date 2024/8/5
 */
//对外 提供快速调用的方法
val apiservice :ApiService by lazy(mode =  LazyThreadSafetyMode.SYNCHRONIZED){
    NetWorkApi.INSTANCE.getApi(ApiService::class.java,ApiService.URL)
}
class NetWorkApi :BaseNetWokApi(){
    //对外之后一个实例
    companion object{
        //懒加载一个创建  使用同步锁的方式确保只在一个线程里 初始化
        val INSTANCE :NetWorkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
                NetWorkApi()
        }
    }
    override fun setHttpClientBuilder(bulider: OkHttpClient.Builder): OkHttpClient.Builder {
       //进行设置拦截器还有一些请求延时
        return  bulider.apply {
            //设置缓存的时间  可以减少网络请求的压力  及缓存的目录  范围大小
            cache(Cache(File(appContext.cacheDir,"wanandroid_cache"),10*1024*1024))
            //类似于通过sp的存储 登录的状态
            cookieJar(cookieJar)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(MyHeadInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
        }
    }
    override fun setRetrofitBuilder(bulider: Retrofit.Builder): Retrofit.Builder {
        //使用gson解析
        return  bulider.apply {
            addConverterFactory(GsonConverterFactory.create())
        }
    }
    /**
     * 持久化存储
     */
    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }
}