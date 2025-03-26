package com.example.wanandroidtest.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.util</p>
 * <p>简述:缓存获取及清除</p>
 *
 * @author 张凯涛
 * @date 2025/3/25
 */
object CacheDataManager {
    //获取当前内存
    fun getTotalCacheSize(context: Context): String {
        var folderSize = getFolderSize(context.cacheDir)
        //检测分析当前是否有外置的存储sdka
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            folderSize += getFolderSize(context.externalCacheDir)
        }
        return getFormatSize(folderSize)
    }
}

/**
 * @param file 文件
 * @return  目录或者文件的大小长度
 */
private fun getFolderSize(file: File?): Long {
    var long: Long = 0
    file?.run {
        try {
            //获取文件集合
            val listFiles = listFiles()
            //循环遍历判断是不是目录或者文件
            for (i in listFiles.indices) {
                long += if (listFiles[i].isDirectory) {
                    getFolderSize(listFiles[i])
                } else {
                    //直接获取当前文件的长度
                    listFiles[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return long
}

/**
 * 字节转换 格式化单位
 */
fun getFormatSize(size: Long): String {
    //Byte
    val kiloByte = size / 1024
    if (kiloByte < 1) {
        return size.toString() + "Byte"
    }
    //kb
    val mageByte = kiloByte / 1024
    if (mageByte < 1) {
        val bigDecimal = BigDecimal(kiloByte.toString())
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
    }
    //mb
    val gigaByte = mageByte / 1024
    if (gigaByte < 1) {
        val bigDecimal = BigDecimal(mageByte.toString())
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
    }
    //tb
    val teraByte = gigaByte / 1024
    if (teraByte < 1) {
        val bigDecimal = BigDecimal(teraByte.toString())
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
    }

    val bigDecimal = BigDecimal(teraByte)
    return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
}
