package com.example.jetpackmvvm.callback.databing

import androidx.databinding.ObservableField

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.callback.databing</p>
 * <p>简述:</p>
 *自定义
 * @author 张凯涛
 * @date 2024/8/3
 */
open class  StringObservable(value:String=""): ObservableField<String>(value) {
    override fun get(): String{
        return super.get()!!
    }
}
