package com.example.wanandroidtest.weight.preference

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.color.R
import com.afollestad.materialdialogs.utils.MDUtil.dimenPx

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.preference</p>
 * <p>简述:自定义视图颜色展示</p>
 * @param context  上下文
 * @param attributeSet 属性
 * @author 张凯涛
 * @date 2025/3/25
 */
class MyColorView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {
    //设置画笔
    private val strokePaint = Paint()

    //填充画笔
    private val fillPaint = Paint()

    //空白图案
    private var transparentgrid: Drawable? = null
    private val borderWidth = dimenPx(
        R.dimen.color_circle_view_border
    )

    //初始化
    init {
        //告知系统要按照我的内容绘制
        setWillNotDraw(false)
        strokePaint.style = Paint.Style.STROKE
        //绘制圆润 抗锯齿
        strokePaint.isAntiAlias = true
        //绘制背景颜色
        strokePaint.color = Color.BLACK
        strokePaint.strokeWidth = borderWidth.toFloat()
        //设置 填充笔
        fillPaint.style = Paint.Style.FILL
        //抗锯齿
        fillPaint.isAntiAlias = true
        //绘制颜色
        fillPaint.color = Color.DKGRAY
    }

    @ColorInt
    var color: Int = Color.BLACK
        set(value) {
            field = value
            fillPaint.color = value
            //通知系统开始绘制
            invalidate()
        }

    @ColorInt
    var border: Int = Color.DKGRAY
        set(value) {
            field = value
            strokePaint.color = value
            invalidate()
        }

    /**
     * 测量
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (color == Color.TRANSPARENT) {
            if (transparentgrid == null) {
                transparentgrid = ContextCompat.getDrawable(context,
                    com.example.wanandroidtest.R.drawable.transparentgrid)
            }
            transparentgrid?.setBounds(0, 0, measuredWidth, measuredHeight)
            transparentgrid?.draw(canvas)
        } else {
            canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f,
                (measuredWidth / 2f) - borderWidth, fillPaint)
        }
        canvas.drawCircle(measuredWidth / 2f,
            measuredHeight / 2f,
            (measuredWidth / 2f) - borderWidth, strokePaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        transparentgrid = null
    }

    fun setView(parseColor: Int) {
        color = parseColor
        border = parseColor

    }

    fun setViewSelect(parseColor: Int) {
        color = parseColor
        border = Color.DKGRAY
    }
}