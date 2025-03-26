package com.example.wanandroidtest.ui.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.HomeBean
import com.example.wanandroidtest.weight.custom.CollectView
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.adapater</p>
 * <p>简述:首页列表数据适配器</p>
 * 传递给的参数
 * @author 张凯涛
 * @date 2025/3/13
 */
class HomeListAdapter(data: ArrayList<HomeBean>) :
    BaseDelegateMultiAdapter<HomeBean, BaseViewHolder>(data) {
    val Ariticle = 1
    val Project = 2

    //是否展示标签
    val isShowType: Boolean = true

    //设置动态代理区分文章还是项目  由于api没有明确的给出字段 所以通过又没有图片来进行区分是哪个类型
    init {
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<HomeBean>() {
            override fun getItemType(data: List<HomeBean>, position: Int): Int {
                return if (data[position].envelopePic.isEmpty()) Ariticle else Project
            }
        })
        //获取代理并连接对应的布局
        getMultiTypeDelegate()?.let {
            it.addItemType(Ariticle, R.layout.item_ariticle)
            it.addItemType(Project, R.layout.item_project)
        }
    }

    override fun convert(holder: BaseViewHolder, item: HomeBean) {
        when (holder.itemViewType) {
            Ariticle -> {
                item.run {
                    //作者名称
                    holder.setText(R.id.item_collect_author_name,
                        if (author.isEmpty()) "匿名用户" else author)
                    holder.setText(R.id.item_collect_time, niceDate)
                    holder.setText(R.id.item_collect_message, title)
                    holder.setText(R.id.item_collect_types, chapterName)
                    holder.setText(
                        R.id.item_collect_types,
                        "${superChapterName}·${chapterName}".toHtml()
                    )
                    holder.getView<CollectView>(R.id.item_collect_collect).isChecked = collect
                    //判断是否需要隐藏标签
                    if (isShowType) {
                        holder.setGone(R.id.item_collect_new, !fresh)
                        holder.setGone(R.id.item_collect_top, type != 1)
                        if (tags.isEmpty()) {
                            holder.setGone(R.id.item_collect_type, true)
                        } else {
                            holder.setGone(R.id.item_collect_type, false)
                            holder.setText(R.id.item_collect_types, tags[0].name)
                        }
                    } else {
                        holder.setGone(R.id.item_collect_top, true)
                        holder.setGone(R.id.item_collect_type, true)
                        holder.setGone(R.id.item_collect_new, true)
                    }
                    //设置收藏的点击事件
                    holder.getView<CollectView>(R.id.item_collect_collect)
                        .setOnCollectViewClickListener(object :
                            CollectView.OnCollectViewClickListener {
                            override fun onClick(v: CollectView) {
                                homeCollectClick.invoke(item, v, holder.adapterPosition)
                            }
                        })
                }
            }

            Project -> {
                item.run {
                    //作者名称
                    holder.setText(R.id.item_project_name, if (author.isEmpty()) "匿名用户" else author)
                    holder.setText(R.id.item_project_time, niceDate)
                    holder.setText(R.id.item_project_message, title)
                    holder.setText(R.id.item_project_types, chapterName)
                    holder.setText(
                        R.id.item_project_types,
                        "${superChapterName}·${chapterName}".toHtml()
                    )
                    holder.getView<CollectView>(R.id.item_project_collect).isChecked = collect
                    //判断是否需要隐藏标签
                    if (isShowType) {
                        holder.setGone(R.id.item_project_new, !fresh)
                        holder.setGone(R.id.item_project_top, type != 1)
                        if (tags.isEmpty()) {
                            holder.setGone(R.id.item_project_type, true)
                        } else {
                            holder.setGone(R.id.item_project_type, false)
                            holder.setText(R.id.item_project_types, tags[0].name)
                        }
                    } else {
                        holder.setGone(R.id.item_project_top, true)
                        holder.setGone(R.id.item_project_type, true)
                        holder.setGone(R.id.item_project_new, true)
                    }
                    Glide.with(context)
                        .load(item.envelopePic)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(holder.getView(R.id.item_project_image))

                    //设置收藏的点击事件
                    holder.getView<CollectView>(R.id.item_project_collect)
                        .setOnCollectViewClickListener(object :
                            CollectView.OnCollectViewClickListener {
                            override fun onClick(v: CollectView) {
                                homeCollectClick.invoke(item, v, holder.adapterPosition)
                            }
                        })
                }

            }
        }
    }

    private var homeCollectClick: (item: HomeBean, v: CollectView, position: Int) -> Unit =
        { _: HomeBean, _: CollectView, _: Int -> }

    //创建对外的点击事件
    fun setHomeCollectClick(block: (item: HomeBean, v: CollectView, position: Int) -> Unit) {
        this.homeCollectClick = block
    }
}