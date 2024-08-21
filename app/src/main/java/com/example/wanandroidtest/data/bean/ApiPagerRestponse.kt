package com.example.wanandroidtest.data.bean

import java.io.Serializable

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.bean</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/8
 */
/**
 * @param datas  数据源
 * @param curPage  当前页码
 * @param offset   页码的偏移量
 * @param over   是否还有更多数据
 * @param pageCount 总页码
 * @param  size  页码中的数量
 * @param total  总数据量
 */
data  class ApiPagerRestponse<T>(
    var datas :T,
    var curPage:Int,
    var offset:Int,
    var over:Boolean,
    var pageCount:Int,
    var  size:Int,
    var total:Int,
):Serializable{
    /**
     *  数据是否为空
     */
    fun isEmpty()=(datas as List<*>).size==0
    /**
     * 是否刷新
     */
    fun isRefresh()=offset ==0

    /**
     * 是否还有更多信息
     */
    fun hasMore()= !over
}