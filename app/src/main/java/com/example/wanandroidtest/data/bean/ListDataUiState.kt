package com.example.wanandroidtest.data.bean

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.bean</p>
 * <p>简述:</p>
 *  列表数据状态类
 * @author 张凯涛
 * @date 2024/8/8
 */
data class ListDataUiState<T>(
    val isSuccess: Boolean,  //是否请求成功
    val errMessage: String = "",//错误信息
    val isRefresh: Boolean = false,//是否刷新
    val isEmpty: Boolean = false,//是否为空
    val hasMore: Boolean = false, //是否有更多
    val isFirstEmpty: Boolean = false,  //是否是第一页且没有数据
    val listData: ArrayList<T> = arrayListOf(),
)
