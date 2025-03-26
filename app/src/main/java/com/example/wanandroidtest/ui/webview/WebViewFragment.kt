package com.example.wanandroidtest.ui.webview

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.jetpackmvvm.util.nav
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.data.bean.CollectStateBean
import com.example.wanandroidtest.data.bean.HomeBean
import com.example.wanandroidtest.data.menus.CollectType
import com.example.wanandroidtest.databinding.FragmentWebviewBinding
import com.example.wanandroidtest.eventViewModel
import com.example.wanandroidtest.ext.jumpByLogin
import com.example.wanandroidtest.ext.showMessage
import com.example.wanandroidtest.util.CacheUtil
import com.example.wanandroidtest.viewmodel.state.collect.CollectViewModel
import com.example.wanandroidtest.viewmodel.state.webview.WebViewViewModel
import com.just.agentweb.AgentWeb

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.webview</p>
 * <p>简述:webview的fragment</p>
 * webview记得释放 防止内存泄漏
 * @author 张凯涛
 * @date 2025/3/18
 */
class WebViewFragment : BaseFragment<WebViewViewModel, FragmentWebviewBinding>() {
    private var mAgentWeb: AgentWeb? = null
    private var createAgentWeb: AgentWeb.PreAgentWeb? = null
    private val collectViewModel: CollectViewModel by viewModels()
    override fun layoutId(): Int {
        return R.layout.fragment_webview
    }

    override fun initView(savedInstanceState: Bundle?) {
        //设置menu
        setHasOptionsMenu(true)
        //获取传递过来的数据
        arguments?.run {
            getParcelable<HomeBean>("ariticleData")?.let {
                viewModel.articleTitle = it.title
                viewModel.url = it.link
                viewModel.isChecked = it.collect
                viewModel.id = it.id
                viewModel.type = CollectType.Ariticle.type
            }
        }
        //对webview进行初始化
        createAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(myDataBinding.agentwebview, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb().ready()
        //设置标题栏的内容
        myDataBinding.webviewInclude.toolbar.run {
            //添加menu
            mActivity.setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            title = viewModel.articleTitle
            setNavigationOnClickListener {
                mAgentWeb?.let {
                    if (it.webCreator.webView.canGoBack()) {
                        it.webCreator.webView.goBack()
                    } else {
                        nav().navigateUp()
                    }
                }
            }
        }
    }

    override fun lazyData() {
        //加载网页
        mAgentWeb = createAgentWeb?.go(viewModel.url)
        //自定义返回事件
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //不为空的时候 网页有上一级的时候则返回另一个没有则返回上一层
                    mAgentWeb?.let {
                        if (it.webCreator.webView.canGoBack()) {
                            it.webCreator.webView.goBack()
                        } else {
                            nav().navigateUp()
                        }
                    }
                }
            })
    }

    override fun createObserver() {
        //收藏文章
        collectViewModel.articleCollect.observe(viewLifecycleOwner, {
            if (it.isSuccess) {
                viewModel.isChecked = it.isCollect
                //全局viewmodel通知收藏
                eventViewModel.collectEvent.value =
                    CollectStateBean(isSuccess = true, isCollect = it.isCollect)
                //刷新menu
                activity?.window?.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
                activity?.invalidateOptionsMenu()
            } else {
                showMessage(it.errorMsg)
            }
        })
        //收藏网址
        collectViewModel.urlCollect.observe(viewLifecycleOwner, {
            if (it.isSuccess) {
                viewModel.isChecked = it.isCollect
                //全局viewmodel通知收藏
                eventViewModel.collectEvent.value =
                    CollectStateBean(isSuccess = true, isCollect = it.isCollect)
                //刷新menu
                activity?.window?.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
                activity?.invalidateOptionsMenu()
            } else {
                showMessage(it.errorMsg)
            }
        })
    }

    //创建
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_webview, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //设置收藏标识
    override fun onOptionsMenuClosed(menu: Menu) {
        if (viewModel.isChecked) {
            menu.getItem(R.id.menu_collect).icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_collect)
        } else {
            menu.getItem(R.id.menu_collect).icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_collected)
        }
        super.onOptionsMenuClosed(menu)
    }

    //菜单选项的点击事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //收藏
            R.id.menu_collect -> {
                //应先判断是否是已经登录
                if (CacheUtil.isLogin()) {
                    //由于有两种类型先判断是那种的类型
                    if (viewModel.type == CollectType.Ariticle.type) {
                        //判断当前是收藏还是没有收藏
                        if (viewModel.isChecked) {
                            collectViewModel.setArticleUnCollect(viewModel.id)
                        } else {
                            collectViewModel.setArticleCollect(viewModel.id)
                        }
                    } else {
                        if (viewModel.isChecked) {
                            collectViewModel.setUrlUnCollect(viewModel.id)
                        } else {
                            collectViewModel.setUrlCollect(viewModel.articleTitle, viewModel.url)
                        }
                    }
                } else {
                    nav().jumpByLogin {}
                }
            }
            //刷新
            R.id.menu_refresh -> {
                mAgentWeb?.urlLoader?.reload()
            }
            //分享
            R.id.menu_share -> {
                startActivity(Intent.createChooser(Intent().apply {
                    //分享的动作
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, "${viewModel.articleTitle},${viewModel.url}")
                    type = "text/pain"
                }, "分享到"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}