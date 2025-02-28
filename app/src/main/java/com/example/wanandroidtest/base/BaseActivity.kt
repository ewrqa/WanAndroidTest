package com.example.wanandroidtest.base
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.jetpackmvvm.base.BaseDbActvivity
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.wanandroidtest.ext.dismissLoadingExt
import com.example.wanandroidtest.ext.showLoadingEXT
/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.base</p>
 * <p>简述:activity的基类</p>
 * @author 张凯涛
 * @date 2024/8/1
 */
abstract  class BaseActivity<VM : BaseViewModel,DB:ViewDataBinding>:BaseDbActvivity<VM,DB>(){
    abstract override fun initView(savedInstanceState: Bundle?)
    //初始化观察者
    override fun createObserver() {}
    //显示网络加载的框
    override fun showLoading(message: String) {
        showLoadingEXT(message)
    }
    override fun dismissLoading() {
        dismissLoadingExt()
    }
}