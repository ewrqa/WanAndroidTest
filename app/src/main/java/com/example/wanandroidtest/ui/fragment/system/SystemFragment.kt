package com.example.wanandroidtest.ui.fragment.system

import android.os.Bundle
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.SystemFragmentBinding
import com.example.wanandroidtest.viewmodel.state.system.SystemViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.system</p>
 * <p>简述:</p>
 * 广场的fragment
 * @author 张凯涛
 * @date 2024/8/7
 */
class SystemFragment :BaseFragment<SystemViewModel,SystemFragmentBinding>(){
    override fun layoutId(): Int {
        return  R.layout.system_fragment
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}