package com.example.wanandroidtest.weight.loadcallback


import com.example.wanandroidtest.R
import com.kingja.loadsir.callback.Callback


/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.loadcallback</p>
 * <p>简述:LoadService加载状态管理 空页面</p>
 *
 * @author 张凯涛
 * @date 2025/3/12
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }
}