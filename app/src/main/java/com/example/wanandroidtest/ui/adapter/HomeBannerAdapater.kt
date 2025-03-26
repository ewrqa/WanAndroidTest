package com.example.wanandroidtest.ui.adapter
import android.view.View
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.BannerBean
import com.example.wanandroidtest.weight.banner.HomeBannerVIewHolder
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.adapater</p>
 * <p>简述:</p>
 *首页banner的 adapater
 * @author 张凯涛
 * @date 2024/8/8
 */
class HomeBannerAdapater :BaseBannerAdapter<BannerBean,HomeBannerVIewHolder>(){
    override fun createViewHolder(p0: View, p1: Int): HomeBannerVIewHolder {
        return  HomeBannerVIewHolder(p0)
    }

    override fun getLayoutId(p0: Int): Int {
        return R.layout.banner_itemhome
    }

    override fun onBind(p0: HomeBannerVIewHolder?, p1: BannerBean?, p2: Int, p3: Int) {
        p0?.bindData(p1,p2,p3)
    }
}
