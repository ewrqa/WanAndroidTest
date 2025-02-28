package com.example.wanandroidtest

import android.app.Application
import com.example.wanandroidtest.base.BaseApp
import com.example.wanandroidtest.event.AppViewModel
import com.example.wanandroidtest.weight.loadcallback.LoadingCallBack
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/8
 */
//创建对外的方法
val appViewModel :AppViewModel by lazy {App.appViewModelInstance }
class App :BaseApp(){
    /**
     * 提供静态成员
     */
    companion object{
        lateinit var  instance :App
        lateinit var appViewModelInstance: AppViewModel
    }
    override fun onCreate() {
        super.onCreate()
        //初始化mmkv
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        instance=this
        //获取全局的viewmodel
         appViewModelInstance = getAppViewmodelProvider().get(AppViewModel::class.java)
        //加载状态库
        LoadSir.beginBuilder()
            .addCallback(LoadingCallBack())//加载
            .commit()
    }
}