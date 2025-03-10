package com.example.wanandroidtest.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.base</p>
 * <p>简述:</p>
 *用于获取全局的viewmodel viewmodel的生命周期与application一致
 * 适合存储长期的数据和多个模块公共的数据如 用户信息与配置信息等
 * open 是继承
 * @author 张凯涛
 * @date 2025/2/28
 */
//实现ViewModelStoreOwner接口，提供了一个viewmodelStore对象，方便存储和管理viewmodel实例
open  class BaseApp :Application(),ViewModelStoreOwner{
    //lateinit减缓初始化的时间
    private lateinit var  mAppViewModelStore: ViewModelStore
    //mFactoru可为空
    private var mFactoru: ViewModelProvider.Factory?=null

    /**
     * 便于其他组件访问viewmodelstore
     */
    override fun getViewModelStore(): ViewModelStore {
        return  mAppViewModelStore
    }
    /**
     * 初始化操作  创建viewmodelStore对象
     */
    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore= ViewModelStore()
    }



    /**
     * 获取viewmodel
     */
    fun getAppViewmodelProvider():ViewModelProvider{
        //viewmodelprovideer是创建和管理viewmodel
        return  ViewModelProvider(this,this.getAppFactory())
    }
    /**
     *  AndroidViewModelFactory是获取AndroidViewModel的工厂类
     */
    private fun  getAppFactory():ViewModelProvider.Factory{
        if(mFactoru==null){
            mFactoru=ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
            return  mFactoru as ViewModelProvider.Factory

    }

}