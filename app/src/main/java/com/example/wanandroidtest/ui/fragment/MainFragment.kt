package com.example.wanandroidtest.ui.fragment
import android.os.Bundle
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.FragmentMainBinding
import com.example.wanandroidtest.ext.init
import com.example.wanandroidtest.ext.initMain
import com.example.wanandroidtest.viewmodel.state.MainViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment</p>
 * <p>简述:主页的fragment</p>
 *
 * @author 张凯涛
 * @date 2024/8/4
 */
class MainFragment :BaseFragment<MainViewModel,FragmentMainBinding>(){
    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager2

        myDataBinding.mainViewpager.initMain(this)
        myDataBinding.mainBottom.init {
            when(it){
                R.id.menu_main ->myDataBinding.mainViewpager.setCurrentItem(0, false)
                R.id.menu_project ->myDataBinding. mainViewpager.setCurrentItem(1, false)
                R.id.menu_system ->myDataBinding. mainViewpager.setCurrentItem(2, false)
                R.id.menu_public ->myDataBinding. mainViewpager.setCurrentItem(3, false)
                R.id.menu_me ->myDataBinding.mainViewpager.setCurrentItem(4, false)
            }
        }
    }
    override fun layoutId(): Int {
        return  R.layout.fragment_main
    }

}