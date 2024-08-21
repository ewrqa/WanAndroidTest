package com.example.jetpackmvvm.callback.databing

import androidx.databinding.ObservableField

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.callback.databing</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/3
 */
class DoubleObservable(value:Double=0.0): ObservableField<Double>(value){
    override fun get(): Double? {
        return super.get()
    }
}