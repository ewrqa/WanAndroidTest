package com.example.wanandroidtest.data.bean

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class IntegralBean(
    var coinCount: Int,//当前积分
    var rank: Int,
    var userId: Int,
    var username: String)

