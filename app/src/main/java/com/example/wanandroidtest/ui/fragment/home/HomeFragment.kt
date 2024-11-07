package com.example.wanandroidtest.ui.fragment.home
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.example.jetpackmvvm.ext.parseState
import com.example.jetpackmvvm.util.nav
import com.example.jetpackmvvm.util.navigateActio
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.data.bean.BannerBean
import com.example.wanandroidtest.databinding.HomeFragmentBinding
import com.example.wanandroidtest.ext.loadServiceInit
import com.example.wanandroidtest.ext.showLoading
import com.example.wanandroidtest.ui.adapater.HomeBannerAdapater
import com.example.wanandroidtest.viewmodel.state.home.HomeViewModel
import com.example.wanandroidtest.weight.banner.HomeBannerVIewHolder
import com.kingja.loadsir.core.LoadService
import com.zhpan.bannerview.BannerViewPager
import me.hgj.jetpackmvvm.base.appContext
/**
 *  当前是主分支
 */
class HomeFragment :BaseFragment<HomeViewModel,HomeFragmentBinding>(){
    private lateinit var loadSir:LoadService<Any>
    override fun layoutId(): Int {
      return  R.layout.home_fragment
    }
    override fun initView(savedInstanceState: Bundle?) {

        loadSir=loadServiceInit(myDataBinding.includeList.includeRecyclerview.swipeRefresh){
            loadSir.showLoading()
            viewModel.getBannerData()
            //获取轮播图 和页面的数据
        }
        //设置标题栏
        myDataBinding.homeToolbar.toolbar.apply {
                title="首页"
               inflateMenu(R.menu.home_menu)
               setOnMenuItemClickListener {
                   when(it.itemId){
                       R.id.home_search->{
                           nav().navigateActio(R.id.login_activity)
                       }
                   }
                   true
               }
        }
    }

    /**
     * 懒加载
     */
    override fun lazyData() {
      loadSir.showLoading()
      viewModel.getBannerData()
    }
    override fun createObserver() {

//        viewModel.bannerData.observe(this, Observer { resultState->
//            parseState(resultState,{ data->
//              val headview=  LayoutInflater.from(context).inflate(R.layout.home_fragment,null).apply {
//                      findViewById<BannerViewPager<BannerBean,HomeBannerVIewHolder>>(R.id.home_bannview).apply {
//                          adapter=HomeBannerAdapater()
//                          setLifecycleRegistry(lifecycle)
//                            create(data)
//                      }
//               }
////                myDataBinding.includeList.includeRecyclerview.swipeRecyclerview.addHeaderView(headview)
////                myDataBinding.includeList.includeRecyclerview.swipeRecyclerview.scrollToPosition(0)
//            })
//        })
    }
}