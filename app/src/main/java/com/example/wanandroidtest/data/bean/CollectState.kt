package com.example.wanandroidtest.data.bean

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.bean</p>
 * <p>简述:收藏与取消收藏的状态</p>
 *
 * @author 张凯涛
 * @date 2025/3/14
 */
data class CollectStateBean(
    //是否成功
    var isSuccess: Boolean = true,
    //收藏与取消收藏
    var isCollect: Boolean = true,
    //收藏id
    var isID: Int = -1,
    //失败原因
    var errorMsg: String = "",
)
