package com.example.wanandroidtest.base
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.jetpackmvvm.base.BaseDbFragment
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.wanandroidtest.ext.dismissLoadingExt
import com.example.wanandroidtest.ext.showLoadingExt


/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.base</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/4
 */
abstract  class BaseFragment <VM: BaseViewModel,DB:ViewDataBinding>: BaseDbFragment<VM, DB>(){
  abstract  override fun initView(savedInstanceState: Bundle?)
   override fun lazyData() {}
    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}
    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {}
    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }
    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }
    override fun onPause() {
        super.onPause()
    }

}