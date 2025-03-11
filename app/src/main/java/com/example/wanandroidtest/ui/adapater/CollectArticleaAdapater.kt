package com.example.wanandroidtest.ui.adapater

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.CollectBean
import com.example.wanandroidtest.weight.custom.CollectView
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.adapater</p>
 * <p>简述:收藏中文章的适配器</p>
 * BaseDelegateMultiAdapter 是一个完全适配adapter的基类
 * 多布局adapater可以通过代理的方式返回布局的id 和item类型
 * 适用于实体类不方便拓展（之前java 的写过将两个实体类合并成一个数据类）与条目类型不多的
 * @author 张凯涛
 * @date 2025/3/9
 */
class CollectArticleaAdapter(data:ArrayList<CollectBean>)
    :BaseDelegateMultiAdapter<CollectBean,BaseViewHolder>(data){
    private  val Ariticle=1
    private  val Project=2
    private  var  collectAction:(item:CollectBean,view:CollectView,position:Int)->Unit=
        {_:CollectBean,_:CollectView,_:Int->}

    /**
     * 设置代理
     */
     init {
         setMultiTypeDelegate(object:BaseMultiTypeDelegate<CollectBean>(){
             override fun getItemType(data: List<CollectBean>, position: Int): Int {
                 return  if(data[position].envelopePic.isEmpty()) Ariticle else Project
             }
         })

        getMultiTypeDelegate()?.let {
            it.addItemType(Ariticle,R.layout.item_ariticle)
            it.addItemType(Project,R.layout.item_project)
        }
     }
    override fun convert(holder: BaseViewHolder, item: CollectBean) {
        when(holder.itemViewType){
            Ariticle->{
                item.run {
                    holder.setText(R.id.item_collect_author_name,if (author.isEmpty())"匿名用户"  else item.author)
                    holder.setText(R.id.item_collect_time,niceDate)
                    holder.setText(R.id.item_collect_message,title)
                    holder.setText(R.id.item_collect_types,chapterName)
                    holder.getView<CollectView>(R.id.item_collect_collect).isChecked=true
                    //隐藏
                    holder.setGone(R.id.item_collect_top,true)
                    holder.setGone(R.id.item_collect_type,true)
                    holder.setGone(R.id.item_collect_new,true)
                    holder.getView<CollectView>(R.id.item_collect_collect).setOnCollectViewClickListener(
                        object :CollectView.OnCollectViewClickListener{
                            override fun onClick(v: CollectView) {
                                collectAction.invoke(item,v,holder.adapterPosition)
                            }
                        }
                    )
                }
                }
            Project->{
                item.run {

                    holder.setText(R.id.item_project_name,if (author.isEmpty())"匿名用户"  else item.author)
                    holder.setText(R.id.item_project_time,niceDate)
                    holder.setText(R.id.item_project_message,title)
                    holder.setText(R.id.item_project_types,chapterName)
                    holder.getView<CollectView>(R.id.item_project_collect).isChecked=true
                    Glide.with(context).load(envelopePic)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(holder.getView(R.id.item_project_image))
                    //隐藏
                    holder.setGone(R.id.item_project_top,true)
                    holder.setGone(R.id.item_project_type,true)
                    holder.setGone(R.id.item_project_new,true)

                    holder.getView<CollectView>(R.id.item_project_collect).setOnCollectViewClickListener(object :CollectView.OnCollectViewClickListener{
                        override fun onClick(v: CollectView) {
                            collectAction.invoke(item,v,holder.adapterPosition)
                        }

                    })
                }
            }
        }
    }
    /**
     * 收藏的点击事件
     */
    fun  setCollectClick(inputCollectAction:(item:CollectBean,v:CollectView,position:Int)->Unit){
        this.collectAction=inputCollectAction
    }
}



//    :BaseDelegateMultiAdapter<CollectBean,BaseViewHolder>(data){
//    /**
//     * 清除条目的类型有几个区分好
//     */
//    private val   Ariticle=1
//    private val   Project=2
//    /**
//     * 设置代理类型
//     * 重写该方法
//     */
//    init {
//        /**
//         * 设置条目的代理类型
//         */
//        setMultiTypeDelegate(object :BaseMultiTypeDelegate<CollectBean>(){
//            //获取条目的类型
//            override fun getItemType(data: List<CollectBean>, position: Int): Int {
//                ///实体类中没有给出字段所以通过是否有照片来判断是什么
//                //如果为空则是文章不为空则是带图标的项目条目
//                return  if(TextUtils.isEmpty(data[position].envelopePic)) Ariticle else Project
//            }
//        })
//        /**
//         * 根据不同的条目绑定相对应的布局
//         */
//        getMultiTypeDelegate()?.let {
//            it.addItemType(Ariticle,R.layout.item_ariticle)
//            it.addItemType(Project,R.layout.item_project)
//        }
//    }
//    override fun convert(holder: BaseViewHolder, item: CollectBean) {
//       //判断是那种的类型
//        when(holder.itemViewType){
//            //设置参数
//            Ariticle->{
//              holder.setText(R.id.item_collect_author_name,item.author)
//
//            }
//            Project->{
//
//            }
//
//        }
//    }
//}