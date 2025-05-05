package com.example.wanandroidtest

import com.example.wanandroidtest.base.BaseApp
import com.example.wanandroidtest.event.AppViewModel
import com.example.wanandroidtest.event.EventViewModel
import com.example.wanandroidtest.weight.loadcallback.LoadingCallBack
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/8
 */
//通过属性委托的 方式
val appViewModel: AppViewModel by lazy { App.appViewModelInstance }
val eventViewModel: EventViewModel by lazy { App.eventViewModel }

class App : BaseApp() {
    /**
     * 提供静态成员
     */
    companion object {
        lateinit var instance: App
        lateinit var appViewModelInstance: AppViewModel
        lateinit var eventViewModel: EventViewModel
    }

    override fun onCreate() {
        super.onCreate()
        //初始化mmkv
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        instance = this
        //获取全局的viewmodel
        appViewModelInstance = getAppViewmodelProvider().get(AppViewModel::class.java)
        //获取全局的viewmodel便于收藏与取消的传递
        eventViewModel = getAppViewmodelProvider().get(EventViewModel::class.java)
        //加载状态库
        LoadSir.beginBuilder()
            .addCallback(LoadingCallBack())//加载
            .commit()
    }
}