package com.example.wanandroidtest.viewmodel.state.collect

import androidx.lifecycle.MutableLiveData
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.jetpackmvvm.ext.request
import com.example.wanandroidtest.data.bean.CollectBean
import com.example.wanandroidtest.data.bean.CollectStateBean
import com.example.wanandroidtest.data.bean.CollectUrlBean
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
class CollectViewModel : BaseViewModel() {
    private var page = 0

    //获取收藏数据 可观察的livedata
    var collectData = MutableLiveData<ListDataUiState<CollectBean>>()

    //获取收藏网址的数据  可观察的livedata
    var collectUrlData = MutableLiveData<ListDataUiState<CollectUrlBean>>()

    /**
     * 获取收藏列表数据
     *
     */
    fun getCollectAriticleData(isRefresh: Boolean) {
        //刷新的话则加
        if (isRefresh) {
            page = 0
        }
        request({ apiservice.getCollectList(0) }, {
            page++
            val listDataUiState = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = it.hasMore(),
                listData = it.datas
            )
            collectData.value = listDataUiState
        }, {
            val listDataUiState = ListDataUiState(
                isSuccess = false,
                errMessage = it.errorMsg,
                isRefresh = isRefresh,
                listData = arrayListOf<CollectBean>()
            )
            collectData.value = listDataUiState
        })
    }

    /**
     * 获取收藏网址列表数据
     */
    fun getCollectUrlListData(isRefresh: Boolean) {
        var isNull = false
        request({ apiservice.getCollectUrl() }, {

            if (it.isEmpty()) {
                isNull = true
            }
            val listDataUiState = ListDataUiState(
                isRefresh = true,
                isSuccess = isRefresh,
                isEmpty = it.isEmpty(),
                listData = it
            )
            collectUrlData.value = listDataUiState
        }, {
            val listDataUiState = ListDataUiState(
                isSuccess = false,
                isRefresh = false,
                errMessage = it.errorMsg,
                listData = arrayListOf<CollectUrlBean>()
            )
            collectUrlData.value = listDataUiState
        })
    }

    /**
     *  收藏页面与首页都会用到的 收藏与取消收藏功能
     */
    var articleCollect = MutableLiveData<CollectStateBean>()
    var urlCollect = MutableLiveData<CollectStateBean>()

    /**
     * 取消收藏文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会一直显示收藏失败 但是不会报错
     */
    //收藏
    fun setArticleCollect(collectId: Int) {
        request({ apiservice.setCollect(collectId) }, {
            val collectStateBean =
                CollectStateBean(isSuccess = true, isCollect = true, isID = collectId)
            articleCollect.value = collectStateBean
        }, {
            val collectStateBean = CollectStateBean(isSuccess = false,
                isCollect = false,
                isID = collectId,
                errorMsg = it.errorMsg)
            articleCollect.value = collectStateBean
        })
    }

    //取消收藏
    fun setArticleUnCollect(collectId: Int) {
        request({ apiservice.setUnCollect(collectId) }, {
            val collectStateBean =
                CollectStateBean(isSuccess = true, isCollect = false, isID = collectId)
            articleCollect.value = collectStateBean
        }, {
            val collectStateBean = CollectStateBean(isSuccess = false,
                isCollect = true,
                isID = collectId,
                errorMsg = it.errorMsg)
            articleCollect.value = collectStateBean
        })
    }

    /**
     * 取消收藏文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会一直显示收藏失败 但是不会报错
     */
    //收藏
    fun setUrlCollect(name: String, link: String) {
        request({ apiservice.setUrlCollect(name, link) }, {
            val collectStateBean =
                CollectStateBean(isSuccess = true, isCollect = true, isID = it.id)
            articleCollect.value = collectStateBean
        }, {
            val collectStateBean =
                CollectStateBean(isSuccess = false, isCollect = false, errorMsg = it.errorMsg)
            articleCollect.value = collectStateBean
        })
    }

    //取消收藏
    fun setUrlUnCollect(collectId: Int) {
        request({ apiservice.setUrlUnCollect(collectId) }, {
            val collectStateBean =
                CollectStateBean(isSuccess = true, isCollect = false, isID = collectId)
            articleCollect.value = collectStateBean
        }, {
            val collectStateBean = CollectStateBean(isSuccess = false,
                isCollect = true,
                isID = collectId,
                errorMsg = it.errorMsg)
            articleCollect.value = collectStateBean
        })
    }


}