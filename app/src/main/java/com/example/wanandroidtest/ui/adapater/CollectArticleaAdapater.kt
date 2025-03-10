package com.example.wanandroidtest.ui.adapater

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.CollectBean

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
    /**
     * 清除条目的类型有几个区分好
     */
    private val   Ariticle=1
    private val   Project=2

    /**
     * 设置代理类型
     * 重写该方法
     */
    init {
        /**
         * 设置条目的代理类型
         */
        setMultiTypeDelegate(object :BaseMultiTypeDelegate<CollectBean>(){
            //获取条目的类型
            override fun getItemType(data: List<CollectBean>, position: Int): Int {
                ///实体类中没有给出字段所以通过是否有照片来判断是什么
                //如果为空则是文章不为空则是带图标的项目条目
                return  if(TextUtils.isEmpty(data[position].envelopePic)) Ariticle else Project
            }
        })
        /**
         * 根据不同的条目绑定相对应的布局
         */
        getMultiTypeDelegate()?.let {

        }
    }
    override fun convert(holder: BaseViewHolder, item: CollectBean) {
        TODO("Not yet implemented")
    }

}