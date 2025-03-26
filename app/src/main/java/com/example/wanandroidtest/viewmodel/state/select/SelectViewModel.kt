package com.example.wanandroidtest.viewmodel.state.select

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackmvvm.ResultState
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.ext.request
import com.example.wanandroidtest.data.bean.HotSelectBean
import com.example.wanandroidtest.network.apiservice
import com.example.wanandroidtest.util.CacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.viewmodel.state.select</p>
 * <p>简述:首页搜索viewmodel</p>
 *
 * @author 张凯涛
 * @date 2025/3/19
 */
class SelectViewModel : BaseViewModel() {

    //可观察livedata
    var hotSelectList = MutableLiveData<ResultState<ArrayList<HotSelectBean>>>()

    //历史数据
    var searchHistory = MutableLiveData<ArrayList<String>>()

    //获取数据
    fun getHotSelect() {
        request({ apiservice.getSearchData() }, hotSelectList)
    }

    lateinit var searchHistory1: ArrayList<String>

    //获取历史数据
    fun getSearchHistoryData() {
        viewModelScope.launch {
            kotlin.runCatching {
                //设置协成的调度器
                withContext(Dispatchers.IO) {
                    searchHistory1 = CacheUtil.getSearchHistory()
                }
            }.onSuccess {
                //将string转换成arraylist
//               val containsAll = searchHistory1.split(",").toCollection(ArrayList())
                searchHistory.value = searchHistory1
            }.onFailure {
                Log.e("失败", it.toString())
            }
        }
    }
}