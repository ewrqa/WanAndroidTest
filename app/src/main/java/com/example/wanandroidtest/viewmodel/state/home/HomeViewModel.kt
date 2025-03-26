package com.example.wanandroidtest.viewmodel.state.home

import androidx.lifecycle.MutableLiveData
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.ext.request
import com.example.wanandroidtest.data.bean.HomeBean
import com.example.wanandroidtest.data.bean.ListDataUiState
import com.example.wanandroidtest.data.request.HttpRequestCoroutine
import com.example.wanandroidtest.network.apiservice

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.viewmodel.state</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/7
 */
class HomeViewModel : BaseViewModel() {
    var page = 0

    //创建列表和轮播图可观察的livedata
    var homeLivedata = MutableLiveData<ListDataUiState<HomeBean>>()
    var searchbykeydata = MutableLiveData<ListDataUiState<HomeBean>>()

    /**
     * 获取首页列表数据
     * @param 是否进行刷洗
     */
    fun getHomeList(isRefresh: Boolean) {
        if (isRefresh) {
            page = 0
        }
        request({ HttpRequestCoroutine.getHomeData(page) }, {
            page++
            val listDataUiState = ListDataUiState(
                isRefresh = isRefresh,
                isSuccess = true,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            homeLivedata.value = listDataUiState
        }, {
            val listDataUiState = ListDataUiState(
                isRefresh = isRefresh,
                isSuccess = false,
                errMessage = it.errorMsg,
                listData = arrayListOf<HomeBean>()
            )
            homeLivedata.value = listDataUiState
        })
    }

    /**
     * 搜索
     */
    fun searchHistoryData(isRefresh: Boolean, key: String) {
        if (isRefresh) {
            page = 0
        }
        request({ apiservice.getSearchDataByKey(page, key) }, {
            val listDataUiState = ListDataUiState(isSuccess = true,
                isRefresh = true,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            searchbykeydata.value = listDataUiState
        }, {
            val listDataUiState = ListDataUiState(isRefresh = isRefresh,
                isSuccess = false,
                errMessage = it.errorMsg,
                listData = arrayListOf<HomeBean>()
            )
            searchbykeydata.value = listDataUiState
        })
    }
}