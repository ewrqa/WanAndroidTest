package com.example.wanandroidtest.data.bean

data class UserInfoBean(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
){data class Data(
    val coinInfo: CoinInfo,
    val userInfo: UserInfo
){ data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val nickname: String,
    val rank: String,
    val userId: Int,
    val username: String
)data class UserInfo(
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
   }