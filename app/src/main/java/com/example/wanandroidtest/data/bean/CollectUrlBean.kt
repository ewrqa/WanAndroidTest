package com.example.wanandroidtest.data.bean

import android.annotation.SuppressLint

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.bean</p>
 * <p>简述:收藏网址数据类</p>
 *
 * @author 张凯涛
 * @date 2025/3/13
 */
@SuppressLint("ParcelCreator")
data class CollectUrlBean(
    var icon: String,
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var userId: Int,
    var visible: Int,
)
