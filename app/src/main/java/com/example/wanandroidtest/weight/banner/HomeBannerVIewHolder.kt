package com.example.wanandroidtest.weight.banner

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.BannerBean
import com.zhpan.bannerview.BaseViewHolder
import me.hgj.jetpackmvvm.base.appContext

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.banner</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/8
 */
class HomeBannerVIewHolder(view:View):BaseViewHolder<BannerBean>(view){
    override fun bindData(data: BannerBean?, position: Int, pageSize: Int) {
        val bannerView = itemView.findViewById<ImageView>(R.id.bannerhome_img)
        data?.let {
            Glide.with(appContext)
                .load(it.data.get(0).imagePath)
                .into(bannerView)
        }
    }
}