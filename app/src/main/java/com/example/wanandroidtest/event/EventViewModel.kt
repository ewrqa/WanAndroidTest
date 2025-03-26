package com.example.wanandroidtest.event

import com.example.jetpackmvvm.callback.livedata.EventLiveData
import com.example.wanandroidtest.data.bean.CollectStateBean
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.event</p>
 * <p>简述:全局类型的viewmodel 如eventBus  livedata等</p>
 *  如收藏的全局通报
 * @author 张凯涛
 * @date 2025/3/14
 */
class EventViewModel : BaseViewModel() {
    //收藏与取消收藏的 全局消息推送
    val collectEvent = EventLiveData<CollectStateBean>()
}