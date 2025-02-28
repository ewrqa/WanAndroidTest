package com.example.wanandroidtest.weight

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight</p>
 * <p>简述:导航栏文字自定义视图</p>
 *
 * @author 张凯涛
 * @date 2025/2/28
 */
class ScaleTransitionPagerTitleView  (context: Context):ColorTransitionPagerTitleView(context){
    var minScale=0.7f
    /**
     * 进入时的动画
     */
    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        super.onEnter(index, totalCount, enterPercent, leftToRight)
           //进入时文字变大
            scaleX=minScale+(1.0f-minScale)*enterPercent
            scaleY=minScale+(1.0f-minScale)*enterPercent

    }
    /**
     * 离开时的动画
     */
    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        super.onLeave(index, totalCount, leavePercent, leftToRight)
        //离开时减小
        scaleX=1.0f+(minScale-1.0f)*leavePercent
        scaleY=1.0f+(minScale-1.0f)*leavePercent
    }
}