package com.example.wanandroidtest.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.CollectUrlBean
import com.example.wanandroidtest.weight.custom.CollectView
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.adapater</p>
 * <p>简述:收藏网址的adapter</p>
 *
 * @author 张凯涛
 * @date 2025/3/13
 */
class CollectUrlAdapter(data: ArrayList<CollectUrlBean>) :
    BaseQuickAdapter<CollectUrlBean, BaseViewHolder>(R.layout.item_collecturl, data) {
    override fun convert(holder: BaseViewHolder, item: CollectUrlBean) {
        item.run {
            //文本内容
            holder.setText(R.id.item_collect_url_message, name.toHtml())
            //网址
            holder.setText(R.id.item_collect_url_url, link.toHtml())
            //设置收藏的状态
            val view = holder.getView<CollectView>(R.id.item_collect_url_collect)
            view.isChecked = true
            //收藏的点击事件
            view.setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                override fun onClick(v: CollectView) {
                    collectUrlAction.invoke(item, v, holder.adapterPosition)
                }
            })
        }
    }

    //设置对外的接口回调
    private var collectUrlAction: (data: CollectUrlBean, v: CollectView, position: Int) -> Unit =
        { _: CollectUrlBean, _: CollectView, _: Int -> }

    fun setCollectUrlClick(block: (data: CollectUrlBean, v: CollectView, position: Int) -> Unit) {
        this.collectUrlAction = block
    }
}