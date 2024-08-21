package com.example.wanandroidtest.data.bean

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.data.bean</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/5
 */
data class LoginBean(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
){
    data class Data(
        val admin: Boolean,
        val chapterTops: List<Any>,
        val coinCount: Int,
        val collectIds: List<Any>,
        val email: String,
        val icon: String,
        val id: Int,
        val nickname: String,
        val password: String,
        val publicName: String,
        val token: String,
        val type: Int,
        val username: String
    )
}

