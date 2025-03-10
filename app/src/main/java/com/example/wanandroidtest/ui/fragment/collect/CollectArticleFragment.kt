package com.example.wanandroidtest.ui.fragment.collect

import android.os.Bundle
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.FragmentCollectarticleBinding
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.collect</p>
 * <p>简述:收藏当中的文章收藏 </p>
 *
 * @author 张凯涛
 * @date 2025/2/28
 */
class CollectArticleFragment :BaseFragment<CollectViewModel,FragmentCollectarticleBinding>(){
    override fun layoutId(): Int {
        return  R.layout.fragment_collectarticle
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}