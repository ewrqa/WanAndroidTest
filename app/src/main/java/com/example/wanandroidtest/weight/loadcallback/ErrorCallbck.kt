package com.example.wanandroidtest.weight.loadcallback

import com.example.wanandroidtest.R
import com.kingja.loadsir.callback.Callback

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.loadcallback</p>
 * <p>简述:loadservice的失败页面</p>
 *
 * @author 张凯涛
 * @date 2025/3/12
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}