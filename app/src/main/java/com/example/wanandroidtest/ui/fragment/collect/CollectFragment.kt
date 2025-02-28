package com.example.wanandroidtest.ui.fragment.collect

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jetpackmvvm.util.nav
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.FragmentCollectBinding
import com.example.wanandroidtest.ext.bindViePager2
import com.example.wanandroidtest.ext.init
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel
import com.tencent.bugly.proguard.r

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.collect</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2025/2/25
 */
class CollectFragment :BaseFragment<CollectViewModel,FragmentCollectBinding>(){
    //导航栏文本
    var titleData= arrayListOf("文章","网址")
    override fun layoutId(): Int {
        return R.layout.fragment_collect
    }

    //存储fragment的集合
    private  var  fragments :ArrayList<Fragment> = arrayListOf()

    //初始化 添加fragment与集合当中
    init {
        fragments.add(CollectUrlFragment())
        fragments.add(CollectArticleFragment())

    }
    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager2
        myDataBinding.collectViewPager.init(this,fragments)
        //初始化导航栏 文本及线条样式
        myDataBinding.collectViewpagerIndicator.bindViePager2(myDataBinding.collectViewPager,titleData)
        //设置文本
        myDataBinding.includeToolbar.toolbar.setTitle("")
        //设置图标
        myDataBinding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        //点击事件的跳转
        myDataBinding.includeToolbar.toolbar.setNavigationOnClickListener { nav().navigateUp()}

    }
}