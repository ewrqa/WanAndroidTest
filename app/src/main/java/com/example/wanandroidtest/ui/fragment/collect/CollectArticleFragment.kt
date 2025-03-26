package com.example.wanandroidtest.ui.fragment.collect

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.data.bean.CollectStateBean
import com.example.wanandroidtest.databinding.FragmentCollectarticleBinding
import com.example.wanandroidtest.eventViewModel
import com.example.wanandroidtest.ext.*
import com.example.wanandroidtest.ui.adapter.CollectArticleaAdapter
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel
import com.kingja.loadsir.core.LoadService

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.collect</p>
 * <p>简述:收藏当中的文章收藏 </p>
 *
 * @author 张凯涛
 * @date 2025/2/28
 */
class CollectArticleFragment : BaseFragment<CollectViewModel, FragmentCollectarticleBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_collectarticle
    }

    //加载状态的管理不同的状态展示不同的页面
    private lateinit var loadservice: LoadService<Any>

    private val collectArticleAdapter: CollectArticleaAdapter by lazy {
        CollectArticleaAdapter(
            arrayListOf())
    }

    override fun initView(savedInstanceState: Bundle?) {
        //配置页面加载状态
        loadservice = loadServiceInit(myDataBinding.collectArticleRecyclerview.swipeRefresh) {
            loadservice.showLoading()
            viewModel.getCollectAriticleData(true)
        }
        /**
         * 对recyclerview进行初始化操作
         */
        myDataBinding.collectArticleRecyclerview.swipeRecyclerview.init(
            LinearLayoutManager(context), collectArticleAdapter, true).let {

            //由于initFooter的参数只有一个并且是函数类型的参数，所以在调用的的时候可以使用lambda的方式去
            //在lambda表达式中如果该函数参数只有一个抽象法方法的时候可以不用通过object:Sw...的方式去实现直接省略掉，执行代码
            it.initFooter {
                viewModel.getCollectAriticleData(false)
            }
//             it.initFooter(object : SwipeRecyclerView.LoadMoreListener{
//                 override fun onLoadMore() {
//                     viewModel.getCollectAriticleData(false)
//                 }
//             })
        }
        myDataBinding.collectArticleRecyclerview.swipeRefresh.init {
            viewModel.getCollectAriticleData(true)
        }
        /**
         * 适配器的点击事件
         *  收藏与取消收藏
         */
        collectArticleAdapter.run {
            setCollectClick { item, v, position ->
                //判断当前的状态
                if (v.isChecked)
                    viewModel.setArticleUnCollect(item.originId)
                else
                    viewModel.setArticleCollect(item.originId)
            }
        }
    }

    /**
     *  数据懒加载
     */
    override fun lazyData() {
        loadservice.showLoading()
        viewModel.getCollectAriticleData(true)
    }

    /**
     * 请求结果响应
     */
    override fun createObserver() {
        //列表展示
        viewModel.collectData.observe(viewLifecycleOwner, {
            loadListData(it, collectArticleAdapter,
                loadservice, myDataBinding.collectArticleRecyclerview.swipeRecyclerview,
                myDataBinding.collectArticleRecyclerview.swipeRefresh)
        })
        //收藏与取消收藏
        viewModel.articleCollect.observe(viewLifecycleOwner, {
            if (it.isSuccess) {
                eventViewModel.collectEvent.value =
                    CollectStateBean(isCollect = it.isCollect, isID = it.isID)
            } else {
                showMessage(message = it.errorMsg)
                for (index in collectArticleAdapter.data.indices) {
                    if (collectArticleAdapter.data[index].originId == it.isID) {
                        collectArticleAdapter.notifyItemChanged(index)
                        break
                    }
                }

            }
        })
    }
}