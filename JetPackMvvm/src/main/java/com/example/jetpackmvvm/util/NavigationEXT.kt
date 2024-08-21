package com.example.jetpackmvvm.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.jetpackmvvm.util</p>
 * <p>简述:Navigation的拓展类</p>
 * 跳转  带参  防连点
 * @author 张凯涛
 * @date 2024/8/7
 */
//跳转
fun Fragment.nav():NavController{
    return  NavHostFragment.findNavController(this)
}
/**
 * 防连点 跳转
 */
var  lastTime=0L
fun NavController.navigateActio(id :Int,bundle: Bundle?=null,time:Long=500){
    //获取点击的时间
    val currentTime = System.currentTimeMillis()
    if (currentTime>= lastTime+time){
        //上一次点击额时间
        lastTime = currentTime
        try {
            navigate(id, bundle)
        }catch (ignore:Exception){

        }
    }
}
