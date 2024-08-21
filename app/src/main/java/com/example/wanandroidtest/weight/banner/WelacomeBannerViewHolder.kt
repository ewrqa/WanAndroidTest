package com.example.wanandroidtest.weight.banner

import android.view.View
import android.widget.TextView
import com.example.wanandroidtest.R
import com.zhpan.bannerview.BaseViewHolder

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.banner</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/7
 */
class WelacomeBannerViewHolder(view:View) : BaseViewHolder<String>(view){
    override fun bindData(data: String?, position: Int, pageSize: Int) {
        val findViewById = itemView.findViewById<TextView>(R.id.banner_text)
        findViewById.text = data
    }
}