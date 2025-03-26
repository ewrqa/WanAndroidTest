package com.example.wanandroidtest.data.bean

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

/**
 * 收藏的网址类
 * @Author:         hegaojian
 * @CreateDate:     2019/8/31 10:36
 */
@SuppressLint("ParcelCreator")
data class CollectUrlResponse(
    var icon: String,
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var userId: Int,
    var visible: Int,
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}

