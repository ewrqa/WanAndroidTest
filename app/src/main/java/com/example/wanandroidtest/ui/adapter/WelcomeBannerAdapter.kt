package com.example.wanandroidtest.ui.adapter

/**
 * 作者　: hegaojian
 * 时间　: 2020/2/20
 * 描述　:
 */

import android.view.View
import com.example.wanandroidtest.R
import com.example.wanandroidtest.weight.banner.WelacomeBannerViewHolder
import com.zhpan.bannerview.BaseBannerAdapter

class WelcomeBannerAdapter() :BaseBannerAdapter<String,WelacomeBannerViewHolder>(){
    //连接显示的布局
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemwelcome
    }

    override fun createViewHolder(p0: View, p1: Int): WelacomeBannerViewHolder {
        return  WelacomeBannerViewHolder(p0)
    }

    override fun onBind(p0: WelacomeBannerViewHolder?, p1: String?, p2: Int, p3: Int) {
       p0?.bindData(p1,p2,p3)
    }
}

