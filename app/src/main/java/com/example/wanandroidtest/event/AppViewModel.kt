package com.example.wanandroidtest.event

import com.blankj.utilcode.util.Utils
import com.example.jetpackmvvm.base.BaseViewModel
import com.example.wanandroidtest.data.bean.UserInfoBean
import com.example.wanandroidtest.util.CacheUtil
import com.example.wanandroidtest.util.SettingUtil
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData


/**
 * <p>项目名称:WanAndroidTest</p>
 * <p>包名:com.example.wanandroidtest.event</p>
 * <p>简述:App的公共ViewMode方便统一更新数据</p>
 *
 * @author 张凯涛
 * @date 2025/2/27
 */
class AppViewModel : BaseViewModel() {
    /**
     * UnPeekLiveData 是为了防止livedata数据倒灌自定义的
     * App账户信息  用于更新账户信息同时防止livedata数据倒灌
     */
    var userInfo = UnPeekLiveData.Builder<UserInfoBean>().setAllowNullValue(true).create()

    //App 列表动画
    var appAnimation = EventLiveData<Int>()

    //主题颜色
    var appColor = EventLiveData<Int>()

    init {
        userInfo.value = CacheUtil.getUser()
        //初始化列表动画
        appAnimation.value = SettingUtil.getModel()
        appColor.value = SettingUtil.getColor(Utils.getApp())
    }
}