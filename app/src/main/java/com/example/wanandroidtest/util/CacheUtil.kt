package com.example.wanandroidtest.util

import android.text.TextUtils
import com.example.wanandroidtest.data.bean.UserInfoBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.util</p>
 * <p>简述:状态存储的工具</p>
 *使用mmkv方式存储
 * @author 张凯涛
 * @date 2024/8/2
 */
object CacheUtil {
    /**
     *  获取存储账户信息
     */
    fun getUser(): UserInfoBean? {
        val kv = MMKV.mmkvWithID("app")
        val userString = kv.decodeString("user")
        return if (TextUtils.isEmpty(userString)) {
            null
        } else {
            //将数据反序列化存储
            Gson().fromJson(userString, UserInfoBean::class.java)
        }
    }

    /**
     *  存储账户信息
     */
    fun setUser(userInfoBean: UserInfoBean?) {
        val kv = MMKV.mmkvWithID("app")
        if (userInfoBean == null) {
            kv.encode("user", "")
            setLogin(false)
        } else {
            //存储序列化后的数据
            kv.encode("user", Gson().toJson(userInfoBean))
            setLogin(true)
        }
    }




    //记录是否是第一次进入该App
    fun getJoinAppFirst():Boolean{
        val kv = MMKV.mmkvWithID("app")
        return  kv.decodeBool("first",true)
    }
    //记录是否是第一次进入
    fun setJoinAppFirst(first:Boolean):Boolean{
        val kv = MMKV.mmkvWithID("app")
        return  kv.encode("first",first)
    }
    /**
      获取是否已经登录过  默认false
     */
    fun isLogin():Boolean{
        val mmkvWithID = MMKV.mmkvWithID("app")
       return  mmkvWithID.decodeBool("login")
    }

    /**
     *  记录登录状态
     */
    fun setLogin(first: Boolean) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("login", first)
    }

    /**
     *  记录是否置顶的状态
     */
    fun setNeedTop(setNeedTop: Boolean) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("top", setNeedTop)
    }

    /**
     * 置顶状态
     */
    fun getTopState(): Boolean {
        return MMKV.mmkvWithID("app").getBoolean("top", true)
    }

    /**
     * 存储和获取搜索历史
     */
    fun getSearchHistory(): ArrayList<String> {
        val mmkvWithID = MMKV.mmkvWithID("search")
        val decodeString = mmkvWithID.decodeString("searchhistory")
        if (!TextUtils.isEmpty(decodeString)) {
            return Gson().fromJson(decodeString, object : TypeToken<ArrayList<String>>() {}.type)
        }
        return arrayListOf()
    }

    fun setSearchHistory(history: String) {
        val mmkvWithID = MMKV.mmkvWithID("search")
        mmkvWithID.encode("searchhistory", history)
    }

    /**
     * 清空
     */
    fun clearSearchHistory() {
        MMKV.mmkvWithID("search").clear()
    }
}
