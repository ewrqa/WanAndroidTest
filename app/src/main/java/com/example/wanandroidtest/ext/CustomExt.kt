package com.example.wanandroidtest.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.wanandroidtest.R
import com.example.wanandroidtest.data.bean.ListDataUiState
import com.example.wanandroidtest.ui.fragment.home.HomeFragment
import com.example.wanandroidtest.ui.fragment.me.MeFragment
import com.example.wanandroidtest.ui.fragment.project.ProjectFragment
import com.example.wanandroidtest.ui.fragment.public.PublicFragmetn
import com.example.wanandroidtest.ui.fragment.system.SystemFragment
import com.example.wanandroidtest.util.SettingUtil
import com.example.wanandroidtest.weight.ScaleTransitionPagerTitleView
import com.example.wanandroidtest.weight.loadcallback.EmptyCallback
import com.example.wanandroidtest.weight.loadcallback.ErrorCallback
import com.example.wanandroidtest.weight.loadcallback.LoadingCallBack
import com.example.wanandroidtest.weight.recyclerview.DefineLoadMoreView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import me.hgj.jetpackmvvm.base.appContext
import me.hgj.jetpackmvvm.ext.util.toHtml
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ext</p>
 * <p>简述:view 的拓展类 </p>
 *
 * @author 张凯涛
 * @date 2024/8/6
 */
/**
 *  toolber的拓展类 便于设置想要的文本
 */
fun Toolbar.initClose(
    toolbarTitle: String,
    toolbarBackImage: Int = R.drawable.ic_back,
    block: (toolbar: Toolbar) -> Unit,
): Toolbar {
    title = toolbarTitle.toHtml()
    setNavigationIcon(toolbarBackImage)
    setNavigationOnClickListener { block.invoke(this) }
    return this
}

/**
 *初始化 主页面的viewpager2
 */
fun ViewPager2.initMain(fragment: Fragment): ViewPager2 {
    //是否允许滑动
    this.isUserInputEnabled = false
    //缓存的页面
    this.offscreenPageLimit = 5
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return HomeFragment()
                }
                1 -> {
                    return ProjectFragment()
                }
                2 -> {
                    return SystemFragment()
                }
                3 -> {
                    return PublicFragmetn()
                }
                4 -> {
                    return MeFragment()
                }
                else -> {
                    return HomeFragment()
                }
            }
        }

        override fun getItemCount() = 5
    }
    return this
}

/**
 * 初始化 收藏的viewpager2
 */
fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true,
): ViewPager2 {
    this.isUserInputEnabled = isUserInputEnabled
    adapter = object : FragmentStateAdapter(fragment) {
        //fragment页数
        override fun getItemCount() = fragments.size

        //当前的下标
        override fun createFragment(position: Int) = fragments[position]
    }
    return this
}

/**
 * 列表动画
 */
fun BaseQuickAdapter<*, *>.setListAnimation(mode: Int) {
    //0表示关闭动画
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

/**
 * 收藏导航栏初始化拓展
 */
fun MagicIndicator.bindViePager2(
    viewPager: ViewPager2,
    mTitleList: List<String> = arrayListOf(),
    action: (index: Int) -> Unit = {},
) {
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        //文本的个数
        override fun getCount(): Int {
            return mTitleList.size
        }

        //设置文本的样式
        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            return ScaleTransitionPagerTitleView(appContext).apply {
                //设置文本
                text = mTitleList[index].toHtml()
                //设置大小
                textSize = 15f
                //未选中的颜色
                normalColor = Color.WHITE
                //选中的颜色
                selectedColor = Color.WHITE

                setOnClickListener {
                    //当前的下标就切换到当前的位置
                    viewPager.currentItem = index
                    action.invoke(index)
                }
            }
        }

        /**
         * 指示器下方条形颜色
         */
        override fun getIndicator(context: Context?): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = UIUtil.dip2px(appContext, 3.0).toFloat()
                lineWidth = UIUtil.dip2px(appContext, 30.0).toFloat()
                //线条的圆角
                roundRadius = UIUtil.dip2px(appContext, 6.0).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.WHITE)
            }
        }

    }
    this.navigator = commonNavigator
    /**
     * viewpager的状态监听
     */
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        //选中
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViePager2.onPageSelected(position)
            action.invoke(position)
        }

        //滚动时
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViePager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        //滚动状态
        //ViewPager2.SCROLL_STATE_IDLE：表示 ViewPager2 处于静止状态。
        //ViewPager2.SCROLL_STATE_DRAGGING：表示用户正在拖动 ViewPager2。
        //ViewPager2.SCROLL_STATE_SETTLING：表示 ViewPager2 正在自动滚动到某个页面。
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViePager2.onPageScrollStateChanged(state)
        }
    })
}

/**
 *  初始化底部按钮
 */
fun BottomNavigationViewEx.init(block: (Int) -> Unit): BottomNavigationViewEx {
    //是否启动动画
    enableAnimation(true)
    //itemIconTintList = SettingUtil.getColorStateList(SettingUtil.getColor(appContext))
    //    itemTextColor = SettingUtil.getColorStateList(appContext)
    //设置导航栏文本的大小
    setTextSize(12F)
    setOnNavigationItemSelectedListener {
        block.invoke(it.itemId)
        true
    }
    return this
}

/**
 * 布局 loadSir架构 加载页面
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallBack::class.java)
}

/**
 * 布局 loadSir架构  显示空布局页面
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 布局 loadSir架构  失败页面
 */
fun LoadService<*>.showError(errorMessage: String) {
    this.errorMessage(errorMessage)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 布局 loadSir架构  失败内容的展示
 */
fun LoadService<*>.errorMessage(message: String) {
    if (message.isEmpty()) this.setCallBack(ErrorCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.error_text).text = message
    }
}

/**
 *   初始化加载数据
 *   使用 LoadSir   作用于 页面数据显示的状态
 */
fun loadServiceInit(view: View, block: () -> Unit): LoadService<Any> {
    val register = LoadSir.getDefault().register(view) {
        //点击重试的时候触发
        block.invoke()
    }
    //显示成功的状态
    register.showSuccess()
    //设置加载数据时候的进度条
    SettingUtil.setLoadingColor(SettingUtil.getColor(appContext), register)

    return register
}

/**
 *   初始化刷新页面 的拓展函数
 */
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        //setOnRefreshListener刷新组件中的方法用于监听刷新状态 执行lamda表达式
        setOnRefreshListener {
            //invoke()表示调用该  onRefreshListener函数
            onRefreshListener.invoke()
        }
        setColorSchemeColors(SettingUtil.getColor(appContext))
    }
}


/**
 *  对recyclerview进行初始化操作
 *   recyclerview排列方式
 *   与其绑定的适配器
 *   isNestedScrollingEnabled是否允许嵌入的条目滚动
 */
fun SwipeRecyclerView.init(
    layoutManager: RecyclerView.LayoutManager,
    recyclerviewAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true,
): SwipeRecyclerView {
    setLayoutManager(layoutManager)
    adapter = recyclerviewAdapter
    setHasFixedSize(true)
    isNestedScrollingEnabled = isScroll
    return this
}

/**
 * 对recyclerview进行拓展 刷新
 *
 */
fun SwipeRecyclerView.initFooter(loadMoreListener: SwipeRecyclerView.LoadMoreListener)
        : DefineLoadMoreView {
    //初始化
    val defineLoadMoreView = DefineLoadMoreView(context)
    //尾部加载的颜色
    defineLoadMoreView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
    defineLoadMoreView.setLoadModeListener {
        defineLoadMoreView.onLoading()
        //到底部的时候触发回调机制进行加载更多的数据
        loadMoreListener.onLoadMore()
    }
    this.run {
        //将自定义的defineloadMoreview添加到recyclerview底部
        addFooterView(defineLoadMoreView)
        //将自定义的加载视图添加到recyclerview 使用
        setLoadMoreView(defineLoadMoreView)
        //到底部的时候触发回调机制进行加载更多的数据
        setLoadMoreListener(loadMoreListener)
    }
    return defineLoadMoreView
}

/**
 *  recyclerview拓展初始化
 */
fun RecyclerView.init(
    layoutManager: RecyclerView.LayoutManager,
    baseQuickAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = false,
): RecyclerView {
    setLayoutManager(layoutManager)
    adapter = baseQuickAdapter
    setHasFixedSize(true)
    isNestedScrollingEnabled = isScroll
    return this
}

/**
 *  列表数据加载
 *  之前的页面管理是通过是否有没有数据来隐藏和展示页面实现
 *  现在通过loadservice实现
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<*>,
    recyclerView: SwipeRecyclerView,
    swipeRefreshLayout: SwipeRefreshLayout,
) {
    //不可以滑动
    swipeRefreshLayout.isRefreshing = false
    recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
    if (data.isSuccess) {
        when {
            //没有数据的时候展示的页面
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            //第一页数据
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                loadService.showSuccess()
            }
            else -> {
                baseQuickAdapter.addData(data.listData)
                loadService.showSuccess()
            }
        }
    } else {
        //失败
        if (data.isRefresh) {
            //如果是第一页，则显示错误界面，并提示错误信息
            loadService.showError(data.errMessage)
        } else {
            recyclerView.loadMoreError(0, data.errMessage)
        }
    }
}

/**
 *  根据用户设定更改主题颜色
 */
fun setUiTheme(color: Int, vararg anyList: Any?) {
    anyList.forEach { view ->
        view?.let {
            when (it) {
                //加载服务
                is LoadService<*> -> SettingUtil.setLoadingColor(color, it as LoadService<Any>)
                //导航栏的按钮
                is FloatingActionButton -> it.backgroundTintList =
                    SettingUtil.getOneColorStateList(color)
                //刷新的加载圈
//                is SwipeRefreshLayout -> it.setColorSchemeColors(color)
//                //列表底部加载时的加载圈
//                is DefineLoadMoreView -> it.setLoadViewColor(SettingUtil.getOneColorStateList(color))
                is BottomNavigationViewEx -> {
                    it.itemIconTintList = SettingUtil.getColorStateList(color)
                    it.itemTextColor = SettingUtil.getColorStateList(color)
                }
                is androidx.appcompat.widget.Toolbar -> it.setBackgroundColor(color)
                is TextView -> it.setTextColor(color)
                is LinearLayout -> it.setBackgroundColor(color)
                is ConstraintLayout -> it.setBackgroundColor(color)
                is FrameLayout -> it.setBackgroundColor(color)
            }
        }
    }
}






