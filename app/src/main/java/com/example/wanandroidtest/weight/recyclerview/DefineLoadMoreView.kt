package com.example.wanandroidtest.weight.recyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.blankj.utilcode.util.ConvertUtils
import com.example.wanandroidtest.R
import com.example.wanandroidtest.util.SettingUtil
import com.yanzhenjie.recyclerview.SwipeRecyclerView

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.weight.recyclerview</p>
 * <p>简述:自定义的loadmodelview </p>
 *  参数:自定义组件必须得上下文
 *  继承  自定义加载视图
 * @author 张凯涛
 * @date 2025/3/12
 */
class DefineLoadMoreView(context: Context) : LinearLayout(context),
    SwipeRecyclerView.LoadMoreView, View.OnClickListener {
    var loading_progressBar: ProgressBar
    private var loading_message: TextView

    //初始化
    init {
        //layoutparams存储视图信息
        //设置当前布局在父布局当中的 参数信息
        layoutParams = ViewGroup.LayoutParams(-1, -2)
        //对齐方式
        gravity = Gravity.CENTER
        val minHeight = ConvertUtils.dp2px(36f)
        minimumHeight = minHeight
        //显示隐藏
        visibility = View.GONE
        //连接加载的布局
        View.inflate(context, R.layout.layout_fotter_lloadmore, this)
        loading_progressBar = findViewById(R.id.loading_progressBar)
        //判断安卓版本 进度条颜色及形状是android5.0之后才可以的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //用于设置在加载中的进度条颜色
            loading_progressBar.indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
            loading_progressBar.indeterminateTintList = SettingUtil.getOneColorStateList(context)
        }
        loading_message = findViewById(R.id.loading_message)
        setOnClickListener(this)
    }

    /**
     * 情景:准备进行回调加载更多所以要展示加载中的状态
     */
    override fun onLoading() {
        //状态和文本显示
        visibility = View.VISIBLE
        loading_progressBar.visibility = View.VISIBLE
        loading_message.visibility = View.VISIBLE
        loading_message.text = "正在努力加载请稍等.."
    }

    /**
     * 加载完毕之后
     * @param dataEmpty 是否请求到空数据
     * @param hasMore   是否还有其他数据等在加载
     */
    override fun onLoadFinish(dataEmpty: Boolean, hasMore: Boolean) {
        if (!hasMore) {
            visibility = View.VISIBLE
            if (dataEmpty) {
                loading_progressBar.visibility = View.GONE
                loading_message.visibility = View.VISIBLE
                loading_message.text = "暂时没有数据.."
            } else {
                loading_progressBar.visibility = View.GONE
                loading_message.visibility = View.VISIBLE
                loading_message.text = "没有更多数据啦"
            }
        } else visibility = View.INVISIBLE
    }

    /**
     *  在关闭自动加载的时候，通过点击方式来进行加载
     */
    override fun onWaitToLoadMore(loadMoreListener: SwipeRecyclerView.LoadMoreListener?) {
        visibility = View.VISIBLE
        loading_progressBar.visibility = View.GONE
        loading_message.visibility = View.VISIBLE
        loading_message.text = "点击我加载更多"
    }

    /**
     * 加载失败
     */
    override fun onLoadError(errorCode: Int, errorMessage: String?) {
        visibility = View.VISIBLE
        loading_progressBar.visibility = View.GONE
        loading_message.visibility = View.VISIBLE
        loading_message.text = errorMessage
        Log.e("load_error", errorMessage)
    }


    private var mLoadMoreListIterator: SwipeRecyclerView.LoadMoreListener? = null

    override fun onClick(view: View?) {
        mLoadMoreListIterator?.let {
            if (loading_message.text != "没有更多数据啦" && loading_progressBar.visibility != View.VISIBLE) {
                it.onLoadMore()
            }
        }
    }

    fun setLoadModeListener(loadMoreListener: SwipeRecyclerView.LoadMoreListener) {
        this.mLoadMoreListIterator = loadMoreListener
    }

    fun setLoadViewColor(colorstatelist: ColorStateList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loading_progressBar.indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
            loading_progressBar.indeterminateTintList = colorstatelist
        }
    }
}