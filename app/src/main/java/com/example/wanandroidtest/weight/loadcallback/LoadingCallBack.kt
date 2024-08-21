package com.example.wanandroidtest.weight.loadcallback

import android.content.Context
import android.view.View
import com.example.wanandroidtest.R
import com.kingja.loadsir.callback.Callback

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.loadcallback</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/8
 */
class LoadingCallBack :Callback(){
    override fun onCreateView(): Int {

        return  R.layout.layout_loading
    }
    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }

}