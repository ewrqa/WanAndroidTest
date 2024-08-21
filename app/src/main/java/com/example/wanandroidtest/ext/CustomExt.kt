package com.example.wanandroidtest.ext

import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.wanandroidtest.R
import com.example.wanandroidtest.ui.fragment.home.HomeFragment
import com.example.wanandroidtest.ui.fragment.me.MeFragment
import com.example.wanandroidtest.ui.fragment.project.ProjectFragment
import com.example.wanandroidtest.ui.fragment.public.PublicFragmetn
import com.example.wanandroidtest.ui.fragment.system.SystemFragment
import com.example.wanandroidtest.util.SettingUtil
import com.example.wanandroidtest.weight.loadcallback.LoadingCallBack
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import me.hgj.jetpackmvvm.base.appContext
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * 布局 loadSir架构 加载页面
 */
fun LoadService<*>.showLoading(){
    this.showCallback(LoadingCallBack::class.java)
}
/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ext</p>
 * <p>简述:view 的拓展类 </p>
 *
 * @author 张凯涛
 * @date 2024/8/6
 */
fun Toolbar.initFunction(
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
 *初始化 viewpager2
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



