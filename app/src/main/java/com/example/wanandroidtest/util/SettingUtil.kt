package com.example.wanandroidtest.util
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.preference.PreferenceManager
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.example.wanandroidtest.R
import com.example.wanandroidtest.weight.loadcallback.LoadingCallBack
import com.kingja.loadsir.core.LoadService
/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.util</p>
 * <p>简述:</p>
 *用于 设置动画  加载进度条的颜色
 * @author 张凯涛
 * @date 2024/8/8
 */
object SettingUtil{
    /**
     *  获取当前主题的颜色
     */
    fun getColor(context: Context):Int{
        //获取默认颜色
        val defaultColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val settint = PreferenceManager.getDefaultSharedPreferences(context)
        val color = settint.getInt("color", defaultColor)
        return  if(color!=0&&Color.alpha(color)!=255){
            defaultColor
        }else{
            color
        }
    }
    /**
     * ColorStateList用于表示一组颜色状态和对应的颜色值
     * 包含单个颜色值达到数据
     * 一个空状态的数据 存储
     * 没有状态的单颜色数组
     */
    fun getOneColorStateList(color: Int):ColorStateList{
        val intArrayOf = intArrayOf(color)
        //设置大小
        val arrayListOf = arrayOfNulls<IntArray>(1)
        arrayListOf[0]=intArrayOf
        return  ColorStateList(arrayListOf,intArrayOf)
    }
    /**
     *  设置 loading加载布局
     */
    fun  setLoadingColor(color: Int,loadSir:LoadService<Any>){
        //去连接布局
         loadSir.setCallBack(LoadingCallBack::class.java){
             _,view->
             // 进度条颜色与背景图保持一致
             view.findViewById<ProgressBar>(R.id.loading_progress).indeterminateTintMode=PorterDuff.Mode.SRC_ATOP
             //设置进度条颜色
             view.findViewById<ProgressBar>(R.id.loading_progress).indeterminateTintList = getOneColorStateList(color)
         }
    }
}