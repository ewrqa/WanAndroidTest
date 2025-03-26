package com.example.wanandroidtest.ui.fragment.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.jetpackmvvm.ext.parseState
import com.example.jetpackmvvm.util.nav
import com.example.jetpackmvvm.util.navigateActio
import com.example.wanandroidtest.R
import com.example.wanandroidtest.base.BaseFragment
import com.example.wanandroidtest.databinding.FragmentSelectBinding
import com.example.wanandroidtest.ext.init
import com.example.wanandroidtest.ui.adapter.HotSelectAdapter
import com.example.wanandroidtest.util.CacheUtil
import com.example.wanandroidtest.util.ColorUtil
import com.example.wanandroidtest.viewmodel.state.select.SelectViewModel
import com.example.wanandroidtest.weight.custom.CustomFluidLayout
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import me.hgj.jetpackmvvm.ext.util.toJson

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.ui.fragment.select</p>
 * <p>简述:首页搜索fragment</p>
 *W
 * @author 张凯涛
 * @date 2025/3/19
 */
class SelectFragment : BaseFragment<SelectViewModel, FragmentSelectBinding>() {
    //适配器的委托
    private val hotSelectAdapter: HotSelectAdapter by lazy { HotSelectAdapter(arrayListOf()) }
    private lateinit var button: Button
    private lateinit var hotSearchHistory: CustomFluidLayout
    override fun initView(savedInstanceState: Bundle?) {
        button = Button(context)
        //状态栏初始化
        hotToll()
        //设置热搜词的排列
        val boxLayoutManager = FlexboxLayoutManager(context)
        //设置主轴排列方式从左往右自动换行
        boxLayoutManager.flexDirection = FlexDirection.ROW
        boxLayoutManager.justifyContent = JustifyContent.FLEX_START
        myDataBinding.selectRecycler.init(boxLayoutManager, hotSelectAdapter)
        hotSearchHistory = myDataBinding.hotSearchHistory
        //清空
        myDataBinding.removeHistory.setOnClickListener() {
            //设置弹窗
            MaterialDialog(requireContext())
                .cancelable(true)
                .lifecycleOwner()
                .show {
                    title(text = "温馨提示")
                    message(text = "确定要全部删除吗")
                    negativeButton(text = "取消")
                    positiveButton(text = "确定")
                    negativeButton {
                        dismiss()
                    }
                    positiveButton {
                        CacheUtil.clearSearchHistory()
                        hotSearchHistory.removeAllViews()
                    }
                }
        }
        hotSelectAdapter.setHotSelectClick { item, view, position ->
            val name = hotSelectAdapter.data[position].name
            nav().navigateActio(R.id.action_selectFragment_to_fragment_searchForDetails,
                Bundle().apply {
                    putString("search", name)
                })
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_select
    }

    override fun lazyData() {
        viewModel.getHotSelect()
        //获取历史数据
        viewModel.getSearchHistoryData()
        super.lazyData()
    }

    //创建
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_select, menu)
        //设置输入框
        val searchView = menu.findItem(R.id.select_select)?.actionView as SearchView
        searchView.run {
            //设置宽度
            maxWidth = Int.MAX_VALUE
            //输入栏展开便于操作
            onActionViewExpanded()
            queryHint = "请输入搜索关键词"
            //搜索监听
            setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let {
                        updateKey(p0)
                        nav().navigateActio(R.id.action_selectFragment_to_fragment_searchForDetails,
                            Bundle().apply {
                                putString("search", p0)
                            })
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
            isSubmitButtonEnabled = true
            //反射获取权限
            val declaredField = javaClass.getDeclaredField("mGoButton")
            declaredField.run {
                //打开权限
                isAccessible = true
                val imageView = get(searchView) as ImageView
                imageView.setImageResource(R.drawable.ic_search)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun createObserver() {
        viewModel.hotSelectList.observe(viewLifecycleOwner, { it ->
            parseState(it, {
                hotSelectAdapter.setList(it)
            })
        })
        viewModel.searchHistory.observe(viewLifecycleOwner, { it ->
            hotSearchHistory.removeAllViews()
            for (i in 0 until it.size) {
                button = Button(context)
                button.text = it[i]
                button.setTextColor(ColorUtil.randomColor())
                hotSearchHistory.addView(button)
                button.setOnClickListener {
                    if (it is Button) {
                        val text = it.text.toString()
                        updateKey(text)
                        nav().navigateActio(R.id.action_selectFragment_to_fragment_searchForDetails,
                            Bundle().apply {
                                putString("search", text)
                            })
                    }
                }
            }
            CacheUtil.setSearchHistory(it.toJson())
        })
        super.createObserver()
    }

    override fun onResume() {
        super.onResume()
        hotToll()
    }

    //更新历史搜索词
    fun updateKey(key: String) {
        viewModel.searchHistory.value?.let {
            if (it.contains(key)) {
                it.remove(key)
            } else if (it.size > 10) {
                it.remove(it[it.size - 1])
            }
            it.add(0, key)
            viewModel.searchHistory.value = it
        }
    }

    private fun hotToll() {
        //将fragment添加到menu的使用当中为了可以触发menu的回调
        setHasOptionsMenu(true)
        //初始化toll栏
        myDataBinding.selectInclude.toolbar.apply {
            inflateMenu(R.menu.menu_select)
            //将自定义的tool设置为activity的状态栏
            mActivity.setSupportActionBar(this)
            this.setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                nav().navigateUp()
            }
        }
    }
}