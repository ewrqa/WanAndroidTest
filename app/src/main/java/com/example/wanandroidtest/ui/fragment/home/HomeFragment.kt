package com.example.wanandroidtest.ui.fragment.home
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpackmvvm.util.nav
import com.example.jetpackmvvm.util.navigateActio
import com.example.wanandroidtest.R
import com.example.wanandroidtest.appViewModel
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.data.bean.CollectStateBean
import com.example.wanandroidtest.databinding.HomeFragmentBinding
import com.example.wanandroidtest.eventViewModel
import com.example.wanandroidtest.ext.*
import com.example.wanandroidtest.ui.adapter.HomeListAdapter
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel
import com.example.wanandroidtest.viewmodel.state.home.HomeViewModel
import com.kingja.loadsir.core.LoadService

/**
 *  当前是主分支
 */
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    private lateinit var loadSir: LoadService<Any>
    private val homeListAdapter: HomeListAdapter by lazy { HomeListAdapter(arrayListOf()) }

    //引用收藏的viewmodel使用收藏与取消收藏
    private val collectViewModel: CollectViewModel by viewModels()
    override fun layoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initView(savedInstanceState: Bundle?) {
        //设置标题栏
        myDataBinding.homeToolbar.toolbar.apply {
            title = "首页"
            inflateMenu(R.menu.home_menu)
            //搜索
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        nav().navigateActio(R.id.fragment_select)
                    }
                }
                true
            }
            appViewModel.appColor.value?.let {
                setUiTheme(it, myDataBinding.homeToolbar.toolbar)
            }
        }
        loadSir =
            loadServiceInit(myDataBinding.includeList.includeRecyclerview.swipeRefresh) {
                viewModel.getHomeList(true)
                loadSir.showLoading()
            }
        //recyclerview的初始化
        myDataBinding.includeList.includeRecyclerview
            .swipeRecyclerview.init(LinearLayoutManager(context), homeListAdapter, true).let {
                //当滑动到底部的时候触发回调
                it.initFooter {
                    viewModel.getHomeList(false)
                }
            }

        homeListAdapter.run {
            //收藏与取消收藏
            setHomeCollectClick { item, v, position ->
                if (v.isChecked) {
                    collectViewModel.setArticleUnCollect(item.id)
                } else {
                    collectViewModel.setArticleCollect(item.id)
                }
            }
            //列表详情页
            setOnItemClickListener { _, _, position ->
                nav().navigateActio(R.id.action_mainfragment_to_webviewFragment, Bundle().apply {
                    //通过序列化将dat传递过去
                    putParcelable("ariticleData", homeListAdapter.data[position])
                })
            }
        }
        myDataBinding.includeList.includeRecyclerview.swipeRefresh.init {
            //刷新的时候会执行
            viewModel.getHomeList(true)
        }
    }
    override fun lazyData() {
        loadSir.showLoading()
        viewModel.getHomeList(true)
    }
    override fun createObserver() {
        viewModel.homeLivedata.observe(viewLifecycleOwner, {
            loadListData(it, homeListAdapter, loadSir,
                myDataBinding.includeList.includeRecyclerview.swipeRecyclerview,
                myDataBinding.includeList.includeRecyclerview.swipeRefresh)
        })
        //收藏与取消收藏的回调
        collectViewModel.articleCollect.observe(viewLifecycleOwner, {
            if (it.isSuccess) {
                eventViewModel.collectEvent.value = CollectStateBean(isCollect = true, isID = id)
            } else {
                showMessage(message = it.errorMsg)
                for (index in homeListAdapter.data.indices) {
                    //进行遍历获取
                    if (homeListAdapter.data[index].id == it.isID) {
                        homeListAdapter.data[index].collect = it.isCollect
                        homeListAdapter.notifyItemChanged(index)
                        break
                    }
                }

            }
        })
    }
}