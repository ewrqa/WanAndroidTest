package com.example.jetpackmvvm.base

import androidx.lifecycle.ViewModel
import com.example.jetpackmvvm.callback.livedata.EventLiveData

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.base</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/7
 */
open class BaseViewModel :ViewModel(){

    //通过属性委托的方式来 实现  提高 对该封装的使用
    val loadingChange: UiLoadingChange by  lazy { UiLoadingChange() }
    /**
     * 根据网络请求的状态 来  显示隐藏和
     */
    inner  class  UiLoadingChange{

        val  showDialog by lazy { EventLiveData<String>() }

        val  dismissDialog by  lazy { EventLiveData<Boolean>() }

    }
}