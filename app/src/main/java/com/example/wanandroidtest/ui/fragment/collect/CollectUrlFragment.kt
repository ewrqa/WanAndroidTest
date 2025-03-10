package com.example.wanandroidtest.ui.fragment.collect

import android.os.Bundle
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.FragmentCollecturlBinding
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.collect</p>
 * <p>简述: 收藏界面的 网址收藏</p>
 *
 * @author 张凯涛
 * @date 2025/2/28
 */
class CollectUrlFragment :BaseFragment<CollectViewModel,FragmentCollecturlBinding>(){
    override fun layoutId(): Int {
        return  R.layout.fragment_collecturl
    }
    override fun initView(savedInstanceState: Bundle?) {
    }
}