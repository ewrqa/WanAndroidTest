package com.example.wanandroidtest.data.bean

import java.io.Serializable

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.bean</p>
 * <p>简述:分页数据的基类</p>
 *
 * @author 张凯涛
 * @date 2025/3/5
 */
data class ApiPageBean<T>(
    //具体的数据
    var  datas:T,
    //当前的页数
    var  curPage:Int,
    //从第几条数据开始返回
    var  offest:Int,
    //是否加载完数据
    var  over:Boolean,
    //页数总和
    var pageCount: Int,
    //每页数据总和
    var size: Int,
    //所有数据总和
    var total: Int
    //将其序列化便于传递
):Serializable{
    /**
     * 是否数据为空
     * 强转为list判断总数是否为空
     */
    fun isEmpty() =(datas as List<*>).size==0

    /**
     *   是否进行刷新
     *   如果为0则表示数据下标为0则刷新了
     */
    fun isRefersh() =offest==0

    /**
     * 是否还有其他数据
     */
    fun  hasMore()=!over



}