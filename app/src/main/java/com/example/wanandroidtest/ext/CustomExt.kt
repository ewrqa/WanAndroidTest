package com.example.wanandroidtest.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.wanandroidtest.R
import com.example.wanandroidtest.ui.fragment.home.HomeFragment
import com.example.wanandroidtest.ui.fragment.me.MeFragment
import com.example.wanandroidtest.ui.fragment.project.ProjectFragment
import com.example.wanandroidtest.ui.fragment.public.PublicFragmetn
import com.example.wanandroidtest.ui.fragment.system.SystemFragment
import com.example.wanandroidtest.util.SettingUtil
import com.example.wanandroidtest.weight.ScaleTransitionPagerTitleView
import com.example.wanandroidtest.weight.loadcallback.LoadingCallBack
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tencent.bugly.proguard.p
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
 * 布局 loadSir架构 加载页面
 */
fun LoadService<*>.showLoading(){
    this.showCallback(LoadingCallBack::class.java)
}
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
        setNavigationOnClickListener{block.invoke(this)}
        return this
    }

/**
 *   初始化刷新页面 的拓展函数
 */
fun SwipeRefreshLayout.init(onRefreshListener: ()->Unit){
    this.run {
        //setOnRefreshListener刷新组件中的方法用于监听刷新状态 执行lamda表达式
        setOnRefreshListener {
            //invoke()表示调用该  onRefreshListener函数
            onRefreshListener.invoke()
        }
    }
}
/**
 *初始化 主页面的viewpager2
 */
fun  ViewPager2.initMain(fragment:Fragment):ViewPager2{
    //是否允许滑动
      this.isUserInputEnabled=false
    //缓存的页面
    this.offscreenPageLimit=5
        adapter= object :FragmentStateAdapter(fragment){
            override fun createFragment(position: Int): Fragment {
               when(position){
                    0->{return  HomeFragment () }
                    1->{return ProjectFragment()}
                    2->{return  SystemFragment()}
                    3->{return  PublicFragmetn()}
                    4->{return  MeFragment()
                    }
                    else ->{return  HomeFragment()}
               }
            }
            override fun getItemCount()=5
        }
    return this
}
    /**
     * 初始化 收藏的viewpager2
     */
    fun ViewPager2.init(
        fragment: Fragment,
        fragments:ArrayList<Fragment>,
        isUserInputEnabled:Boolean=true
    ):ViewPager2{
     this.isUserInputEnabled=isUserInputEnabled
       adapter=object :FragmentStateAdapter(fragment){
           //fragment页数
           override fun getItemCount()=fragments.size
           //当前的下标
           override fun createFragment(position: Int)=fragments[position]
       }
        return this
    }
/**
 * 收藏导航栏初始化拓展
 */
    fun MagicIndicator.bindViePager2(
     viewPager:ViewPager2,
     mTitleList:List<String> = arrayListOf(),
     action:(index:Int)->Unit={}){
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter= object :CommonNavigatorAdapter(){
        //文本的个数
        override fun getCount(): Int {
           return  mTitleList.size
        }
        //设置文本的样式
        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
           return  ScaleTransitionPagerTitleView(appContext).apply {
               //设置文本
               text=mTitleList[index].toHtml()
               //设置大小
               textSize=20f
               //未选中的颜色
               normalColor=Color.WHITE
               //选中的颜色
               selectedColor=Color.WHITE

               setOnClickListener {
                   //当前的下标就切换到当前的位置
                   viewPager.currentItem=index
                   action.invoke(index)
               }
           }
        }
        /**
         * 指示器下方条形颜色
         */
        override fun getIndicator(context: Context?): IPagerIndicator {
           return  LinePagerIndicator(context).apply {
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
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
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
            positionOffsetPixels: Int
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
fun BottomNavigationViewEx.init(block: (Int) -> Unit):BottomNavigationViewEx{
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
 *   初始化加载数据
 *   使用 LoadSir   作用于 页面数据显示的状态
 */
fun  loadServiceInit(view:View,block:()->Unit):LoadService<Any>{
    val register = LoadSir.getDefault().register(view) {
        //点击重试的时候触发
        block.invoke()
    }
    //显示成功的状态
    register.showSuccess()
    //设置加载数据时候的进度条
    SettingUtil.setLoadingColor(SettingUtil.getColor(appContext),register)

    return  register
}



