package com.example.wanandroidtest.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.wanandroidtest.R
import com.example.wanandroidtest.appViewModel
import com.example.wanandroidtest.base.BaseActivity
import com.example.wanandroidtest.databinding.ActivityMainBinding
import com.example.wanandroidtest.util.StatusBarUtil
import com.example.wanandroidtest.viewmodel.state.MainViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.activity.main/p>
 * <p>简述:navigation架构的宿主 activity</p>
 *
 * @author 张凯涛
 * @date 2024/8/4
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(){
    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        appViewModel.appColor.value?.let {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0)
        }
        Navigation.findNavController(this, R.id.host_fragment)
    }

    override fun createObserver() {
        appViewModel.appColor.observeInActivity(this, Observer {
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0)
        })
    }
}