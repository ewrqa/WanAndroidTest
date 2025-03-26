package com.example.wanandroidtest.ui.fragment.collect

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.data.bean.CollectStateBean
import com.example.wanandroidtest.databinding.FragmentCollecturlBinding
import com.example.wanandroidtest.eventViewModel
import com.example.wanandroidtest.ext.*
import com.example.wanandroidtest.ui.adapter.CollectUrlAdapter
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel
import com.kingja.loadsir.core.LoadService

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.collect</p>
 * <p>简述: 收藏界面的 网址收藏</p>
 *
 * @author 张凯涛
 * @date 2025/2/28
 */
class CollectUrlFragment : BaseFragment<CollectViewModel, FragmentCollecturlBinding>() {
    //对适配器进行初始化
    private val collectUrlAdapter by lazy { CollectUrlAdapter(arrayListOf()) }
    lateinit var loadServiceInit: LoadService<Any>
    override fun layoutId(): Int {
        return R.layout.fragment_collecturl
    }

    override fun lazyData() {
        loadServiceInit.showLoading()
        viewModel.getCollectUrlListData(true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        //初始化 页面加载管理
        loadServiceInit = loadServiceInit(myDataBinding.collectUrlRecyclerview.swipeRefresh) {
            loadServiceInit.showLoading()
            viewModel.getCollectUrlListData(true)
        }
        //recyclerview的初始化
        myDataBinding.collectUrlRecyclerview.swipeRecyclerview.init(
            LinearLayoutManager(context), collectUrlAdapter
        ).let {
            it.initFooter {
                viewModel.getCollectUrlListData(false)
            }
        }
        myDataBinding.collectUrlRecyclerview.swipeRefresh.init {
            viewModel.getCollectUrlListData(true)
        }
        collectUrlAdapter.run {
            setCollectUrlClick { data, v, position ->
                if (v.isChecked) viewModel.setUrlUnCollect(data.id) else viewModel.setUrlCollect(
                    data.name,
                    data.link)
            }
        }
    }

    override fun createObserver() {
        //列表
        viewModel.collectUrlData.observe(viewLifecycleOwner, {
            loadListData(it, collectUrlAdapter, loadServiceInit,
                myDataBinding.collectUrlRecyclerview.swipeRecyclerview,
                myDataBinding.collectUrlRecyclerview.swipeRefresh)
        })
        //收藏与取消收藏
        viewModel.urlCollect.observe(viewLifecycleOwner, {
            if (it.isSuccess) {
                eventViewModel.collectEvent.value =
                    CollectStateBean(isCollect = true, isID = it.isID)
            } else {
                showMessage(message = it.errorMsg)
                for (index in collectUrlAdapter.data.indices) {
                    if (collectUrlAdapter.data[index].id == it.isID) {
                        collectUrlAdapter.notifyItemChanged(index)
                        break
                    }
                }
            }
        })
    }

}