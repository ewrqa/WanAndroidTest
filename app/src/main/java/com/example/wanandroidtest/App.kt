package com.example.wanandroidtest

import android.app.Application
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
class App :Application(){
    override fun onCreate() {
        //初始化的操作
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")

        LoadSir.beginBuilder()
            .addCallback(LoadingCallBack())
            .commit()
    }
}