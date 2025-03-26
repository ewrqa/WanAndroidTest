package com.example.wanandroidtest.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.HotSelectBean
import com.example.wanandroidtest.util.ColorUtil

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.adapater</p>
 * <p>简述:热搜词汇的适配器</p>
 *
 * @author 张凯涛
 * @date 2025/3/19
 */
class HotSelectAdapter(data: ArrayList<HotSelectBean>) : BaseQuickAdapter<HotSelectBean,
        BaseViewHolder>(R.layout.item_hotselect, data) {
    //设置该适配器的点击事件
    override fun convert(holder: BaseViewHolder, item: HotSelectBean) {
        holder.getView<TextView>(R.id.item_hot).text = item.name
        holder.getView<TextView>(R.id.item_hot).setTextColor(ColorUtil.randomColor())
        holder.getView<TextView>(R.id.item_hot).setOnClickListener {
            hotSelectClick.invoke(item, it, holder.adapterPosition)
        }
    }

    private var hotSelectClick: (item: HotSelectBean, view: View, position: Int) -> Unit =
        { _: HotSelectBean, _: View, _: Int ->
        }

    fun setHotSelectClick(black: (item: HotSelectBean, view: View, position: Int) -> Unit) {
        this.hotSelectClick = black
    }
}