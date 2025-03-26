package com.example.wanandroidtest.ui.fragment.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpackmvvm.util.nav
import com.example.jetpackmvvm.util.navigateActio
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.data.bean.CollectStateBean
import com.example.wanandroidtest.databinding.FragmentSearchfordetailsBinding
import com.example.wanandroidtest.eventViewModel
import com.example.wanandroidtest.ext.*
import com.example.wanandroidtest.ui.adapter.HomeListAdapter
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel
import com.example.wanandroidtest.viewmodel.state.home.HomeViewModel
import com.kingja.loadsir.core.LoadService

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.search</p>
 * <p>简述:搜索详情fragment</p>
 *
 * @author 张凯涛
 * @date 2025/3/22
 */
class SearchForDetailsFragment : BaseFragment<HomeViewModel, FragmentSearchfordetailsBinding>() {
    private var string: String = ""
    private lateinit var loadServiceInit: LoadService<Any>

    //搜索所用到的viewmodel
    private val homeListAdapter: HomeListAdapter by lazy { HomeListAdapter(arrayListOf()) }

    //收藏所用到的viewmodel
    private val collectViewModel: CollectViewModel by viewModels()
    override fun layoutId(): Int {
        return R.layout.fragment_searchfordetails
    }

    override fun lazyData() {
        super.lazyData()
        loadServiceInit.showLoading()
        viewModel.searchHistoryData(true, string)
    }

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.run {
            getString("search")?.let { string = it }
            myDataBinding.searchForDetailsTool.toolbar.run {
                setNavigationIcon(R.drawable.ic_back)
                title = string
                setNavigationOnClickListener {
                    nav().navigateUp()
                }
            }
        }
        //加载状态初始化
        loadServiceInit = loadServiceInit(myDataBinding.searchForDetailsRecycler.swipeRefresh) {
            loadServiceInit.showLoading()
            //请求
            viewModel.searchHistoryData(true, string)
        }
        //recyclerview初始化
        myDataBinding.searchForDetailsRecycler.swipeRecyclerview.init(
            LinearLayoutManager(context), homeListAdapter).let {
            //到底触发刷新
            it.initFooter {
                viewModel.searchHistoryData(false, string)
            }
        }
        myDataBinding.searchForDetailsRecycler.swipeRefresh.init {
            viewModel.searchHistoryData(true, string)
        }
        //收藏
        homeListAdapter.run {
            setHomeCollectClick { item, v, position ->
                if (v.isChecked) {
                    collectViewModel.setArticleUnCollect(item.id)
                } else {
                    collectViewModel.setArticleCollect(item.id)
                }
            }
            //详情列表
            setOnItemClickListener { item, _, position ->
                nav().navigateActio(R.id.action_searchForDetails_to_WebViewFragment,
                    Bundle().apply {
                        putParcelable("ariticleData", homeListAdapter.data[position])
                    })
            }
        }
    }

    override fun createObserver() {
        super.createObserver()
        //列表
        viewModel.searchbykeydata.observe(viewLifecycleOwner, {
            Log.e("数据", it.toString())
            loadListData(
                it, homeListAdapter,
                loadServiceInit,
                myDataBinding.searchForDetailsRecycler.swipeRecyclerview,
                myDataBinding.searchForDetailsRecycler.swipeRefresh)
        })
        //收藏
        collectViewModel.articleCollect.observe(viewLifecycleOwner, {
            //成功则通知全局
            if (it.isSuccess) {
                eventViewModel.collectEvent.value =
                    CollectStateBean(isSuccess = true, isID = it.isID)
            }
        })
    }
}