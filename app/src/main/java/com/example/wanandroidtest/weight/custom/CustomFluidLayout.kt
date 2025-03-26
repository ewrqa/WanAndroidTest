package com.example.wanandroidtest.weight.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.custom</p>
 * <p>简述:流式布局</p>
 *
 * @author 张凯涛
 * @date 2025/3/20
 */
class CustomFluidLayout @JvmOverloads constructor(
    context: Context?,
    attributeSet: AttributeSet? = null,
) : ViewGroup(context, attributeSet) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var left = 10
        var top = 10
        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            //获取父类的宽度
            val width = width
            //获取子布局的高度
            val measuredHeight = childAt.measuredHeight
            //获取子布局的宽度
            val measuredWidth = childAt.measuredWidth
            //进行判断是否超过父类的宽度
            if (left + measuredWidth > width) {
                top += measuredHeight
                left = 10
            }
            //确定子布局的
            childAt.layout(left, top, left + measuredWidth, top + measuredHeight)
            left += measuredWidth
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }
}