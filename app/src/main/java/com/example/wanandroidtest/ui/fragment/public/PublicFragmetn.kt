package com.example.wanandroidtest.ui.fragment.public

import android.os.Bundle
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.PublicFragmentBinding
import com.example.wanandroidtest.viewmodel.state.public.PublicViewMode

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.public</p>
 * <p>简述:</p>
 *public 的fragment
 * @author 张凯涛
 * @date 2024/8/7
 */
class PublicFragmetn :BaseFragment<PublicViewMode,PublicFragmentBinding>(){
    override fun layoutId(): Int {
        return  R.layout.public_fragment
    }
    override fun initView(savedInstanceState: Bundle?) {

    }
}