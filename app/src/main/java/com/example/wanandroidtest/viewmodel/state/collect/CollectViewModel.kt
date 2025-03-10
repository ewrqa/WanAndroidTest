package com.example.wanandroidtest.viewmodel.state.collect

import androidx.lifecycle.MutableLiveData
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.ext.request
import com.example.wanandroidtest.data.bean.CollectBean
import com.example.wanandroidtest.data.bean.ListDataUiState
import com.example.wanandroidtest.network.apiservice

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.viewmodel.state.collect</p>
 * <p>简述:收藏viewmodel</p>
 *
 * @author 张凯涛
 * @date 2025/2/25
 */
class CollectViewModel :BaseViewModel(){
   private  var page =0

   //获取收藏数据 可观察的livedata
   var collectDate:MutableLiveData<ListDataUiState<CollectBean>> = MutableLiveData()
   /**
    * 获取收藏列表数据
    */
   fun getCollectAriticleData(isRefresh:Boolean){
      //刷新的话则加
      if (isRefresh){
         page=0
      }
      request({ apiservice.getCollectList(page)}, {
         page++
         val listDataUiState=ListDataUiState(
            isSuccess = true,
            isRefresh = isRefresh,
            isEmpty = it.isEmpty(),
            hasMore = it.hasMore(),
            isFirstEmpty = it.hasMore(),
            listData = it.datas
         )
         collectDate.value=listDataUiState
     },{
        val listDataUiState=ListDataUiState(
           isSuccess = false,
           errMessage = it.errorMsg,
           isRefresh=isRefresh,
           listData = arrayListOf<CollectBean>()
        )
         collectDate.value=listDataUiState
      })
   }
}