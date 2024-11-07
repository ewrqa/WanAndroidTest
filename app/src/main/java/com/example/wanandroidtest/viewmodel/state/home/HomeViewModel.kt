package com.example.wanandroidtest.viewmodel.state.home

import androidx.lifecycle.MutableLiveData
import com.example.jetpackmvvm.ResultState
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.ext.request
import com.example.wanandroidtest.data.bean.BannerBean
import com.example.wanandroidtest.data.bean.HomeBean
import com.example.wanandroidtest.data.bean.ListDataUiState
import com.example.wanandroidtest.data.request.HttpRequestManager
import com.example.wanandroidtest.data.request.httpRequestManager
import com.example.wanandroidtest.network.apiservice
import me.hgj.jetpackmvvm.ext.request

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.viewmodel.state</p>
 * <p>简述:</p>
 *
 * @author 张凯涛
 * @date 2024/8/7
 */
class HomeViewModel : BaseViewModel() {
    //页码
    var page=0
    //banner  livedata
    var bannerData: MutableLiveData<ResultState<ArrayList<BannerBean>>> = MutableLiveData()
    // homeBean   livedata
    val homeData = MutableLiveData<ListDataUiState<HomeBean>>()
    /**
     * 获取首页轮播图
     */
    fun getBannerData(){
       request({ apiservice.banner()},bannerData,false)
    }


    /**
     *   首页列表数据获取
     *   刷新从第一页开始
     *   是否刷新
     *
     */
//    fun getHomeData(isRefresh: Boolean) {
//        if (isRefresh) {
//            page = 0
//        }
//        request({ httpRequestManager.getHomeData(page) }, {
//            //请求成功
//            page++
//            val listDataUiState =
//                ListDataUiState(
//                    isSuccess = true,
//                    isRefresh = isRefresh,
//                    isEmpty = it.isEmpty(),
//                    hasMore = it.hasMore(),
//                    isFirstEmpty = isRefresh && it.isEmpty(),
//                    listData = it.datas
//                )
//            homeData.value = listDataUiState
//        }, {
//            //请求失败
//            val listDataUiState =
//                ListDataUiState(
//                    isSuccess = false,
//                    errMessage = it.errorMsg,
//                    isRefresh = isRefresh,
//                    listData = arrayListOf<HomeBean>()
//                )
//            homeData.value = listDataUiState
//        })
//    }
}